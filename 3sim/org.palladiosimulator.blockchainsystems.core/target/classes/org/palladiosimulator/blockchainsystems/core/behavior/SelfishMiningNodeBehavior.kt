package org.palladiosimulator.blockchainsystems.core.behavior

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import java.util.UUID
import java.util.random.RandomGenerator

/**
 * Selfish mining after Eyal & Sirer (FC 2014), Algorithm 1.
 *
 * Representation
 * - The node's local blockchain is its PUBLIC view: every valid block it receives is
 *   appended (and relayed), plus the attacker's own blocks once published.
 *   Withheld blocks live only in [hidden] until they are published.
 * - The private lead is computed from heights, not from the number of hidden blocks:
 *     lead = height(last hidden block) - height(public longest chain).
 *   Only an increase of the public height counts as "the others found a block";
 *   stale, orphan and equal-height fork blocks do not change the lead.
 * - [racing] is Eyal & Sirer's state 0': the attacker has published its only
 *   hidden block to match an honest block and waits for the race to resolve.
 *
 * Policy when the public chain grows (lead is evaluated after the growth):
 *   no hidden block, racing  -> race resolved; the round is abandoned only if the new
 *                               public tip does not build on the attacker's block
 *   no hidden block          -> keep mining on the public tip
 *   lead < 0                 -> honest chain overtook the private branch: abandon
 *   lead == 0 (was 1)        -> publish the hidden block, enter the race (state 0')
 *   lead == 1 (was 2)        -> publish everything; the private branch wins outright
 *   lead >= 2                -> publish hidden blocks up to the public height
 * When the attacker mines while racing, it publishes immediately.
 *
 * Several adversarial nodes each run this policy independently (no pooling of
 * hashing power or private branches). Model a colluding pool as one adversarial
 * node with the pool's hashing share.
 */
