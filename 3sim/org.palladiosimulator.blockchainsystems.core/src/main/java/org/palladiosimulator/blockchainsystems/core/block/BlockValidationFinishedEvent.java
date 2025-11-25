package org.palladiosimulator.blockchainsystems.core.block;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable;
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;


public record BlockValidationFinishedEvent(
        long occurrenceTime,
        EventDispatchable origin,
        Block block
) implements Event {

    public static final String EVENT_TYPE = "BlockValidationFinishedEvent";

    @Override
    public long getOccurrenceTime() {
        return this.occurrenceTime();
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    public EventDispatchable getOrigin() {
        return this.origin();
    }

    @Override
    public String getValueFormatted() {
        return this.toString();
    }

}