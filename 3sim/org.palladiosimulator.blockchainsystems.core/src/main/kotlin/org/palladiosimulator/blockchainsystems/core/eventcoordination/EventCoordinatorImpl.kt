package org.palladiosimulator.blockchainsystems.core.eventcoordination

import org.palladiosimulator.blockchainsystems.core.common.abstractions.CancellableEvent
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventCoordinator
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SystemClockControl
import java.util.IdentityHashMap
import java.util.PriorityQueue
import java.util.TreeSet

/**
 * Central simulation-event coordinator.
 *
 * High-volume regular events live in one array-backed PriorityQueue. Mining
 * events are the only events cancelled in the current simulator and implement
 * CancellableEvent, so they live in a small separately indexed TreeSet and are
 * physically removed on restart/stop. Regular events therefore do not carry an
 * unused cancellation epoch, saving one long field per queued event.
 *
 * Optional diagnostics:
 *   -Dthreesim.progressEveryEvents=1000000
 */
class EventCoordinatorImpl(
  private val clock: SystemClockControl,
  private val terminationCondition: TerminationCondition
) : EventCoordinator {

  private data class ScheduledEvent(
    val event: Event,
    val insertionSequence: Long
  )

  private val scheduledEventComparator =
    compareBy<ScheduledEvent> { it.event.occurrenceTime }
      .thenBy { it.insertionSequence }

  private val scheduledEvents = PriorityQueue<ScheduledEvent>(scheduledEventComparator)
  private val cancellableEvents = TreeSet<ScheduledEvent>(scheduledEventComparator)
  private val cancellableEventsByOrigin =
    IdentityHashMap<EventDispatchable, MutableSet<ScheduledEvent>>()

  private var nextInsertionSequence = 0L
  private var processedEventCount = 0L

  private val progressEveryEvents: Long =
    java.lang.Long.getLong("threesim.progressEveryEvents", 0L)

  fun processEvents() {
    while (hasUnprocessedEvents() && !terminationCondition.shouldTerminate()) {
      val next = peekNextEvent() ?: break

      // Preserve the old termination-check timing: first move the clock, then
      // process that timestamp on the next loop iteration.
      if (next.event.occurrenceTime > clock.currentTime) {
        clock.progressClockTo(next.event.occurrenceTime)
        continue
      }

      processCurrentSlice()
    }
  }

  private fun hasUnprocessedEvents(): Boolean = peekNextEvent() != null

  /** Extract all same-time events before dispatching any of them. */
  private fun processCurrentSlice() {
    val currentTime = clock.currentTime
    val currentBatch = ArrayList<Event>()

    while (true) {
      val next = peekNextEvent() ?: break
      if (next.event.occurrenceTime != currentTime) break

      val scheduledEvent = pollNextEvent() ?: break
      currentBatch.add(scheduledEvent.event)
    }

    for (event in currentBatch) {
      dispatchEvent(event)
      processedEventCount++
      reportProgressIfRequested()
    }
  }

  override fun raiseEvent(event: Event) {
    when {
      event.occurrenceTime > clock.currentTime -> scheduleEvent(event)
      event.occurrenceTime == clock.currentTime -> {
        dispatchEvent(event)
        processedEventCount++
        reportProgressIfRequested()
      }
      else -> Unit // preserve legacy behavior for events in the past
    }
  }

  private fun scheduleEvent(event: Event) {
    val scheduledEvent = ScheduledEvent(
      event = event,
      insertionSequence = nextInsertionSequence++
    )

    if (event is CancellableEvent) {
      cancellableEvents.add(scheduledEvent)
      cancellableEventsByOrigin
        .getOrPut(event.origin) { hashSetOf() }
        .add(scheduledEvent)
    } else {
      scheduledEvents.add(scheduledEvent)
    }
  }

  override fun cancelEventsFor(eventOrigin: EventDispatchable) {
    // In this source revision cancelEventsFor is called only by MiningProcessImpl,
    // whose BlockMinedEvent implements CancellableEvent. Remove those events
    // physically instead of retaining tombstones in the main queue.
    cancellableEventsByOrigin.remove(eventOrigin)?.forEach { scheduledEvent ->
      cancellableEvents.remove(scheduledEvent)
    }
  }

  private fun removeFromCancellableIndex(scheduledEvent: ScheduledEvent) {
    val origin = scheduledEvent.event.origin
    val originEvents = cancellableEventsByOrigin[origin] ?: return
    originEvents.remove(scheduledEvent)
    if (originEvents.isEmpty()) cancellableEventsByOrigin.remove(origin)
  }

  private fun peekNextEvent(): ScheduledEvent? {
    val regular = scheduledEvents.peek()
    val cancellable = cancellableEvents.firstOrNull()

    return when {
      regular == null -> cancellable
      cancellable == null -> regular
      scheduledEventComparator.compare(regular, cancellable) <= 0 -> regular
      else -> cancellable
    }
  }

  private fun pollNextEvent(): ScheduledEvent? {
    val regular = scheduledEvents.peek()
    val cancellable = cancellableEvents.firstOrNull()

    return when {
      regular == null && cancellable == null -> null
      cancellable == null || (regular != null && scheduledEventComparator.compare(regular, cancellable) <= 0) ->
        scheduledEvents.poll()
      else -> {
        val removed = cancellableEvents.pollFirst()
        removeFromCancellableIndex(removed)
        removed
      }
    }
  }

  private fun dispatchEvent(event: Event) {
    event.origin.dispatchEvent(event)
  }

  private fun reportProgressIfRequested() {
    if (progressEveryEvents <= 0L) return
    if (processedEventCount % progressEveryEvents != 0L) return

    System.err.println(
      "[3SIM-progress] processedEvents=$processedEventCount " +
        "simulationTimeMs=${clock.currentTime} " +
        "futureQueueSize=${scheduledEvents.size + cancellableEvents.size} " +
        "cancellableQueueSize=${cancellableEvents.size}"
    )
  }
}
