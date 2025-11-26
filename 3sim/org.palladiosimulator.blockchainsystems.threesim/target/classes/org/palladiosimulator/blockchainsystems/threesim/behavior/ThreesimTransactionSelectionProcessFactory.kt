package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSelectionProcess
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSelectionProcessFactory

class ThreesimTransactionSelectionProcessFactory(
  private val maxBlockSize: Int // in byte
) : TransactionSelectionProcessFactory {
  override fun createTransactionSelectionProcess(nodeId: String): TransactionSelectionProcess {
    return ThreesimTransactionSelectionProcess(maxBlockSize)
  }
}