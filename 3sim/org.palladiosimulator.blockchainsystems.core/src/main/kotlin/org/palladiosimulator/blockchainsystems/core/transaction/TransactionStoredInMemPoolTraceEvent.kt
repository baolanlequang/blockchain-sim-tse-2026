package org.palladiosimulator.blockchainsystems.core.transaction

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * Trace event that is triggered when a transaction is stored in the mempool of a validating node.
 *
 * @author Davis Riedel
 */
@Serializable
data class TransactionStoredInMemPoolTraceEvent(
  override val occurrenceTime: Long,
  val storedTransaction: Transaction
) : TraceEvent {
  companion object {
    const val EVENT_TYPE: String = "TransactionStoredInMemPoolTraceEvent"
  }

  override val eventType = EVENT_TYPE
}