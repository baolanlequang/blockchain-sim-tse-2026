package org.palladiosimulator.blockchainsystems.core.system.abstractions

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockFactory
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidator
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegion
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategy
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPool
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSelectionProcess

/**
 * The [BlockchainSystemNodeContext] interface represents the
 * context of a blockchain system node.
 * It provides access to the components of a blockchain system node.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface BlockchainSystemNodeContext {
  /**
   * Returns an identifier of the blockchain system node.
   *
   * @return blockchain system node identifier
   */
  val id: String

  /**
   * Returns an abstraction of the strategy used by the blockchain system node
   * to propagate the blocks through the network.
   *
   * @return the strategy used to propagate blocks
   */
  val blockPropagationStrategy: PropagationStrategy<Block>

  /**
   * Returns an abstraction of the strategy used by the blockchain system node
   * to propagate transactions through the network.
   *
   * @return the strategy used to propagate blocks
   */
  val transactionPropagationStrategy: PropagationStrategy<Transaction>

  /**
   * Returns an abstraction of the P2P network used by the blockchain system node
   * to communicate with other nodes in the network.
   *
   * @return P2P network abstraction
   */
  val trxMemPool: TrxMemPool

  /**
   * Returns an abstraction of the underlying P2P network.
   *
   * @return P2P network abstraction
   */
  val networkInterface: NodeP2PNetworkInterface

  /**
   * Returns the resource power of the blockchain system node.
   * This is a measure of the computational resources available to the node.
   *
   * @return resource power of the blockchain system node
   */
  val resourcePower: Double

  /**
   * Returns an abstraction of the mining process used by the blockchain system node
   * to mine blocks.
   *
   * @return mining process abstraction
   */
  val miningProcess: MiningProcess

  /**
   * Returns an abstraction of the transaction selection process used by the blockchain system node
   * to select transactions for inclusion in a block.
   *
   * @return transaction selection process abstraction
   */
  val transactionSelectionProcess: TransactionSelectionProcess

  /**
   * Returns an abstraction of the blockchain data structure of the blockchain system node.
   *
   * @return the blockchain abstraction
   */
  val blockchain: Blockchain

  /**
   * Returns an abstraction of the orphan block pool used by the blockchain system node
   * to store orphan blocks.
   *
   * @return orphan block pool abstraction
   */
  val orphanBlockPool: OrphanBlockPool

  /**
   * Returns an abstraction of the block factory used by the blockchain system node
   * to create blocks.
   *
   * @return block factory abstraction
   */
  val blockFactory: BlockFactory

  /**
   * Returns an abstraction of the block validator used by the blockchain system node
   * to validate blocks.
   *
   * @return block validator abstraction
   */
  val blockValidator: BlockValidator

  /**
   * Returns the geographical region of the blockchain system node.
   *
   * @return the geographical region of the blockchain system node
   */
  val geographicalRegion: GeographicalRegion
}