class SelfishMiningNodeBehavior @JvmOverloads constructor(
  private val randomGenerator: RandomGenerator = RandomGenerator.of("Random")
) : BlockchainNodeObject(), BlockchainSystemNodeBehavior {

  /** Withheld attacker blocks, oldest first. Not part of the local (public) chain. */
  private val hidden: MutableList<Block> = mutableListOf()

  /** State 0': the attacker published one block to tie and waits for resolution. */
  private var racing: Boolean = false

  /** Hash of the attacker's most recently published block of the active round. */
  private var publishedRoundTipHash: String? = null

  /** Hash of the block the mining process is currently extending. */
  private var miningTipHash: String? = null

  private var activeRoundId: String? = null
  private var activeRoundFirstPrivateBlockHash: String? = null
  private var activeRoundPrivateBlockCount: Int = 0
  private var activeRoundReleased: Boolean = false

  override fun onNodeInitialized(context: BlockchainSystemNodeContext) {
    hidden.clear()
    racing = false
    publishedRoundTipHash = null
    clearActiveRoundMetadata()
    context.miningProcess.startMining()
  }

  override fun onTransactionReceived(transaction: Transaction, context: BlockchainSystemNodeContext) {
    context.trxMemPool.storeTransaction(transaction)
    context.transactionPropagationStrategy.distribute(transaction)
  }

  override fun onBlockReceived(block: Block, context: BlockchainSystemNodeContext) {
    context.blockValidator.validateBlock(block)
  }

  override fun onBlockValidated(block: Block, isValid: Boolean, context: BlockchainSystemNodeContext) {
    if (!isValid) return

    val publicHeightBefore = context.blockchain.getLength()
    val outcome = BehaviorUtils.appendBlockToBlockchainDetailed(block, context)

    // Keep the public view complete and relay public blocks like any other node,
    // so the attacker does not additionally act as a network partition.
    if (outcome == AppendOutcome.INCLUDED || outcome == AppendOutcome.FORKING || outcome == AppendOutcome.STALE) {
      context.blockPropagationStrategy.distribute(block)
    }

    val publicHeight = context.blockchain.getLength()
    if (publicHeight <= publicHeightBefore) {
      // No public progress (stale, orphan, duplicate or equal-height fork block).
      restartIfTargetChanged(context)
      return
    }

    onPublicChainGrew(publicHeight, context)
    restartIfTargetChanged(context)
  }

  private fun onPublicChainGrew(publicHeight: Long, context: BlockchainSystemNodeContext) {
    if (hidden.isEmpty()) {
      if (racing) {
        val attackerTip = publishedRoundTipHash
        val won = attackerTip != null && publicTipsDescendFrom(attackerTip, context)
        racing = false
        if (won) {
          // The honest extension builds on the attacker's block. The monitor
          // determines success from honest validators' fork resolution.
          clearActiveRoundMetadata()
        } else {
          abandonActiveRound("RACE_LOST")
        }
        publishedRoundTipHash = null
      }
      return
    }

    val lead = hiddenTipHeight(context) - publicHeight
    when {
      lead < 0 -> {
        abandonActiveRound("HONEST_CHAIN_OVERTOOK_PRIVATE_BRANCH")
        abandonHiddenBlocks(context)
      }

      lead == 0L -> {
        publishHiddenUpTo(Long.MAX_VALUE, context)
        racing = true
        emitRoundReleased("PRIVATE_LEAD_ONE_TIE")
      }

      lead == 1L -> {
        publishHiddenUpTo(Long.MAX_VALUE, context)
        racing = false
        emitRoundReleased("PRIVATE_LEAD_TWO_OVERRIDE")
        clearActiveRoundMetadata()
        publishedRoundTipHash = null
      }

      else -> {
        publishHiddenUpTo(publicHeight, context)
        emitRoundReleased("PRIVATE_LEAD_PARTIAL_RELEASE")
      }
    }
  }

  override fun onBlockMined(block: Block, context: BlockchainSystemNodeContext) {
    if (activeRoundId == null) beginActiveRound(block, context)

    hidden.add(block)
    // Do not mine the same transactions again in the next private block. Unique
    // transactions of an abandoned private branch are restored in abandonHiddenBlocks().
    context.trxMemPool.removeTransactions(block.transactions)
    activeRoundPrivateBlockCount++
    logPrivateBlock(block)

    if (racing) {
      // State 0' and the attacker finds a block: publish and win the race.
      publishHiddenUpTo(Long.MAX_VALUE, context)
      racing = false
      emitRoundReleased("ATTACKER_MINED_DURING_RACE")
      clearActiveRoundMetadata()
      publishedRoundTipHash = null
    }
    // The mining process schedules the next block right after this callback and
    // asks onPreviousBlockSelection(), which now returns the new private tip.
  }

  override fun onCreatingBlock(blockMinedAt: Long, previousBlockHash: String, context: BlockchainSystemNodeContext): Block {
    val selection = context.transactionSelectionProcess.selectTransactionsForBlock(context)
    return context.blockFactory.createBlock(
      UUID(randomGenerator.nextLong(), randomGenerator.nextLong()).toString(),
      previousBlockHash,
      context.id,
      blockMinedAt,
      selection.totalSize,
      selection.transactions
    )
  }

  override fun onPreviousBlockSelection(context: BlockchainSystemNodeContext): String {
    val target = miningTarget(context)
    miningTipHash = target
    return target
  }

  private fun miningTarget(context: BlockchainSystemNodeContext): String {
    hidden.lastOrNull()?.let { return it.hash }
    if (racing) publishedRoundTipHash?.let { return it }
    return context.blockchain.getLastBlocksOfLongestChains().minBy { it.hash }.hash
  }

  private fun restartIfTargetChanged(context: BlockchainSystemNodeContext) {
    if (miningTarget(context) != miningTipHash) context.miningProcess.restartMining()
  }

  /** Height the last hidden block would have in the local chain. */
  private fun hiddenTipHeight(context: BlockchainSystemNodeContext): Long {
    val base = context.blockchain.getBlock(requireNotNull(hidden.first().previousHash))
      ?: error("Base of the private branch is missing from the attacker's public view")
    return context.blockchain.getPositionOfBlock(base) + hidden.size
  }

  /** Publish hidden blocks, oldest first, whose height does not exceed [maxHeight]. */
  private fun publishHiddenUpTo(maxHeight: Long, context: BlockchainSystemNodeContext) {
    while (hidden.isNotEmpty()) {
      val next = hidden.first()
      val parent = context.blockchain.getBlock(requireNotNull(next.previousHash)) ?: break
      if (context.blockchain.getPositionOfBlock(parent) + 1 > maxHeight) break
      hidden.removeAt(0)
      BehaviorUtils.appendBlockToBlockchainDetailed(next, context)
      context.blockPropagationStrategy.distribute(next)
      publishedRoundTipHash = next.hash
    }
  }

  /** True if every current public tip has the block [hash] as an ancestor (or is it). */
  private fun publicTipsDescendFrom(hash: String, context: BlockchainSystemNodeContext): Boolean {
    val target = context.blockchain.getBlock(hash) ?: return false
    val targetHeight = context.blockchain.getPositionOfBlock(target)
    return context.blockchain.getLastBlocksOfLongestChains().all { tip ->
      var current: Block? = tip
      while (current != null && context.blockchain.getPositionOfBlock(current) > targetHeight) {
        current = current.previousHash?.let { context.blockchain.getBlock(it) }
      }
      current?.hash == hash
    }
  }

  /** Drop the private branch and return its transactions to the mempool. */
  private fun abandonHiddenBlocks(context: BlockchainSystemNodeContext) {
    if (hidden.isNotEmpty()) {
      val privateTransactions = hidden.asSequence()
        .flatMap { it.transactions.asSequence() }
        .distinctBy { it.txId }
        .toList()
      val onPublicChain = context.blockchain.findTransactionIdsOnLongestChains(
        privateTransactions.map { it.txId }.toHashSet(),
        1L
      )
      val restore = privateTransactions.filter { it.txId !in onPublicChain }
      if (restore.isNotEmpty()) context.trxMemPool.storeTransactions(restore)
    }
    hidden.clear()
    racing = false
    publishedRoundTipHash = null
  }

  private fun beginActiveRound(block: Block, context: BlockchainSystemNodeContext) {
    activeRoundId = block.hash
    activeRoundFirstPrivateBlockHash = block.hash
    activeRoundPrivateBlockCount = 0
    activeRoundReleased = false
    if (traceEventLogger.isEventTypeEnabled(SelfishMiningAttackRoundStartedTraceEvent.EVENT_TYPE)) {
      traceEventLogger.logEvent(
        SelfishMiningAttackRoundStartedTraceEvent(
          occurrenceTime = block.blockMinedTimestamp,
          roundId = block.hash,
          attackerNodeId = context.id,
          firstPrivateBlockHash = block.hash,
          forkBaseHash = requireNotNull(block.previousHash) { "A selfish-mining round cannot start from a block without a predecessor" }
        )
      )
    }
  }

  private fun logPrivateBlock(block: Block) {
    val roundId = activeRoundId ?: return
    if (!traceEventLogger.isEventTypeEnabled(SelfishMiningAttackRoundPrivateBlockTraceEvent.EVENT_TYPE)) return
    traceEventLogger.logEvent(
      SelfishMiningAttackRoundPrivateBlockTraceEvent(
        occurrenceTime = block.blockMinedTimestamp,
        roundId = roundId,
        attackerNodeId = traceEventLogger.logOrigin.id,
        blockHash = block.hash,
        privateBlockIndex = activeRoundPrivateBlockCount
      )
    )
  }

  private fun emitRoundReleased(reason: String) {
    val roundId = activeRoundId ?: return
    activeRoundReleased = true
    if (traceEventLogger.isEventTypeEnabled(SelfishMiningAttackRoundReleasedTraceEvent.EVENT_TYPE)) {
      traceEventLogger.logEvent(
        SelfishMiningAttackRoundReleasedTraceEvent(
          occurrenceTime = simulationContext.systemClock.currentTime,
          roundId = roundId,
          attackerNodeId = traceEventLogger.logOrigin.id,
          firstPrivateBlockHash = activeRoundFirstPrivateBlockHash ?: roundId,
          reason = reason
        )
      )
    }
  }

  private fun abandonActiveRound(reason: String) {
    val roundId = activeRoundId ?: return
    if (traceEventLogger.isEventTypeEnabled(SelfishMiningAttackRoundAbandonedTraceEvent.EVENT_TYPE)) {
      traceEventLogger.logEvent(
        SelfishMiningAttackRoundAbandonedTraceEvent(
          occurrenceTime = simulationContext.systemClock.currentTime,
          roundId = roundId,
          attackerNodeId = traceEventLogger.logOrigin.id,
          firstPrivateBlockHash = activeRoundFirstPrivateBlockHash ?: roundId,
          reason = reason
        )
      )
    }
    clearActiveRoundMetadata()
  }

  private fun clearActiveRoundMetadata() {
    activeRoundId = null
    activeRoundFirstPrivateBlockHash = null
    activeRoundPrivateBlockCount = 0
    activeRoundReleased = false
  }

  override fun dispatchEvent(event: Event) {
    // no-op
  }
}
