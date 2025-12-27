package org.palladiosimulator.blockchainsystems.threesim.utils

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block

/**
 * @property threshold The minimum number of nodes that must agree on a block for it to be considered valid.
 *
 * @author Davis Riedel
 */
class BlocksMap(
  private val threshold: Int
) {
  private val blocks: MutableMap<String, Pair<Block, MutableList<String>>> = mutableMapOf()

  private val timestamps: MutableMap<String, Long> = mutableMapOf()

  fun addNodeToBlock(block: Block, nodeId: String, timestamp: Long) {
    blocks
      .computeIfAbsent(block.hash) { Pair(block, mutableListOf()) }
      .second
      .add(nodeId)

    if (isBlockValid(block.hash)) {
      timestamps[block.hash] = timestamp
    }
  }

  fun removeNodeFromBlock(blockHash: String, nodeId: String) {
    blocks[blockHash]?.second?.remove(nodeId)

    if (!isBlockValid(blockHash)) {
      timestamps.remove(blockHash)
    }
  }

  fun isBlockValid(blockHash: String): Boolean {
    return (blocks[blockHash]?.second?.size ?: 0) >= threshold
  }

  fun getNumberOfBlocks(): Int {
    return blocks.size
  }

  fun getNumberOfValidBlocks(): Int {
    return blocks.count { it.value.second.size >= threshold }
  }

  fun getBlocks(): List<Pair<Block, Long>> {
    return blocks.map { Pair(it.value.first, timestamps[it.key]!!) }
  }

  fun getValidBlocks(): List<Pair<Block, Long>> {
    return blocks
      .filter { it.value.second.size >= threshold }
      .map { Pair(it.value.first, timestamps[it.key]!!) }
  }

  fun clear() {
    blocks.clear()
    timestamps.clear()
  }
}