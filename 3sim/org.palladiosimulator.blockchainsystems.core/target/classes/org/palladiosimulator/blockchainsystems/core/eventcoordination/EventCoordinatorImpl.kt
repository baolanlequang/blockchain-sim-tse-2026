package org.palladiosimulator.blockchainsystems.core.eventcoordination

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventCoordinator
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SystemClockControl
import java.util.TreeSet
import java.util.TreeMap

class EventCoordinatorImpl(
  private val clock: SystemClockControl,
  private val terminationCondition: TerminationCondition
) : EventCoordinator {
  private val eventTimeSlices = TreeMap<Long, EffectsTimeSlice>()
  private val eventsPerOrigin = HashMap<EventDispatchable, TreeSet<Event>>()

  fun processEvents() {
    while (hasUnprocessedEvents() && !terminationCondition.shouldTerminate()) {
      processCurrentSlice()
      purgeProcessedSlices()
      advanceCurrentTime()
    }
  }

  private fun hasUnprocessedEvents(): Boolean {
    return !eventTimeSlices.isEmpty()
  }

  private fun purgeProcessedSlices() {
    if (eventTimeSlices.isEmpty()) return

    var firstTimestamp = eventTimeSlices.firstKey()

    while (firstTimestamp != null && firstTimestamp <= clock.currentTime) {
      removeEventsAt(firstTimestamp)

      if (eventTimeSlices.isEmpty()) break

      firstTimestamp = eventTimeSlices.firstKey()
    }
  }

  private fun removeEventsAt(timestamp: Long) {
    val eventTimeSlice = eventTimeSlices[timestamp]
    if (eventTimeSlice != null) {
      for (event in eventTimeSlice.events) {
        val events = eventsPerOrigin[event.origin]
        events?.remove(event)
      }

      eventTimeSlices.remove(clock.currentTime)
    }
  }

  private fun processCurrentSlice() {
    val eventOccurrenceTime = clock.currentTime
    val currentSlice = eventTimeSlices[eventOccurrenceTime]

    if (currentSlice == null) return

    for (event in currentSlice.events) {
      dispatchEvent(event)
    }
  }

  private fun advanceCurrentTime() {
    if (eventTimeSlices.isEmpty()) return

    val nextEventTime = eventTimeSlices.firstKey()!!
    clock.progressClockTo(nextEventTime)
  }

  override fun raiseEvent(event: Event) {
    if (event.occurrenceTime > clock.currentTime) {
      scheduleEvent(event)
    } else if (event.occurrenceTime == clock.currentTime) {
      dispatchEvent(event)
    }
  }

  private fun scheduleEvent(event: Event) {
    val eventOccurrenceTime = event.occurrenceTime
    val timeSlice = eventTimeSlices.computeIfAbsent(eventOccurrenceTime) { timestamp ->
      EffectsTimeSlice(timestamp)
    }

    timeSlice.addEvent(event)

    eventsPerOrigin
      .getOrPut(event.origin) {
        TreeSet<Event> { e1, e2 ->
          e1.occurrenceTime.compareTo(e2.occurrenceTime)
        }
      }.add(event)
  }

  override fun cancelEventsFor(eventOrigin: EventDispatchable) {
    eventsPerOrigin[eventOrigin]?.let { events ->
      if (!events.isEmpty()) {
        for (event in events) {
          // Can only cancel events in the future
          // Prevents concurrent access exception if events from the current time slice are cancelled
          if (event.occurrenceTime <= clock.currentTime) continue

          eventTimeSlices[event.occurrenceTime]?.removeEvent(event)
        }
        events.clear()
      }
    }
  }

  private fun dispatchEvent(event: Event) {
    event.origin.dispatchEvent(event)
  }
}