package org.palladiosimulator.blockchainsystems.core.tracing;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogger;

public class EmptyTraceEventLoggerImpl implements TraceEventLogger {

	private final TraceEventLogOrigin _logOrigin;
	
	public EmptyTraceEventLoggerImpl(TraceEventLogOrigin logOrigin) {
		_logOrigin = logOrigin;
	}
	
	@Override
	public TraceEventLogOrigin getLogOrigin() {
		return _logOrigin;
	}

	@Override
	public void logEvent(TraceEvent traceEvent) {
	}

	@Override
	public boolean isEventTypeEnabled(String eventType) {
		return false;
	}

}
