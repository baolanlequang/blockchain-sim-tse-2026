package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * The @code{EventDispatchable} interface specifies the methods
 * to allow a dispatching of simulation events.
 * 
 * @author Yannik Sproll
 *
 */
public interface EventDispatchable {
	
	/**
	 * Dispatches the specified event on this object.
	 * 
	 * @param event the event to be dispatched
	 */
	void dispatchEvent(Event event);
}
