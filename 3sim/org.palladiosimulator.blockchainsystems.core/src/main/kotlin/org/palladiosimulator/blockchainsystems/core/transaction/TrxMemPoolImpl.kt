package org.palladiosimulator.blockchainsystems.core.transaction

import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPool

/**
 * Implementation of a transaction memory pool (mempool) running on a blockchain node.
 *
 * @author Davis Riedel
 */
class TrxMemPoolImpl(
  val nodeId: String
) : BlockchainNodeObject(), TrxMemPool {

  /*
   * Flat-array transaction store with deterministic fee-rate priority. This avoids
   * one TreeSet node object per pending transaction while retaining exact lookup,
   * duplicate suppression and removal semantics.
   */
  private val mempool = CompactTransactionPriorityStore()

  private fun logTransactionStoredEvent(transaction: Transaction) {
    val event = TransactionStoredInMemPoolTraceEvent(
      simulationContext.systemClock.currentTime,
      transaction
    )

    traceEventLogger.logEvent(event)
  }

  private fun logTransactionRemovedEvent(transaction: Transaction) {
    val event = TransactionRemovedFromMemPoolTraceEvent(
      simulationContext.systemClock.currentTime,
      transaction
    )

    traceEventLogger.logEvent(event)
  }

  override fun dispatchEvent(event: Event) {
  }

  override fun storeTransaction(transaction: Transaction) {
    // TreeSet.add returns false for an already-present transaction. Do not emit a
    // "stored" trace event for a no-op: duplicate trace events distort monitoring
    // and unnecessarily allocate short-lived objects under high gossip load.
    if (mempool.add(transaction)) {
      logTransactionStoredEvent(transaction)
    }
  }

  override fun storeTransactions(transactions: Collection<Transaction>) {
    transactions.forEach { storeTransaction(it) }
  }

  override fun removeTransaction(transaction: Transaction) {
    // Likewise, record removal only when the transaction was actually present.
    val removed = mempool.remove(transaction.txId)
    if (removed != null) {
      logTransactionRemovedEvent(removed)
    }
  }

  override fun removeTransactions(transactions: Collection<Transaction>) {
    transactions.forEach { removeTransaction(it) }
  }

  override fun getTransactionById(txId: String): Transaction? {
    return mempool.get(txId)
  }

  override fun getTransactionsSortedByFeeRate(): List<Transaction> {
    return mempool.toSortedList()
  }

  override fun getTransactionsForBlock(maxTotalSize: Int): List<Transaction> {
    return mempool.selectPrefixByTotalSize(maxTotalSize)
  }
}