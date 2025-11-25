package org.palladiosimulator.blockchainsystems.core.block.abstractions

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Taggable
import org.palladiosimulator.blockchainsystems.core.propagation.Propagatable
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * The `Block` interface represents a block of a blockchain.
 * It contains a hash that uniquely identifies it, and hash that identifies the previous block.
 * It also contains an identifier of the node that mined the block and the timestamp when the block was mined.
 *
 * @author Yannik Sproll
 */
interface Block : Taggable, Propagatable {
  /**
   * Returns the hash that uniquely identifies the block.
   *
   * @return the block identifier hash
   */
  val hash: String

  /**
   * Returns the hash that uniquely identifies the previous block.
   *
   * Null for the genesis block.
   *
   * @return the previous block identifier hash
   */
  val previousHash: String?

  /**
   * Returns an identifier of the node that mined the block.
   *
   * Null for the genesis block.
   *
   * @return the miner node identifier
   */
  val originId: String?

  /**
   * Returns the size of the block in bytes.
   *
   * @return block size in bytes
   */
  val size: Int

  /**
   * Returns the timestamp when the block was mined.
   *
   * @return block mining timestamp
   */
  val blockMinedTimestamp: Long

  /**
   * Returns the set of transactions included in the block.
   *
   * @return set of transactions in the block
   */
  val transactions: Set<Transaction>
}