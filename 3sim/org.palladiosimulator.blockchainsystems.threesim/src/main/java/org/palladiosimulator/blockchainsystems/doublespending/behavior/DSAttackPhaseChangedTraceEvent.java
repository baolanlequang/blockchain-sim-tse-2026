package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;

public record DSAttackPhaseChangedTraceEvent(
		long occurenceTime,
		String previousPhase,
		String newPhase) implements TraceEvent {

	public static final String EVENT_TYPE = "DSAttackPhaseChangedTraceEvent";
	
	public String getPreviousPhase() {
		return previousPhase;
	}
	
	public String getNewPhase() {
		return newPhase;
	}

	@Override
	public String getEventType() {
		return EVENT_TYPE;
	}

	public void formatDetails(StringBuilder stringBuilder) {
		stringBuilder.append("{ previousPhase=");
		stringBuilder.append(previousPhase);
		stringBuilder.append("; newPhase=");
		stringBuilder.append(newPhase);
		stringBuilder.append(" }");
	}

	@Override
	public long getOccurrenceTime() {
		return occurenceTime;
	}

}
