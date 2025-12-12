package org.palladiosimulator.blockchainsystems.core.network

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint

/**
 * Represents a trace event that is triggered when a message is dropped in the P2P network.
 *
 * @param message The message that was dropped.
 * @param occurrenceTime The time at which the event occurred.
 * @param recipientNode The network endpoint (node) that was supposed to receive the message.
 * @param senderNode The network endpoint (node) that sent the message.
 *
 * @author Davis Riedel
 */
@Serializable
data class MessageDroppedTraceEvent(
  val message: Message,
  override val occurrenceTime: Long,
  val recipientNode: P2PNetworkEndpoint,
  val senderNode: P2PNetworkEndpoint,
) : TraceEvent {
  override val eventType = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "MessageDroppedEvent"
  }
}