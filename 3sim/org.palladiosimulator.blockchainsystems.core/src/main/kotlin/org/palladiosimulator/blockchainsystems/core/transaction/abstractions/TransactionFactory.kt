package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

/**
 * Factory for creating instances of [Transaction].
 *
 * @author Davis Riedel
 */
interface TransactionFactory {
  /**
   * Creates a new transaction with the given parameters.
   *
   * @param senderId The ID of the sender of the transaction.
   * @param recipientId The ID of the recipient of the transaction.
   * @param amount The amount of the transaction.
   * @param fee The transaction fee.
   * @return A new [Transaction] instance.
   */
  fun createTransaction(
    txId: String,
    size: Int,
    creationTime: Long,
    senderId: String,
    recipientId: String,
    amount: Double,
    fee: Double
  ): Transaction
}