package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * Marker for future events that must support eager physical cancellation.
 *
 * High-volume ordinary events stay in the memory-efficient priority queue.
 * Events implementing this interface are kept in a small separately indexed
 * ordered set so cancelEventsFor(origin) can remove them without scanning the
 * full future-event calendar.
 */
public interface CancellableEvent extends Event {
}
