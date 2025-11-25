package org.palladiosimulator.blockchainsystems.core.network;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable;

public record MessageSentEvent(
        long occurrenceTime,
        P2PLinkMessageFrame message,
        EventDispatchable target,
        P2PNode recipientNode,
        P2PNode senderNode
) implements Event {

    public static final String EVENT_TYPE = "MessageSentEvent";

    @Override
    public long getOccurrenceTime() {
        return this.occurrenceTime();
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    public P2PLinkMessageFrame getMessage() {
        return this.message();
    }

    @Override
    public EventDispatchable getOrigin() {
        return this.target();
    }


    public P2PNode getRecipientNode() {
        return this.recipientNode();
    }

    public P2PNode getSenderNode() {
        return this.senderNode();
    }

    @Override
    public String getValueFormatted() {
        return this.toString();
    }
}
