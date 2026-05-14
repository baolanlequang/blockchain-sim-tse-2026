package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.jetbrains.annotations.NotNull;
import org.palladiosimulator.blockchainsystems.core.common.BlockchainSimulationObject;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationContext;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint;

import java.util.Set;

public class MaliciousEventHandler extends BlockchainSimulationObject {

    private Set<P2PNetworkEndpoint> maliciousNeighborEndpoints;
    private double resourcePower;
    MaliciousEventHandler() {
        super("MaliciousEventHandler", "MaliciousEventHandler");
    }

    MaliciousEventHandler(double resourcePower, Set<P2PNetworkEndpoint> maliciousNeighborEndpoints) {
        super("MaliciousEventHandler", "MaliciousEventHandler");
        this.resourcePower = resourcePower;
        this.maliciousNeighborEndpoints = maliciousNeighborEndpoints;
    }

    public void logEvent() {
        if (!maliciousNeighborEndpoints.isEmpty()) {
            MaliciousTraceEvent traceEvent = new MaliciousTraceEvent(getSimulationContext().getSystemClock().getCurrentTime(), resourcePower, maliciousNeighborEndpoints);
            getTraceEventLogger().logEvent(traceEvent);
        }
    }

    @Override
    public void dispatchEvent(Event event) {

    }

    @Override
    public void initialize(@NotNull SimulationContext simulationContext) {
        super.initialize(simulationContext);
    }
}
