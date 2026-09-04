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
        reconcileMempoolAfterAppend(block, blockAppendingResult, context)
        val appendedBlockType = blockAppendingResult.blockType

        val orphanBlocks = context.orphanBlockPool
          .getBlocksByPreviousBlockHash(block.hash)

        // A newly arrived ancestor can unlock one or more previously orphaned
        // descendants. If any recursively appended descendant extends the
        // longest chain, callers must restart mining on that new tip as well.
        var hasNewLongestBranch = (appendedBlockType == BlockType.IncludedBlock)

        orphanBlocks.forEach { orphanBlock ->
          val hasNewLongestBranchInner = appendBlockToBlockchain(orphanBlock, context)
          if (!hasNewLongestBranch) hasNewLongestBranch = hasNewLongestBranchInner
        }

        return hasNewLongestBranch
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
        reconcileMempoolAfterAppend(block, blockAppendingResult, context)
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

  /**
   * Keep the node mempool consistent with local longest-chain changes.
   *
   * Transactions from a newly accepted longest/forking block are removed. If a
   * fork is later resolved, transactions unique to blocks that became stale are
   * restored, while transactions already present anywhere on a surviving
   * longest branch remain out of the mempool. Re-storing locally does not restart gossip because
   * TransactionPropagationStrategy keeps persistent transaction IDs.
   */
  private fun reconcileMempoolAfterAppend(
    appendedBlock: Block,
    result: org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockAppendingResult,
    context: BlockchainSystemNodeContext
  ) {
    val toRestore = if (result.blocksBecameStale.isEmpty()) {
      emptyList()
    } else {
      // Restore only transactions absent from every currently longest branch.
      // Looking only at blocks whose type changed in this append is insufficient:
      // the same transaction may already exist in an older surviving block.
      val activeLongestChainTxIds = context.blockchain.getLongestChains()
        .asSequence()
        .flatten()
        .flatMap { it.transactions.asSequence() }
        .map { it.txId }
        .toHashSet()

      result.blocksBecameStale
        .asSequence()
        .flatMap { it.transactions.asSequence() }
        .filter { it.txId !in activeLongestChainTxIds }
        .distinctBy { it.txId }
        .toList()
    }

    if (toRestore.isNotEmpty()) {
      context.trxMemPool.storeTransactions(toRestore)
    }

    if (
      result.blockType == BlockType.IncludedBlock ||
      result.blockType == BlockType.ForkingBlock
    ) {
      context.trxMemPool.removeTransactions(appendedBlock.transactions)
    }

    if (result.blocksBecameIncluded.isNotEmpty()) {
      context.trxMemPool.removeTransactions(
        result.blocksBecameIncluded.flatMap { it.transactions }.distinctBy { it.txId }
      )
    }
  }
}