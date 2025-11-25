package org.palladiosimulator.blockchainsystems.core.common;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationContext;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogger;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLoggerContainer;

/**
 * The {@code P2PNetworkObject} is an base class for all classes that represent components
 * of a P2P network.
 * It provides the necessary fields to hold trace event logger and simulation context
 * and implements their initialization.
 *
 * @author Yannik Sproll
 */
public abstract class P2PNetworkObject implements EventDispatchable, Traceable {

    private SimulationContext _simulationContext;

    private TraceEventLogger _traceEventLogger;

    protected void onInitialize() {
    }

    public final void initialize(SimulationContext simulationContext) {
        _simulationContext = simulationContext;
        onInitialize();
    }

    protected void onCleanup() {
    }

    public final void cleanup() {
        onCleanup();
        _simulationContext = null;
    }

    public final void initializeLogger(TraceEventLogOrigin logOrigin) {
        TraceEventLoggerContainer loggerContainer = _simulationContext.getTraceEventLoggerContainer();
        loggerContainer.createTraceEventLogger(logOrigin);
        _traceEventLogger = loggerContainer.getLogger(logOrigin);
    }

    protected SimulationContext getSimulationContext() {
        return _simulationContext;
    }

    protected TraceEventLogger getTraceEventLogger() {
        return _traceEventLogger;
    }
}
