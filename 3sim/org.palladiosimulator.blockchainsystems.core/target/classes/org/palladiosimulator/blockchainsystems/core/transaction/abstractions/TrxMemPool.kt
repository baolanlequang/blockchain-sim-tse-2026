package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable

/**
 * This interface represents a transaction memory pool (mempool) that stores transactions
 * before they are included in a block.
 *
 * @author Davis Riedel
 */
interface TrxMemPool : Traceable {
  /**
   * Stores a transaction in the mempool.
   *
   * @param transaction The transaction to store.
   */
  fun storeTransaction(transaction: Transaction)

  /**
   * Stores multiple transactions in the mempool.
   *
   * @param transactions The transactions to store.
   */
  fun storeTransactions(transactions: Collection<Transaction>)

  /**
   * Removes a transaction from the mempool.
   *
   * @param transaction The transaction to remove.
   */
  fun removeTransaction(transaction: Transaction)

  /**
   * Removes multiple transactions from the mempool.
   *
   * @param transactions The transactions to remove.
   */
  fun removeTransactions(transactions: Collection<Transaction>)

  /**
   * Gets a transaction with a given ID from the mempool.
   *
   * @param txId The ID of the transaction
   */
  fun getTransactionById(txId: String): Transaction?

  /**
   * Get all transactions in the mempool sorted by their fee rate in descending order.
   */
  fun getTransactionsSortedByFeeRate(): List<Transaction>
}