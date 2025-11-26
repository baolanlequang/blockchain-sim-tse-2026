package org.palladiosimulator.blockchainsystems.core.transaction

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable

@JvmRecord
data class TransactionSubmittedEvent(
  val occurrenceTime: Long,
  val target: EventDispatchable
) : Event {
  override fun getOccurrenceTime(): Long {
    return this.occurrenceTime
  }

  override fun getEventType(): String {
    return EVENT_TYPE
  }

  override fun getOrigin(): EventDispatchable? {
    return this.target
  }

  override fun getValueFormatted(): String {
    return this.toString()
  }

  companion object {
    const val EVENT_TYPE: String = "TransactionSubmittedEvent"
  }
}