package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * 
 * The @code{EventCoordinator} interface is an abstraction of
 * the central coordination component for simulation events.
 * It schedules an dispatches events. Events are always dispatched
 * on their origins.
 * 
 * @author Yannik Sproll
 *
 */
public interface EventCoordinator {

	/**
	 * Schedules a specified event for later dispatching.
	 * 
	 * @param event the event to schedule
	 */
	void raiseEvent(Event event);
	
	/**
	 * Cancels all previously scheduled events for a specified event origin.
	 * 
	 * @param eventOrigin the event origin to cancel events for
	 */
	void cancelEventsFor(EventDispatchable eventOrigin);
}
