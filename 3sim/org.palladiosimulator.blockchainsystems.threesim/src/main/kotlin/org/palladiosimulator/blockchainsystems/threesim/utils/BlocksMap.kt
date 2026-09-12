package org.palladiosimulator.blockchainsystems.threesim.utils

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import java.util.BitSet

/**
 * Tracks how many validators currently classify each block with one block type.
 *
 * Validator membership used to be a HashSet<String> per block. A BitSet keyed by
 * one shared validator-index map represents exactly the same membership while
 * avoiding millions of HashSet node objects in long/high-N_V runs.
 */
class BlocksMap(
  private val threshold: Int,
  private val nodeIndexById: Map<String, Int>
) {
  private data class Observation(
    val block: Block,
    val members: BitSet,
    var thresholdTimestamp: Long? = null
  )

  private val blocks: MutableMap<String, Observation> = HashMap()

  /**
   * Add one validator observation and return true only when the block crosses
   * the validity threshold for the first time in its current membership state.
   */
  fun addNodeToBlock(block: Block, nodeId: String, timestamp: Long): Boolean {
    val nodeIndex = nodeIndexById[nodeId]
      ?: throw IllegalArgumentException("Unknown validator id: $nodeId")
    val observation = blocks.getOrPut(block.hash) {
      Observation(block, BitSet(nodeIndexById.size))
    }

    val wasValid = observation.members.cardinality() >= threshold
    observation.members.set(nodeIndex)
    val isValid = observation.members.cardinality() >= threshold
    if (isValid && !wasValid) observation.thresholdTimestamp = timestamp
    return isValid && !wasValid
  }

  fun removeNodeFromBlock(blockHash: String, nodeId: String): Boolean {
    val observation = blocks[blockHash] ?: return false
    val nodeIndex = nodeIndexById[nodeId]
      ?: throw IllegalArgumentException("Unknown validator id: $nodeId")

    val wasValid = observation.members.cardinality() >= threshold
    observation.members.clear(nodeIndex)
    val isValid = observation.members.cardinality() >= threshold
    if (!isValid) observation.thresholdTimestamp = null
    if (observation.members.isEmpty) blocks.remove(blockHash)
    return wasValid && !isValid
  }

  fun isBlockValid(blockHash: String): Boolean =
    (blocks[blockHash]?.members?.cardinality() ?: 0) >= threshold

  fun getNumberOfBlocks(): Int = blocks.size

  fun getNumberOfValidBlocks(): Int = blocks.values.count { it.members.cardinality() >= threshold }

  fun getBlocks(): List<Pair<Block, Long>> = blocks.values.mapNotNull { observation ->
    observation.thresholdTimestamp?.let { Pair(observation.block, it) }
  }

  fun getValidBlocks(): List<Pair<Block, Long>> = blocks.values.mapNotNull { observation ->
    if (observation.members.cardinality() < threshold) return@mapNotNull null
    observation.thresholdTimestamp?.let { Pair(observation.block, it) }
  }

  fun clear() {
    blocks.clear()
  }
}
