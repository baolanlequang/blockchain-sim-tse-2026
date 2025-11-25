package org.palladiosimulator.blockchainsystems.core.common;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventCoordinator;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationContext;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SystemClock;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLoggerContainer;

/**
 * The {@code SimulationContextImpl} class is an implementation of the {@code SimulationContext} interface.
 * It is a container to provide access to simulation utils such as the event coordinator or the global clock.
 *
 * @author Yannik Sproll
 */
public class SimulationContextImpl implements SimulationContext {

    private final EventCoordinator _eventCoordinator;
    private final SystemClock _clock;
    private final TraceEventLoggerContainer _loggerContainer;

    public SimulationContextImpl(
            EventCoordinator eventCoordinator,
            SystemClock clock,
            TraceEventLoggerContainer loggerContainer
    ) {
        _eventCoordinator = eventCoordinator;
        _clock = clock;
        _loggerContainer = loggerContainer;
    }

    @Override
    public EventCoordinator getEventCoordinator() {
        return _eventCoordinator;
    }

    @Override
    public SystemClock getSystemClock() {
        return _clock;
    }

    @Override
    public TraceEventLoggerContainer getTraceEventLoggerContainer() {
        return _loggerContainer;
    }

}
