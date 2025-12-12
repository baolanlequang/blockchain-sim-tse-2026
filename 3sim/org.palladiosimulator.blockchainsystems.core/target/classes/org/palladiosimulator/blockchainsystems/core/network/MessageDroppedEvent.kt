package org.palladiosimulator.blockchainsystems.core.network

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable;

data class MessageDroppedEvent(
  val message: P2PLinkMessageFrame,
  private val occurrenceTime: Long,
  private val target: EventDispatchable,
  val recipientNode: P2PNode,
  val senderNode: P2PNode,
) : Event {
  override fun getOccurrenceTime(): Long {
    return occurrenceTime
  }

  override fun getEventType(): String {
    return EVENT_TYPE
  }

  override fun getOrigin(): EventDispatchable {
    return target
  }

  override fun getValueFormatted(): String {
    return this.toString()
  }

  companion object {
    const val EVENT_TYPE = "MessageDroppedEvent"
  }
}