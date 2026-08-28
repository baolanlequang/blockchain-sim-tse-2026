package org.palladiosimulator.blockchainsystems.threesim.utils

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block

/** Tracks how many validators currently classify each block with one block type. */
class BlocksMap(private val threshold: Int) {
  private val blocks: MutableMap<String, Pair<Block, MutableSet<String>>> = mutableMapOf()
  private val timestamps: MutableMap<String, Long> = mutableMapOf()

  /**
   * Add one validator observation and return true only when the block crosses
   * the validity threshold for the first time in its current membership state.
   */
  fun addNodeToBlock(block: Block, nodeId: String, timestamp: Long): Boolean {
    val wasValid = isBlockValid(block.hash)
    blocks.computeIfAbsent(block.hash) { Pair(block, mutableSetOf()) }.second.add(nodeId)
    val isValid = isBlockValid(block.hash)
    if (isValid && !wasValid) timestamps[block.hash] = timestamp
    return isValid && !wasValid
  }

  fun removeNodeFromBlock(blockHash: String, nodeId: String): Boolean {
    val wasValid = isBlockValid(blockHash)
    blocks[blockHash]?.second?.remove(nodeId)
    val isValid = isBlockValid(blockHash)
    if (!isValid) timestamps.remove(blockHash)
    return wasValid && !isValid
  }

  fun isBlockValid(blockHash: String): Boolean =
    (blocks[blockHash]?.second?.size ?: 0) >= threshold

  fun getNumberOfBlocks(): Int = blocks.size

  fun getNumberOfValidBlocks(): Int = blocks.count { it.value.second.size >= threshold }

  fun getBlocks(): List<Pair<Block, Long>> = blocks.mapNotNull { (hash, value) ->
    timestamps[hash]?.let { Pair(value.first, it) }
  }

  fun getValidBlocks(): List<Pair<Block, Long>> = blocks
    .filter { it.value.second.size >= threshold }
    .mapNotNull { (hash, value) -> timestamps[hash]?.let { Pair(value.first, it) } }

  fun clear() {
    blocks.clear()
    timestamps.clear()
  }
}
