package org.palladiosimulator.blockchainsystems.doublespending.simulation.termination;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;

public interface NodeTerminationState extends SimulationWinnerVoter {

	void onTraceEventOccured(TraceEvent traceEvent);
}
