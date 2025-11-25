package org.palladiosimulator.blockchainsystems.core.transaction

import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * Represents the result of a transaction selection process.
 *
 * @property transactions The set of transactions selected for inclusion in a block.
 * @property totalSize The total size of the selected transactions.
 *
 * @author Davis Riedel
 */
data class TransactionSelectionResult(
  val transactions: Set<Transaction>,
  val totalSize: Int,
) {}