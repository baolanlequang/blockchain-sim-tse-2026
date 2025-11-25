package org.palladiosimulator.blockchainsystems.core.transaction

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * Trace event that is triggered when a transaction is removed from the mempool of a validating node.
 *
 * @author Davis Riedel
 */
@Serializable
data class TransactionRemovedFromMemPoolTraceEvent(
  override val occurrenceTime: Long,
  val removedTransaction: Transaction
) : TraceEvent {
  companion object {
    const val EVENT_TYPE: String = "TransactionRemovedFromMemPoolTraceEvent"
  }

  override val eventType = EVENT_TYPE
}