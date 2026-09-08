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
 * MEMORY/SCALABILITY REVISION
 * ---------------------------
 * The main future-event calendar remains the single PriorityQueue introduced by
 * the pre-experiment simulator revision. That avoids the large per-timestamp
 * TreeMap/EffectsTimeSlice allocation overhead while preserving ordering by
 * (occurrenceTime, insertionSequence).
 *
 * Origin cancellation now has a bounded fast path. Events that implement
 * CancellableEvent (currently BlockMinedEvent) are stored in a small TreeSet and
 * indexed by origin. cancelEventsFor() physically removes those future events
 * instead of leaving them strongly referenced until their timestamps reach the
 * head of the main calendar. High-volume message/validation events remain in the
 * compact PriorityQueue and pay no reverse-index memory cost.
 *
 * The epoch mechanism is retained as a semantic fallback: if a future non-indexed
 * event from a cancelled origin ever exists, it is still invalidated lazily.
 * Events already extracted for the current timestamp are not cancelled, matching
 * the former coordinator semantics.
 *
 * Optional progress diagnostics can be enabled without changing simulation
 * semantics:
 *
 *   -Dthreesim.progressEveryEvents=1000000
 */
class EventCoordinatorImpl(
  private val clock: SystemClockControl,
  private val terminationCondition: TerminationCondition
) : EventCoordinator {

  private data class ScheduledEvent(
    val event: Event,
    val originEpoch: Long,
    val insertionSequence: Long
  )

  private val scheduledEventComparator =
    compareBy<ScheduledEvent> { it.event.occurrenceTime }
      .thenBy { it.insertionSequence }

  // High-volume, normally non-cancelled events. Array-backed for low memory overhead.
  private val scheduledEvents = PriorityQueue<ScheduledEvent>(scheduledEventComparator)

  // Small set of events that are routinely cancelled/replaced (currently mining events).
  // TreeSet gives O(log n) removal when cancelEventsFor() is called.
  private val cancellableEvents = TreeSet<ScheduledEvent>(scheduledEventComparator)
  private val cancellableEventsByOrigin =
    IdentityHashMap<EventDispatchable, MutableSet<ScheduledEvent>>()

  /*
   * Identity semantics are intentional. Event origins are simulation component
   * instances; cancellation concerns that exact component object, not another
   * object that might happen to compare equal.
   */
  private val originEpochs = IdentityHashMap<EventDispatchable, Long>()

  private var nextInsertionSequence = 0L
  private var processedEventCount = 0L

  private val progressEveryEvents: Long =
    java.lang.Long.getLong("threesim.progressEveryEvents", 0L)

  fun processEvents() {
    while (hasUnprocessedEvents() && !terminationCondition.shouldTerminate()) {
      val next = peekNextLiveEvent() ?: break

      /*
       * Preserve the old termination-check timing. The former coordinator first
       * advanced the clock to the next timestamp and only processed that timestamp
       * on the next loop iteration.
       */
      if (next.event.occurrenceTime > clock.currentTime) {
        clock.progressClockTo(next.event.occurrenceTime)
        continue
      }

      processCurrentSlice()
    }
  }

  private fun hasUnprocessedEvents(): Boolean {
    return peekNextLiveEvent() != null
  }

  /**
   * Extract all still-live events for the current simulation timestamp before
   * dispatching any of them. This preserves the previous same-timestamp
   * cancellation semantics.
   */
  private fun processCurrentSlice() {
    val currentTime = clock.currentTime
    val currentBatch = ArrayList<Event>()

    while (true) {
      val next = peekNextLiveEvent() ?: break
      if (next.event.occurrenceTime != currentTime) break

      val scheduledEvent = pollNextLiveEvent() ?: break
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
        // Same-time events were dispatched synchronously by the former coordinator.
        dispatchEvent(event)
        processedEventCount++
        reportProgressIfRequested()
      }
      else -> {
        // Preserve the former behavior for events in the past: ignore them.
      }
    }
  }

  private fun scheduleEvent(event: Event) {
    val scheduledEvent = ScheduledEvent(
      event = event,
      originEpoch = currentEpoch(event.origin),
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
    /*
     * Increment first so any non-indexed future event from this origin is also
     * invalidated. Newly scheduled events capture the incremented epoch.
     */
    originEpochs[eventOrigin] = currentEpoch(eventOrigin) + 1L

    // Physically remove the routinely cancellable events without scanning the
    // high-volume main PriorityQueue.
    cancellableEventsByOrigin.remove(eventOrigin)?.forEach { scheduledEvent ->
      cancellableEvents.remove(scheduledEvent)
    }
  }

  private fun currentEpoch(origin: EventDispatchable): Long {
    return originEpochs[origin] ?: 0L
  }

  private fun isLive(scheduledEvent: ScheduledEvent): Boolean {
    return scheduledEvent.originEpoch == currentEpoch(scheduledEvent.event.origin)
  }

  private fun discardCancelledRegularEventsAtHead() {
    while (scheduledEvents.isNotEmpty()) {
      val head = scheduledEvents.peek()
      if (isLive(head)) return
      scheduledEvents.poll()
    }
  }

  private fun discardCancelledCancellableEventsAtHead() {
    while (cancellableEvents.isNotEmpty()) {
      val head = cancellableEvents.first()
      if (isLive(head)) return

      val removed = cancellableEvents.pollFirst()
      removeFromCancellableIndex(removed)
    }
  }

  private fun removeFromCancellableIndex(scheduledEvent: ScheduledEvent) {
    val origin = scheduledEvent.event.origin
    val originEvents = cancellableEventsByOrigin[origin] ?: return

    originEvents.remove(scheduledEvent)
    if (originEvents.isEmpty()) {
      cancellableEventsByOrigin.remove(origin)
    }
  }

  private fun peekNextLiveEvent(): ScheduledEvent? {
    discardCancelledRegularEventsAtHead()
    discardCancelledCancellableEventsAtHead()

    val regular = scheduledEvents.peek()
    val cancellable = cancellableEvents.firstOrNull()

    return when {
      regular == null -> cancellable
      cancellable == null -> regular
      scheduledEventComparator.compare(regular, cancellable) <= 0 -> regular
      else -> cancellable
    }
  }

  private fun pollNextLiveEvent(): ScheduledEvent? {
    discardCancelledRegularEventsAtHead()
    discardCancelledCancellableEventsAtHead()

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
