package org.palladiosimulator.blockchainsystems.core.block.abstractions

import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * Interface for a factory that produces instances of `Block`.
 *
 * @author Yannik Sproll
 */
interface BlockFactory {
  /**
   * Creates an instance of @code{Block} the represents a genesis block.
   *
   * @return @code{Block} instance representing a genesis block
   */
  fun createGenesisBlock(): Block

  /**
   * Creates a new block with the specified data.
   *
   * @param hash                the hash of the block
   * @param previousHash        the hash of the previous block
   * @param originId            the id of the origin blockchain system node
   * @param blockMinedTimestamp the timestamp at which the block was mined
   * @param transactions        the set of transactions included in the block
   * @param blockSize           the size of the block in bytes
   * @param transactions        the set of transactions included in the block
   *
   * @return a @code{Block} instance
   */
  fun createBlock(
    hash: String,
    previousHash: String,
    originId: String,
    blockMinedTimestamp: Long,
    blockSize: Int,
    transactions: Set<Transaction>
  ): Block

  /**
   * Creates a new block with the specified data.
   *
   * @param hash                the hash of the block
   * @param previousHash        the hash of the previous block
   * @param originId            the id of the origin blockchain system node
   * @param blockMinedTimestamp the timestamp at which the block was mined
   * @param blockSize           the size of the block
   * @param transactions        the set of transactions included in the block
   * @param tags                tags describing the block
   * @return a @code{Block} instance
   */
  fun createBlock(
    hash: String,
    previousHash: String,
    originId: String,
    blockMinedTimestamp: Long,
    blockSize: Int,
    transactions: Set<Transaction>,
    tags: Set<String>
  ): Block
}