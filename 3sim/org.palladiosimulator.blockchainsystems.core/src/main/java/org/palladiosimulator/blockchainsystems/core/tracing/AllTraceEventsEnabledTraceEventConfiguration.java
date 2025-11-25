package org.palladiosimulator.blockchainsystems.core.tracing;

public class AllTraceEventsEnabledTraceEventConfiguration implements TraceEventConfiguration {

	@Override
	public boolean isEventTypeEnabled(String eventType) {
		return true;
	}

}
