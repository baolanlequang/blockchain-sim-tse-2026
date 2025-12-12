package org.palladiosimulator.blockchainsystems.core.simulation.termination.abstractions

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

interface NodeTerminationState {
  fun onTraceEventOccurred(traceEvent: TraceEvent)
}