package org.palladiosimulator.blockchainsystems.core.network;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message;

/**
 * Represents an event that occurs when a message is received in the P2P network.
 *
 * The message is carried directly. The former P2PLinkMessageFrame only wrapped
 * the same Message reference plus an unused issued-at timestamp, so eliminating
 * it removes one heap object per in-flight network event without changing the
 * modeled message size, latency, bandwidth or delivery ordering.
 */
public record MessageReceivedEvent(
        Message message,
        long occurrenceTime,
        EventDispatchable target,
        P2PNode recipientNode,
        P2PNode senderNode
) implements Event {

    public static final String EVENT_TYPE = "MessageReceivedEvent";

    @Override
    public long getOccurrenceTime() {
        return this.occurrenceTime();
    }

    public Message getMessage() {
        return this.message();
    }

    @Override
    public EventDispatchable getOrigin() {
        return this.target();
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
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
