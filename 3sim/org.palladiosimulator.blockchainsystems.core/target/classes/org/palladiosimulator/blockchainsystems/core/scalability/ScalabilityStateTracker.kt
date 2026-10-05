package org.palladiosimulator.blockchainsystems.core.scalability

/**
 * Constant-space accounting for the main scientifically live simulator states.
 *
 * Limits are disabled by default. When enabled, admission checks run before a
 * new entry is inserted into the corresponding data structure, so an execution
 * terminates as scientifically incomplete before a resize/allocation can push
 * the JVM into OutOfMemoryError. No model state is silently discarded.
 */
class ScalabilityStateTracker {
  data class Snapshot(
    val currentTransactionKnowledgeEntries: Long,
    val maxTransactionKnowledgeEntriesObserved: Long,
    val maxTransactionKnowledgeEntriesPerNodeObserved: Long,
    val currentBlockKnowledgeEntries: Long,
    val maxBlockKnowledgeEntriesObserved: Long,
    val maxBlockKnowledgeEntriesPerNodeObserved: Long,
    val currentMempoolEntries: Long,
    val maxMempoolEntriesObserved: Long,
    val maxMempoolEntriesPerNodeObserved: Long,
    val currentMeasurementTransactionEntries: Long,
    val maxMeasurementTransactionEntriesObserved: Long,
    val maxTransactionKnowledgeEntries: Long,
    val maxBlockKnowledgeEntries: Long,
    val maxMempoolEntries: Long,
    val maxMeasurementTransactionEntries: Long
  )

  private var transactionKnowledgeEntries = 0L
  private var maxTransactionKnowledgeEntriesObserved = 0L
  private var maxTransactionKnowledgeEntriesPerNodeObserved = 0L

  private var blockKnowledgeEntries = 0L
  private var maxBlockKnowledgeEntriesObserved = 0L
  private var maxBlockKnowledgeEntriesPerNodeObserved = 0L

  private var mempoolEntries = 0L
  private var maxMempoolEntriesObserved = 0L
  private var maxMempoolEntriesPerNodeObserved = 0L

  private var measurementTransactionEntries = 0L
  private var maxMeasurementTransactionEntriesObserved = 0L

  private val maxTransactionKnowledgeEntries: Long =
    java.lang.Long.getLong("threesim.maxTransactionKnowledgeEntries", 0L).coerceAtLeast(0L)
  private val maxBlockKnowledgeEntries: Long =
    java.lang.Long.getLong("threesim.maxBlockKnowledgeEntries", 0L).coerceAtLeast(0L)
  private val maxMempoolEntries: Long =
    java.lang.Long.getLong("threesim.maxMempoolEntries", 0L).coerceAtLeast(0L)
  private val maxMeasurementTransactionEntries: Long =
    java.lang.Long.getLong("threesim.maxMeasurementTransactionEntries", 0L).coerceAtLeast(0L)

  fun beforeTransactionKnowledgeEntryAdded() {
    enforceNext(
      transactionKnowledgeEntries,
      maxTransactionKnowledgeEntries,
      "WORKLOAD_LIMIT_TRANSACTION_KNOWLEDGE_ENTRIES"
    )
  }

  fun onTransactionKnowledgeEntryAdded(nodeEntries: Int) {
    transactionKnowledgeEntries++
    maxTransactionKnowledgeEntriesObserved = maxOf(
      maxTransactionKnowledgeEntriesObserved,
      transactionKnowledgeEntries
    )
    maxTransactionKnowledgeEntriesPerNodeObserved = maxOf(
      maxTransactionKnowledgeEntriesPerNodeObserved,
      nodeEntries.toLong()
    )
  }

  fun beforeBlockKnowledgeEntryAdded() {
    enforceNext(
      blockKnowledgeEntries,
      maxBlockKnowledgeEntries,
      "WORKLOAD_LIMIT_BLOCK_KNOWLEDGE_ENTRIES"
    )
  }

  fun onBlockKnowledgeEntryAdded(nodeEntries: Int) {
    blockKnowledgeEntries++
    maxBlockKnowledgeEntriesObserved = maxOf(
      maxBlockKnowledgeEntriesObserved,
      blockKnowledgeEntries
    )
    maxBlockKnowledgeEntriesPerNodeObserved = maxOf(
      maxBlockKnowledgeEntriesPerNodeObserved,
      nodeEntries.toLong()
    )
  }

  fun beforeMempoolEntryAdded() {
    enforceNext(
      mempoolEntries,
      maxMempoolEntries,
      "WORKLOAD_LIMIT_MEMPOOL_ENTRIES"
    )
  }

  fun onMempoolEntryAdded(nodeEntries: Int) {
    mempoolEntries++
    maxMempoolEntriesObserved = maxOf(maxMempoolEntriesObserved, mempoolEntries)
    maxMempoolEntriesPerNodeObserved = maxOf(
      maxMempoolEntriesPerNodeObserved,
      nodeEntries.toLong()
    )
  }

  fun onMempoolEntryRemoved() {
    check(mempoolEntries > 0L) { "Mempool scalability counter underflow" }
    mempoolEntries--
  }

  fun beforeMeasurementTransactionEntryAdded() {
    enforceNext(
      measurementTransactionEntries,
      maxMeasurementTransactionEntries,
      "WORKLOAD_LIMIT_MEASUREMENT_TRANSACTION_ENTRIES"
    )
  }

  fun onMeasurementTransactionEntryAdded() {
    measurementTransactionEntries++
    maxMeasurementTransactionEntriesObserved = maxOf(
      maxMeasurementTransactionEntriesObserved,
      measurementTransactionEntries
    )
  }

  fun resetMeasurementTransactionEntries() {
    measurementTransactionEntries = 0L
  }

  fun snapshot(): Snapshot = Snapshot(
    currentTransactionKnowledgeEntries = transactionKnowledgeEntries,
    maxTransactionKnowledgeEntriesObserved = maxTransactionKnowledgeEntriesObserved,
    maxTransactionKnowledgeEntriesPerNodeObserved = maxTransactionKnowledgeEntriesPerNodeObserved,
    currentBlockKnowledgeEntries = blockKnowledgeEntries,
    maxBlockKnowledgeEntriesObserved = maxBlockKnowledgeEntriesObserved,
    maxBlockKnowledgeEntriesPerNodeObserved = maxBlockKnowledgeEntriesPerNodeObserved,
    currentMempoolEntries = mempoolEntries,
    maxMempoolEntriesObserved = maxMempoolEntriesObserved,
    maxMempoolEntriesPerNodeObserved = maxMempoolEntriesPerNodeObserved,
    currentMeasurementTransactionEntries = measurementTransactionEntries,
    maxMeasurementTransactionEntriesObserved = maxMeasurementTransactionEntriesObserved,
    maxTransactionKnowledgeEntries = maxTransactionKnowledgeEntries,
    maxBlockKnowledgeEntries = maxBlockKnowledgeEntries,
    maxMempoolEntries = maxMempoolEntries,
    maxMeasurementTransactionEntries = maxMeasurementTransactionEntries
  )

  private fun enforceNext(current: Long, limit: Long, reason: String) {
    if (limit > 0L && current >= limit) {
      throw ScalabilityLimitExceededException(reason)
    }
  }
}
