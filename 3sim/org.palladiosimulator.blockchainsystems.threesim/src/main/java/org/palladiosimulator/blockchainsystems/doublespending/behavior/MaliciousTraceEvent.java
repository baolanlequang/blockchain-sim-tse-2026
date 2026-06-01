package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.jetbrains.annotations.NotNull;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint;

import java.util.Set;

public record MaliciousTraceEvent(
        long occurenceTime,
        double resourcePower,
        Set<P2PNetworkEndpoint> maliciousNeighborEndpoints
) implements TraceEvent {

    public static final String EVENT_TYPE = "MaliciousTraceEvent";

    @Override
    public long getOccurrenceTime() {
        return occurenceTime;
    }

    @Override
    @NotNull
    public String getEventType() {
        return EVENT_TYPE;
    }
}
