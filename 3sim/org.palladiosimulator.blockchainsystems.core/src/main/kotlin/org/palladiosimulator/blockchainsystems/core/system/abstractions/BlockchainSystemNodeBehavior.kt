package org.palladiosimulator.blockchainsystems.core.system.abstractions

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * The [BlockchainSystemNodeBehavior] interfaces defines the behavior of
 * a blockchain system node. It provides callbacks to handle the various events
 * that can occur within a blockchain system node.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface BlockchainSystemNodeBehavior : Traceable {
  /**
   * This callback is invoked when the node is initialized.
   *
   * @param context the context of the blockchain system node
   */
  fun onNodeInitialized(context: BlockchainSystemNodeContext)

  /**
   * This callback is invoked when the node receives a transaction from one of its neighbors.
   *
   * @param transaction the received transaction
   * @param context     the context of the blockchain system node
   */
  fun onTransactionReceived(transaction: Transaction, context: BlockchainSystemNodeContext)

  /**
   * This callback is invoked when the node receives a block from one of its neighbors.
   *
   * @param block   the received block
   * @param context the context of the blockchain system node
   */
  fun onBlockReceived(block: Block, context: BlockchainSystemNodeContext)

  /**
   * This callback is invoked when the node has finished the validation of a block.
   *
   * @param block   the validated block
   * @param isValid indicates if the block is valid
   * @param context the context of the blockchain system node
   */
  fun onBlockValidated(block: Block, isValid: Boolean, context: BlockchainSystemNodeContext)

  /**
   * This callback is invoked when the node has finished mining a block.
   *
   * @param block   the block that was mined
   * @param context the context of the blockchain system node
   */
  fun onBlockMined(block: Block, context: BlockchainSystemNodeContext)

  /**
   * This callback is invoked when a block instance must be created.
   *
   * @param blockMinedAt      the time at which the block was mined
   * @param blockHash         the hash of the mined block
   * @param previousBlockHash the hash of predecessor to the mined block
   * @param context           the context of the blockchain system node
   * @return the block instance
   */
  fun onCreatingBlock(blockMinedAt: Long, previousBlockHash: String, context: BlockchainSystemNodeContext): Block

  /**
   * This callback is invoked when a previous block for the next block mining must be selected.
   *
   * @param context the context of the blockchain system node
   * @return the hash of the selected previous block
   */
  fun onPreviousBlockSelection(context: BlockchainSystemNodeContext): String
}