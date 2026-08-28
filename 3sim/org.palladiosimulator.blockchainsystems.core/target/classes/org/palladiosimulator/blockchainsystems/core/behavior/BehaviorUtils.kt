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

  /**
   * Appends the specified block to the blockchain, if possible, returning the detailed
   * [AppendOutcome] rather than just whether the chain grew longer. This lets callers (e.g.
   * selfish-mining behaviors) distinguish a block that merely forks the chain (creates or
   * continues a tie) from one that extends the longest chain, or one that is stale/orphaned.
   *
   * @param block   the block to add to the blockchain
   * @param context the context of a blockchain system node
   * @return the detailed outcome of the append operation
   */
  fun appendBlockToBlockchainDetailed(
    block: Block,
    context: BlockchainSystemNodeContext,
    visited: MutableSet<String> = mutableSetOf()
  ): AppendOutcome {
    if (!visited.add(block.hash)) return AppendOutcome.ALREADY_APPENDED

    val blockAppendingResult = context.blockchain.appendBlock(block)

    return when (blockAppendingResult.type) {
      BlockAppendingResultType.Appended -> {
        val orphanBlocks = context.orphanBlockPool
          .getBlocksByPreviousBlockHash(block.hash)

        orphanBlocks.forEach { orphanBlock ->
          val orphanOutcome = appendBlockToBlockchainDetailed(orphanBlock, context, visited)
          if (orphanOutcome == AppendOutcome.INCLUDED || orphanOutcome == AppendOutcome.FORKING) {
            context.blockPropagationStrategy.distribute(orphanBlock)
          }
        }

        when (blockAppendingResult.blockType) {
          BlockType.IncludedBlock -> AppendOutcome.INCLUDED
          BlockType.ForkingBlock -> AppendOutcome.FORKING
          BlockType.StaleBlock -> AppendOutcome.STALE
          else -> AppendOutcome.NOT_APPENDED
        }
      }

      BlockAppendingResultType.NotAppendedBecauseOrphanBlock -> {
        context.orphanBlockPool.storeBlock(block)
        AppendOutcome.ORPHAN
      }

      BlockAppendingResultType.AlreadyAppended -> AppendOutcome.ALREADY_APPENDED

      else -> AppendOutcome.NOT_APPENDED
    }
  }
}