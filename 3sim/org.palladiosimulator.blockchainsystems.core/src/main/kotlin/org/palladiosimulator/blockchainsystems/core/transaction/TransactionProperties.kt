package org.palladiosimulator.blockchainsystems.core.transaction

/**
 * Represents the properties of a transaction which are randomly generated
 *
 * @property size The size of the transaction in bytes.
 * @property fee The transaction fee for this transaction.
 * @property amount The amount of cryptocurrency being transferred in this transaction.
 */
class TransactionProperties(
  val size: Int,
  val fee: Double,
  val amount: Double,
)