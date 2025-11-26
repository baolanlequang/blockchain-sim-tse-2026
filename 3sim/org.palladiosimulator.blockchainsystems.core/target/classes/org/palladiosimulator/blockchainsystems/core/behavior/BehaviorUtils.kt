package org.palladiosimulator.blockchainsystems.core.behavior

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockAppendingResultType
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext

/**
 * The `BehaviorUtils` class provides common behavior methods used by behavior implementations.
 *
 * @author Yannik Sproll, Davis Riedel
 */
object BehaviorUtils {
  /**
   * Appends the specified block to the blockchain, if possible.
   * If there is no matching previous block, the block is stored in the orphan block pool.
   * If there are descending blocks to the current block, these blocks are also appended to the blockchain.
   * The method returns a value that indicates if the blockchain has a new longest branch.
   *
   * @param block   the block to add to the blockchain
   * @param context the context of a blockchain system node
   * @return true if the blockchain has a new longest branch, false otherwise
   */
  fun appendBlockToBlockchain(block: Block, context: BlockchainSystemNodeContext): Boolean {
    val blockAppendingResult = context.blockchain.appendBlock(block)

    when (blockAppendingResult.type) {
      BlockAppendingResultType.Appended -> {
        val appendedBlockType = blockAppendingResult.blockType

        val orphanBlocks = context.orphanBlockPool
          .getBlocksByPreviousBlockHash(block.hash)

        var hasNewLongestBranch = (appendedBlockType == BlockType.IncludedBlock)

        orphanBlocks.forEach { orphanBlock ->
          val hasNewLongestBranchInner = appendBlockToBlockchain(orphanBlock, context)
          if (!hasNewLongestBranch) hasNewLongestBranch = hasNewLongestBranchInner
        }

        return appendedBlockType == BlockType.IncludedBlock
      }

      BlockAppendingResultType.NotAppendedBecauseOrphanBlock -> {
        context.orphanBlockPool.storeBlock(block)
        return false
      }

      else -> return false
    }
  }
}