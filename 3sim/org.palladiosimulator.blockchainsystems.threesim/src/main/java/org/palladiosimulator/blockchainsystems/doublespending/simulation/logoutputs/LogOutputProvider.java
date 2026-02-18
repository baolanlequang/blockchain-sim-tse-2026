package org.palladiosimulator.blockchainsystems.doublespending.simulation.logoutputs;

import java.util.Set;

import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput;

public interface LogOutputProvider {

	Set<TraceEventLogOutput> getLogoutputs();
}
