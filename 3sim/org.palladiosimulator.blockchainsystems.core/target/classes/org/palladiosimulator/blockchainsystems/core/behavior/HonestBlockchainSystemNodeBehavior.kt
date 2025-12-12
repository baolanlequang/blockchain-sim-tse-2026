package org.palladiosimulator.blockchainsystems.core.behavior

import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import java.util.*

/**
 * This behavior represents a node in the blockchain system that behaves honestly.
 *
 * @author Yannik Sproll
 */
class HonestBlockchainSystemNodeBehavior : BlockchainNodeObject(), BlockchainSystemNodeBehavior {
  override fun onBlockReceived(
    block: Block,
    context: BlockchainSystemNodeContext
  ) {
    context.blockValidator.validateBlock(block)
  }

  override fun onTransactionReceived(
    transaction: Transaction,
    context: BlockchainSystemNodeContext
  ) {
    context.trxMemPool.storeTransaction(transaction)
    context.transactionPropagationStrategy.distribute(transaction)
  }

  override fun onBlockValidated(block: Block, isValid: Boolean, context: BlockchainSystemNodeContext) {
    if (!isValid) return

    // Remove transactions included in the block from the mempool
    context.trxMemPool.removeTransactions(block.transactions)

    // Append the block to the blockchain
    val hasNewLongestChain = BehaviorUtils.appendBlockToBlockchain(block, context)
    if (hasNewLongestChain) {
      context.miningProcess.restartMining()
    }

    // Propagate the valid block to peers
    context.blockPropagationStrategy.distribute(block)
  }

  override fun onBlockMined(block: Block, context: BlockchainSystemNodeContext) {
    BehaviorUtils.appendBlockToBlockchain(block, context)
    context.blockPropagationStrategy.distribute(block)
  }

  override fun onCreatingBlock(
    blockMinedAt: Long,
    previousBlockHash: String,
    context: BlockchainSystemNodeContext
  ): Block {
    // Select transactions to include in the block
    val selectedTrxsResult = context.transactionSelectionProcess.selectTransactionsForBlock(context)

    // Remove the selected transactions from the mempool
    context.trxMemPool.removeTransactions(selectedTrxsResult.transactions)

    // Create a new block with the selected transactions
    return context.blockFactory.createBlock(
      UUID.randomUUID().toString(),
      previousBlockHash,
      context.id,
      blockMinedAt,
      selectedTrxsResult.totalSize,
      selectedTrxsResult.transactions,
    )
  }

  override fun onPreviousBlockSelection(context: BlockchainSystemNodeContext): String {
    val blocks = context.blockchain.getLastBlocksOfLongestChains()
    return blocks.stream().findFirst().get().hash
  }

  override fun onNodeInitialized(context: BlockchainSystemNodeContext) {
    context.miningProcess.startMining()
  }

  override fun dispatchEvent(event: Event) {
  }
}