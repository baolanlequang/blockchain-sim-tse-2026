package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

interface TransactionSelectionProcessFactory {
  /**
   * Creates a new instance of [TransactionSelectionProcess].
   *
   * @return a new [TransactionSelectionProcess] instance
   */
  fun createTransactionSelectionProcess(nodeId: String): TransactionSelectionProcess
}