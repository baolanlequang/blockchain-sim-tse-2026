package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionSelectionResult
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSelectionProcess

/**
 * This class implements a simple transaction selection process,
 * that selects transactions based on their fee rate until the maximum block size is reached.
 *
 * @author Davis Riedel
 */
class ThreesimTransactionSelectionProcess(
  private val maxBlockSize: Int // in byte
) : BlockchainNodeObject(), TransactionSelectionProcess {
  override fun selectTransactionsForBlock(
    context: BlockchainSystemNodeContext
  ): TransactionSelectionResult {
    var currentBlockSize = 0
    val transactions = context.trxMemPool.getTransactionsSortedByFeeRate()
      .takeWhile {
        val newBlockSize = currentBlockSize + it.size
        val canBeAdded = newBlockSize <= maxBlockSize
        if (canBeAdded) currentBlockSize = newBlockSize
        canBeAdded
      }
    return TransactionSelectionResult(
      transactions = transactions.toSet(),
      totalSize = currentBlockSize
    )
  }

  override fun dispatchEvent(event: Event) {
  }
}