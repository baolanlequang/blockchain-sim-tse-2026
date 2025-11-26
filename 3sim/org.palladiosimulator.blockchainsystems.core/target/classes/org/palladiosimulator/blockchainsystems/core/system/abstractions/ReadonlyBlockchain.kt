package org.palladiosimulator.blockchainsystems.core.system.abstractions

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block

/**
 * The `ReadonlyBlockchain` interface represents
 * a readonly blockchain data structure of a blockchain system node.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface ReadonlyBlockchain {
  /**
   * Returns all blocks in the blockchain
   *
   * @return blocks in the blockchain
   */
  fun getBlocks(): Set<Block>

  /**
   * Returns a set of the latest blocks of the blockchain.
   * If the blockchain is forked, this set contains more than one block.
   * Otherwise, it contains only one block.
   *
   * @return set of the latest blocks in the blockchain
   */
  fun getLastBlocksOfLongestChains(): Set<Block>

  /**
   * Returns all blocks at the given position.
   * If there are no stale blocks at this position in the blockchain this set only contains one block.
   *
   * @return set of blocks at the specified position
   */
  fun getBlocksAtPosition(position: Long): Set<Block>

  /**
   * Returns the position of the block in the blockchain, or -1 if the block is not stored in the blockchain
   *
   * @param block the block whose position is requested
   * @return the position of the block or -1
   */
  fun getPositionOfBlock(block: Block): Long

  /**
   * Returns the length of the blockchain.
   *
   * @return length of the blockchain
   */
  fun getLength(): Long

  /**
   * Check if there is a block with the specified hash in the blockchain.
   *
   * @param hash the hash of the block to check
   * @return true if the block is contained in the blockchain
   */
  fun hasBlockWithHash(hash: String): Boolean

  /**
   * Returns the block with the specified hash if it is contained in the blockchain.
   * Otherwise, it returns null.
   *
   * @param hash the block identifier hash
   * @return the block with specified hash as identifier
   */
  fun getBlock(hash: String): Block?

  /**
   * Returns all successor blocks the immediately follow the block with the specified hash if the block is contained in the blockchain.
   * Otherwise, returns null.
   *
   * @param hash the block identifier hash
   * @return set of all immediate successor blocks
   */
  fun getImmediateSuccessorBlocks(hash: String): Set<Block>?

  /**
   * Returns all successor blocks of the block with the specified hash if the block is contained in the blockchain.
   * Otherwise, returns null.
   *
   * @param hash the block identifier hash
   * @return set of all successor blocks
   */
  fun getSuccessorBlocks(hash: String): Set<Block>?

  /**
   * Returns the number of successors in the longest chain that is a successor to the block of the specified hash.
   * If the block is not contained in the blockchain, returns 0.
   *
   * @param hash the block identifier hash
   * @return length of the longest successor chain
   */
  fun getLongestSuccessorChainLength(hash: String): Long

  /**
   * Returns the longest chain of blocks in the blockchain as an ArrayList.
   *
   * @return list of blocks in the longest chain
   */
  fun getLongestChains(): List<ArrayList<Block>>
}