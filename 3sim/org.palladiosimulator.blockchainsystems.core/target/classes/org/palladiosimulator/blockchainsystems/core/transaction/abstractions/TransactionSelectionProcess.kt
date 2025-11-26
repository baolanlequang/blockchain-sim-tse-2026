package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionSelectionResult

interface TransactionSelectionProcess : Traceable {
  /**
   * Selects transactions for inclusion in a block.
   */
  fun selectTransactionsForBlock(context: BlockchainSystemNodeContext): TransactionSelectionResult
}