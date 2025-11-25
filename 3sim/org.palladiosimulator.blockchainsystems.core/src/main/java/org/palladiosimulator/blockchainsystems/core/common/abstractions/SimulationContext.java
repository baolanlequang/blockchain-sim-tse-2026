package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * The @code{SimulationContext} provides the components required
 * for simulation purposes.
 *
 * @author Yannik Sproll
 */
public interface SimulationContext {

    /**
     * Returns the event coordinator of the simulation.
     *
     * @return event coordinator of the simulation
     */
    EventCoordinator getEventCoordinator();

    /**
     * Returns the system clock of the simulation.
     *
     * @return the system clock for the simulation
     */
    SystemClock getSystemClock();

    /**
     * Returns the Trace event logger container of the simulation.
     *
     * @return trace event logger container of the simulation
     */
    TraceEventLoggerContainer getTraceEventLoggerContainer();
}
