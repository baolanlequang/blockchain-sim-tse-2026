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
 * Honest validating-node behavior used in EVERY execution, with or without
 * adversarial nodes, so that the honest protocol rules do not change with f_A.
 *
 * Rules
 * - Every valid block that attaches to the local block tree is relayed exactly once.
 *   Orphans are relayed only after their parent arrives (a node cannot serve a block
 *   that is not in its chain, so announcing it earlier would stall propagation).
 * - Longest chain wins. Mining restarts whenever the block currently being extended
 *   is no longer a tip of a longest chain (also after recursive orphan unlocking).
 * - Ties between honest tips: first seen (keep mining on the current tip).
 * - Ties between an attacker tip and an honest tip (Eyal & Sirer's gamma): whenever
 *   such a tie arises or changes, the node mines on the attacker tip with
 *   probability [gamma]. With no adversarial nodes this rule never applies.
 */
class GammaAwareHonestBlockchainSystemNodeBehavior @JvmOverloads constructor(
  private val attackerNodeIds: Set<String>,
  private val gamma: Double,
  private val randomGenerator: RandomGenerator = RandomGenerator.of("Random")
) : BlockchainNodeObject(), BlockchainSystemNodeBehavior {

  /** Block the mining process is currently extending. */
  private var miningTipHash: String? = null

  /** Set when a new attacker/honest tie must be resolved with a gamma draw. */
  private var gammaDrawPending: Boolean = false

  override fun onNodeInitialized(context: BlockchainSystemNodeContext) {
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

    val outcome = BehaviorUtils.appendBlockToBlockchainDetailed(block, context)
    if (outcome.isValidAttachableBlock()) {
      context.blockPropagationStrategy.distribute(block)
    }

    val tips = context.blockchain.getLastBlocksOfLongestChains()
    val currentTip = miningTipHash
    when {
      currentTip == null || tips.none { it.hash == currentTip } -> {
        gammaDrawPending = isMixedTie(tips)
        context.miningProcess.restartMining()
      }
      outcome == AppendOutcome.FORKING && isMixedTie(tips) -> {
        gammaDrawPending = true
        context.miningProcess.restartMining()
      }
    }
  }

  override fun onBlockMined(block: Block, context: BlockchainSystemNodeContext) {
    val outcome = BehaviorUtils.appendBlockToBlockchainDetailed(block, context)
    if (outcome.isValidAttachableBlock()) {
      context.blockPropagationStrategy.distribute(block)
    }
  }

  private fun AppendOutcome.isValidAttachableBlock(): Boolean =
    this == AppendOutcome.INCLUDED || this == AppendOutcome.FORKING || this == AppendOutcome.STALE

  private fun isMixedTie(tips: Collection<Block>): Boolean =
    tips.size > 1 &&
      tips.any { it.originId in attackerNodeIds } &&
      tips.any { it.originId !in attackerNodeIds }

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
    val tips = context.blockchain.getLastBlocksOfLongestChains().sortedBy { it.hash }
    val current = miningTipHash

    val chosen = if (gammaDrawPending && isMixedTie(tips)) {
      val attackerTips = tips.filter { it.originId in attackerNodeIds }
      val honestTips = tips.filter { it.originId !in attackerNodeIds }
      val pool = if (randomGenerator.nextDouble() < gamma) attackerTips else honestTips
      pool[randomGenerator.nextInt(pool.size)]
    } else {
      // First seen: stay on the current tip while it is still a longest tip.
      tips.firstOrNull { it.hash == current } ?: tips.first()
    }

    gammaDrawPending = false
    miningTipHash = chosen.hash
    return chosen.hash
  }

  override fun dispatchEvent(event: Event) {
    // no-op
  }
}
