package org.palladiosimulator.blockchainsystems.core.network

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message

data class MessageDroppedEvent(
  val message: Message,
  private val occurrenceTime: Long,
  private val target: EventDispatchable,
  val recipientNode: P2PNode,
  val senderNode: P2PNode,
) : Event {
  override fun getOccurrenceTime(): Long = occurrenceTime
  override fun getEventType(): String = EVENT_TYPE
  override fun getOrigin(): EventDispatchable = target
  override fun getValueFormatted(): String = toString()

  companion object {
    const val EVENT_TYPE = "MessageDroppedEvent"
  }
}
