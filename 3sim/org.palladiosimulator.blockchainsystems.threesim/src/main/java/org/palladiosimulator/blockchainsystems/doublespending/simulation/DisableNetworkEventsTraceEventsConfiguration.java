package org.palladiosimulator.blockchainsystems.doublespending.simulation;

import java.util.HashSet;

import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockReceivedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockSentTraceEvent;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventConfiguration;

public class DisableNetworkEventsTraceEventsConfiguration implements TraceEventConfiguration {

	private final HashSet<String> _networkEventTypes;

	public DisableNetworkEventsTraceEventsConfiguration() {
		_networkEventTypes = new HashSet<String>();
		_networkEventTypes.add(BlockReceivedTraceEvent.EVENT_TYPE);
		_networkEventTypes.add(BlockSentTraceEvent.EVENT_TYPE);
	}
	
	@Override
	public boolean isEventTypeEnabled(String eventType) {
		return !_networkEventTypes.contains(eventType);
	}
}
