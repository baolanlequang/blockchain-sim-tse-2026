package org.palladiosimulator.blockchainsystems.core.mining;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable;

public record BlockMinedEvent(
        long occurrenceTime,
        String previousBlockHash,
        EventDispatchable target
) implements Event {

    public static final String EVENT_TYPE = "BlockMinedEvent";

    @Override
    public long getOccurrenceTime() {
        return this.occurrenceTime();
    }

    public String getPreviousBlockHash() {
        return this.previousBlockHash();
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    public EventDispatchable getOrigin() {
        return this.target();
    }

    @Override
    public String getValueFormatted() {
        return this.toString();
    }

}
