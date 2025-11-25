package org.palladiosimulator.blockchainsystems.core.transaction

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

@Serializable
data class TransactionSubmittedTraceEvent(
  override val occurrenceTime: Long,
  val transaction: Transaction
) : TraceEvent {
  companion object {
    const val EVENT_TYPE = "TransactionSubmittedTraceEvent"
  }

  override val eventType = EVENT_TYPE
}