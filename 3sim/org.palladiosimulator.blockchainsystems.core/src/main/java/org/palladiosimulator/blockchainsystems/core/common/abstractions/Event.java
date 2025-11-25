package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * The @code{Event} interface represents a simulation event.
 *
 * @author Yannik Sproll
 */
public interface Event {

    /**
     * Returns the occurrence time of the event.
     *
     * @return the occurrence time of the event
     */
    long getOccurrenceTime();

    /**
     * Returns the type of the event.
     *
     * @return type of the event
     */
    String getEventType();

    /**
     * Returns the origin component where the event occurred.
     *
     * @return event origin
     */
    EventDispatchable getOrigin();

    /**
     * Returns a string formatted version of the event.
     *
     * @return formatted version of the event
     */
    String getValueFormatted();
}
