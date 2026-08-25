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
 * Honest-node behavior that models the network-propagation-advantage parameter gamma (γ) from
 * Eyal & Sirer's selfish-mining analysis: the fraction of the honest hash power that ends up
 * mining on the attacker's block rather than another honest block when the chain tips are tied.
 *
 * Without this, honest nodes would resolve ties uniformly at random between all tips (including
 * the attacker's), which corresponds to gamma == (number of attacker tips / number of tied tips)
 * instead of a configurable, paper-accurate gamma.
 *
 * @author Yannik Sproll (ported)
 */
class GammaAwareHonestBlockchainSystemNodeBehavior @JvmOverloads constructor(
  private val attackerNodeIds: Set<String>,
  private val gamma: Double,
  private val randomGenerator: RandomGenerator = RandomGenerator.of("Random")
) : BlockchainNodeObject(), BlockchainSystemNodeBehavior {

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
    if (outcome == AppendOutcome.INCLUDED || outcome == AppendOutcome.FORKING) {
      context.trxMemPool.removeTransactions(block.transactions)
      context.miningProcess.restartMining()
      context.blockPropagationStrategy.distribute(block)
    }
  }

  override fun onBlockMined(block: Block, context: BlockchainSystemNodeContext) {
    val outcome = BehaviorUtils.appendBlockToBlockchainDetailed(block, context)
    if (outcome == AppendOutcome.INCLUDED || outcome == AppendOutcome.FORKING) {
      context.trxMemPool.removeTransactions(block.transactions)
      context.blockPropagationStrategy.distribute(block)
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
    val tips = context.blockchain.getLastBlocksOfLongestChains()

    if (tips.size <= 1) {
      return tips.sortedBy { it.hash }.first().hash
    }

    val attackerTips = tips.filter { attackerNodeIds.contains(it.originId) }
    val honestTips = tips.filter { !attackerNodeIds.contains(it.originId) }

    if (attackerTips.isEmpty() || honestTips.isEmpty()) {
      return tips.sortedBy { it.hash }.first().hash
    }

    val chosen = if (randomGenerator.nextDouble() < gamma) {
      val ordered = attackerTips.sortedBy { it.hash }
      ordered[randomGenerator.nextInt(ordered.size)]
    } else {
      val ordered = honestTips.sortedBy { it.hash }
      ordered[randomGenerator.nextInt(ordered.size)]
    }

    return chosen.hash
  }

  override fun dispatchEvent(event: Event) {
    // no-op
  }
}
