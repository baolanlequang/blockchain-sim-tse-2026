package org.palladiosimulator.blockchainsystems.core.propagation.transaction

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * Trace event that is triggered when a transaction is received by a network endpoint.
 *
 * @author Davis Riedel
 */
@Serializable
data class TransactionReceivedTraceEvent(
  override val occurrenceTime: Long,
  val sentTransaction: Transaction,
  val receivingNetworkEndpoint: P2PNetworkEndpoint
) : TraceEvent {
  companion object {
    const val EVENT_TYPE: String = "TransactionReceivedTraceEvent"
  }

  override val eventType = EVENT_TYPE
}