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
 * Selfish-mining behavior implementing Eyal & Sirer's Algorithm 1 ("Majority is not Enough:
 * Bitcoin Mining is Vulnerable", 2014): the attacker keeps newly mined blocks private and only
 * reveals them in response to honest-network progress, tracking its hidden lead over the public
 * chain plus whether it is currently contesting a public tie.
 *
 * The refined TSE runtime additionally emits explicit attack-round trace events. A round starts
 * with the first block withheld from state 0 and remains the same round while the attacker retains
 * a hidden lead or contests the resulting fork. Publishing material does not itself declare
 * success: the simulation monitor determines the outcome from honest validators' fork-resolution
 * events. An explicit branch abandonment is emitted as an unambiguous failed round.
 *
 * State:
 * - [privateChain]: attacker-mined blocks that are still hidden / unpublished. Index 0 is the
 *   next hidden block that would be published first.
 * - [ownTipHash]: hash of the attacker's own most recently authored block, published or not. Kept
 *   separate from [privateChain] because that list empties out the moment a block is published
 *   (e.g. to force a tie), even though the pool must keep mining on top of that same block rather
 *   than falling back to an ambiguous public tip.
 * - [inTieState]: true while the attacker is contesting a public tie/race it created by revealing
 *   exactly one hidden block at lead 1.
 *
 * Policy (hidden lead k, on honest block arrival):
 * - k == 0: adopt honest progress.
 * - k == 1: publish one hidden block, entering a tie.
 * - k == 2: publish all hidden blocks to override outright.
 * - k > 2: publish one hidden block, keeping the rest of the lead hidden.
 * - If the attacker mines while tied: publish all remaining hidden blocks immediately.
 *
 * @author Yannik Sproll (ported)
 */
