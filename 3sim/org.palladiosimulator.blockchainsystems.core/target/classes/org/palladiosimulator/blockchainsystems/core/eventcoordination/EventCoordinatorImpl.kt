package org.palladiosimulator.blockchainsystems.core.eventcoordination

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventCoordinator
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SystemClockControl
import java.util.IdentityHashMap
import java.util.PriorityQueue

/**
 * Central simulation-event coordinator.
 *
 * MEMORY/SCALABILITY REVISION
 * ---------------------------
 * The former implementation represented the future-event calendar as
 *
 *   TreeMap<Long, EffectsTimeSlice>
 *
 * with one EffectsTimeSlice + ArrayList (and therefore usually one Object[])
 * for every distinct timestamp.  P01 diagnostics showed tens of millions of
 * MessageReceivedEvent timestamps, so those per-timestamp containers alone
 * consumed many gigabytes:
 *
 *   ~57 M TreeMap.Entry
 *   ~42 M EffectsTimeSlice
 *   ~42 M ArrayList
 *   ~42 M Object[]
 *
 * This implementation stores the same future events in one priority queue.
 * Ordering is by (occurrenceTime, insertionSequence), which preserves the old
 * semantics exactly:
 *
 *   1. earlier simulation times run first;
 *   2. events with the same timestamp run in insertion order.
 *
 * No event is dropped, coalesced, delayed or reordered by event type.
 *
 * Cancellation is generation based.  A cancellation increments the origin's
 * epoch; already queued future events from an older epoch become stale and are
 * skipped lazily.  Events already extracted for the current timestamp are not
 * cancelled, matching the former rule that cancelEventsFor() only cancelled
 * events strictly in the future.
 *
 * Optional progress diagnostics can be enabled without changing simulation
 * semantics:
 *
 *   -Dthreesim.progressEveryEvents=1000000
 *
 * The diagnostic prints processed-event count, simulated time and queue size.
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

  private val scheduledEvents = PriorityQueue<ScheduledEvent>(
    compareBy<ScheduledEvent> { it.event.occurrenceTime }
      .thenBy { it.insertionSequence }
  )

  /*
   * Identity semantics are intentional.  Event origins are simulation component
   * instances; cancellation concerns that exact component object, not another
   * object that might happen to compare equal.
   *
   * This map has one small entry per origin instead of one cancellation entry per
   * scheduled event.
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
       * Preserve the old termination-check timing.  The former coordinator first
       * advanced the clock to the next timestamp and only processed that timestamp
       * on the next loop iteration.  Keeping that two-step behavior ensures a
       * clock-based termination condition can become true after the advance but
       * before the future event is dispatched.
       */
      if (next.event.occurrenceTime > clock.currentTime) {
        clock.progressClockTo(next.event.occurrenceTime)
        continue
      }

      processCurrentSlice()
    }
  }

  private fun hasUnprocessedEvents(): Boolean {
    discardCancelledEventsAtHead()
    return scheduledEvents.isNotEmpty()
  }

  /**
   * Extract all still-live events for the current simulation timestamp before
   * dispatching any of them.
   *
   * This detail preserves cancellation semantics.  If one current-time event
   * calls cancelEventsFor(origin), sibling events at the current timestamp were
   * not cancellable in the former implementation and therefore must still run.
   * Extracting the complete current batch first gives exactly that behavior.
   */
  private fun processCurrentSlice() {
    val currentTime = clock.currentTime
    val currentBatch = ArrayList<Event>()

    while (true) {
      discardCancelledEventsAtHead()
      val next = scheduledEvents.peek() ?: break
      if (next.event.occurrenceTime != currentTime) break

      scheduledEvents.poll()
      currentBatch.add(next.event)
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
        /*
         * Same-time events were dispatched synchronously by the former
         * EventCoordinatorImpl.  Keep that behavior because it can affect the
         * ordering of protocol callbacks within one timestamp.
         */
        dispatchEvent(event)
        processedEventCount++
        reportProgressIfRequested()
      }
      else -> {
        /*
         * Preserve the former behavior for events in the past: ignore them.
         * Simulation components should never intentionally schedule such events.
         */
      }
    }
  }

  private fun scheduleEvent(event: Event) {
    scheduledEvents.add(
      ScheduledEvent(
        event = event,
        originEpoch = currentEpoch(event.origin),
        insertionSequence = nextInsertionSequence++
      )
    )
  }

  override fun cancelEventsFor(eventOrigin: EventDispatchable) {
    /*
     * Only already-queued future events are invalidated.  Newly scheduled events
     * after this call capture the incremented epoch and remain valid.
     *
     * We deliberately do not scan/remove matching entries from the PriorityQueue:
     * doing so is O(numberOfQueuedEvents).  Stale entries are discarded when they
     * reach the queue head.  This preserves semantics while avoiding the huge
     * per-event reverse-index structure used by the previous implementation.
     */
    originEpochs[eventOrigin] = currentEpoch(eventOrigin) + 1L
  }

  private fun currentEpoch(origin: EventDispatchable): Long {
    return originEpochs[origin] ?: 0L
  }

  private fun isLive(scheduledEvent: ScheduledEvent): Boolean {
    return scheduledEvent.originEpoch == currentEpoch(scheduledEvent.event.origin)
  }

  private fun discardCancelledEventsAtHead() {
    while (scheduledEvents.isNotEmpty()) {
      val head = scheduledEvents.peek()
      if (isLive(head)) return
      scheduledEvents.poll()
    }
  }

  private fun peekNextLiveEvent(): ScheduledEvent? {
    discardCancelledEventsAtHead()
    return scheduledEvents.peek()
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
        "futureQueueSize=${scheduledEvents.size}"
    )
  }
}