class SelfishMiningNodeBehavior @JvmOverloads constructor(
  private val randomGenerator: RandomGenerator = RandomGenerator.of("Random")
) : BlockchainNodeObject(), BlockchainSystemNodeBehavior {

  private val privateChain: MutableList<Block> = mutableListOf()
  private var ownTipHash: String? = null
  private var inTieState: Boolean = false

  /** Metadata for the currently active withholding attempt. */
  private var activeRoundId: String? = null
  private var activeRoundFirstPrivateBlockHash: String? = null
  private var activeRoundPrivateBlockCount: Int = 0

  override fun onNodeInitialized(context: BlockchainSystemNodeContext) {
    resetPrivateState()
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

    if (inTieState) {
      handleBlockWhileInTie(block, context)
      return
    }

    when (val hiddenLead = hiddenLead()) {
      0 -> adoptPublicBlockAndAbandonPrivateState(block, context)

      1 -> {
        if (publishOneHiddenBlock(context)) {
          // The first private block is now public even though the same attack
          // round remains active while the tie is resolved.  Emit a release
          // marker without clearing round ownership so honest adoption can be
          // counted correctly by the SPSM tracker.
          emitRoundReleased("PRIVATE_LEAD_ONE_TIE")
          inTieState = true
        }
      }

      2 -> {
        if (publishAllHiddenBlocks(context)) {
          releaseActiveRound("PRIVATE_LEAD_TWO_OVERRIDE")
        }
        inTieState = false
      }

      else -> {
        check(hiddenLead > 2)
        // Reveal one block to keep pressure on the public chain, but preserve the remaining
        // hidden advantage. The same attack round remains active while hidden material remains.
        if (publishOneHiddenBlock(context)) {
          inTieState = false
        }
      }
    }
  }

  override fun onBlockMined(block: Block, context: BlockchainSystemNodeContext) {
    if (activeRoundId == null) {
      beginActiveRound(block, context)
    }

    privateChain.add(block)
    ownTipHash = block.hash
    activeRoundPrivateBlockCount++
    logPrivateBlock(block, context)

    // If the attacker mines during a tie, it can immediately reveal the remaining hidden blocks
    // and try to secure the win. The monitor waits for honest fork resolution before calling the
    // round successful.
    if (inTieState) {
      if (publishAllHiddenBlocks(context)) {
        releaseActiveRound("ATTACKER_MINED_TIE_BREAK_EXTENSION")
      }
      inTieState = false
    }
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
    // Always continue mining on top of my own last-authored block, published or not (Algorithm
    // 1's "mine at the head of the private chain"). Falling back to a generic public-tip lookup
    // here would pick arbitrarily between my own tip and a tied honest tip during a race.
    return ownTipHash ?: context.blockchain.getLastBlocksOfLongestChains().sortedBy { it.hash }.first().hash
  }

  /**
   * Tie-state handler: if there are still hidden blocks, reveal them now to try to win the tie.
   * Otherwise the tie is lost locally, so adopt the honest block and record an unambiguous failed
   * attack round.
   */
  private fun handleBlockWhileInTie(block: Block, context: BlockchainSystemNodeContext) {
    if (hiddenLead() > 0) {
      if (publishAllHiddenBlocks(context)) {
        releaseActiveRound("RELEASED_REMAINING_PRIVATE_BRANCH_DURING_TIE")
      }
      inTieState = false
      return
    }

    adoptPublicBlockAndAbandonPrivateState(block, context)
  }

  /** Append a public block and fully abandon any no-longer-viable private strategy state. */
  private fun adoptPublicBlockAndAbandonPrivateState(block: Block, context: BlockchainSystemNodeContext) {
    val outcome = BehaviorUtils.appendBlockToBlockchainDetailed(block, context)

    if (outcome == AppendOutcome.INCLUDED || outcome == AppendOutcome.FORKING) {
      context.trxMemPool.removeTransactions(block.transactions)
      context.miningProcess.restartMining()
      context.blockPropagationStrategy.distribute(block)

      if (activeRoundId != null) {
        abandonActiveRound("ATTACKER_ADOPTED_HONEST_BRANCH")
      }

      // Once we meaningfully adopt public progress, the prior hidden branch is no longer treated
      // as our active private advantage.
      resetPrivateState()
    }
  }

  /**
   * Publish the oldest hidden attacker block.
   *
   * @return true iff the block was meaningfully appended and removed from hidden state
   */
  private fun publishOneHiddenBlock(context: BlockchainSystemNodeContext): Boolean {
    val publish = privateChain.firstOrNull() ?: return false

    val outcome = BehaviorUtils.appendBlockToBlockchainDetailed(publish, context)

    if (outcome == AppendOutcome.INCLUDED || outcome == AppendOutcome.FORKING) {
      privateChain.removeAt(0)
      context.trxMemPool.removeTransactions(publish.transactions)
      context.miningProcess.restartMining()
      context.blockPropagationStrategy.distribute(publish)
      return true
    }

    return false
  }

  /**
   * Publish all remaining hidden attacker blocks in order.
   *
   * @return true iff the private list was completely drained by successful publication
   */
  private fun publishAllHiddenBlocks(context: BlockchainSystemNodeContext): Boolean {
    while (privateChain.isNotEmpty()) {
      val sizeBefore = privateChain.size
      val published = publishOneHiddenBlock(context)

      // Safety guard against infinite loops if append fails and no progress is made.
      if (!published || privateChain.size == sizeBefore) return false
    }
    return true
  }

  private fun beginActiveRound(block: Block, context: BlockchainSystemNodeContext) {
    activeRoundId = block.hash
    activeRoundFirstPrivateBlockHash = block.hash
    activeRoundPrivateBlockCount = 0

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

  private fun logPrivateBlock(block: Block, context: BlockchainSystemNodeContext) {
    val roundId = activeRoundId ?: return
    if (!traceEventLogger.isEventTypeEnabled(SelfishMiningAttackRoundPrivateBlockTraceEvent.EVENT_TYPE)) return

    traceEventLogger.logEvent(
      SelfishMiningAttackRoundPrivateBlockTraceEvent(
        occurrenceTime = block.blockMinedTimestamp,
        roundId = roundId,
        attackerNodeId = context.id,
        blockHash = block.hash,
        privateBlockIndex = activeRoundPrivateBlockCount
      )
    )
  }

  /** Emit publication of attack material while retaining the active round. */
  private fun emitRoundReleased(reason: String) {
    val roundId = activeRoundId ?: return
    val firstHash = activeRoundFirstPrivateBlockHash ?: roundId

    if (traceEventLogger.isEventTypeEnabled(SelfishMiningAttackRoundReleasedTraceEvent.EVENT_TYPE)) {
      traceEventLogger.logEvent(
        SelfishMiningAttackRoundReleasedTraceEvent(
          occurrenceTime = simulationContext.systemClock.currentTime,
          roundId = roundId,
          attackerNodeId = traceEventLogger.logOrigin.id,
          firstPrivateBlockHash = firstHash,
          reason = reason
        )
      )
    }
  }

  /** End strategy ownership of a round without declaring its network outcome. */
  private fun releaseActiveRound(reason: String) {
    emitRoundReleased(reason)
    clearActiveRoundMetadata()
  }

  /** Explicitly record an unambiguous failed attempt before abandoning its private branch. */
  private fun abandonActiveRound(reason: String) {
    val roundId = activeRoundId ?: return
    val firstHash = activeRoundFirstPrivateBlockHash ?: roundId

    if (traceEventLogger.isEventTypeEnabled(SelfishMiningAttackRoundAbandonedTraceEvent.EVENT_TYPE)) {
      traceEventLogger.logEvent(
        SelfishMiningAttackRoundAbandonedTraceEvent(
          occurrenceTime = simulationContext.systemClock.currentTime,
          roundId = roundId,
          attackerNodeId = traceEventLogger.logOrigin.id,
          firstPrivateBlockHash = firstHash,
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
  }

  private fun hiddenLead(): Int = privateChain.size

  private fun resetPrivateState() {
    privateChain.clear()
    ownTipHash = null
    inTieState = false
    clearActiveRoundMetadata()
  }

  override fun dispatchEvent(event: Event) {
    // no-op
  }
}
