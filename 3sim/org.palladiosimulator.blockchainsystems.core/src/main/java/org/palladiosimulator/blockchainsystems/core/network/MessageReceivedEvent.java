package org.palladiosimulator.blockchainsystems.core.network;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.EventDispatchable;

/**
 * Represents an event that occurs when a message is received in the P2P network.
 *
 * @param message        the message that was received
 * @param occurrenceTime the time when the event occurred
 * @param target         the target of the event, typically the component that handles the message
 * @param recipientNode  the node that is the intended recipient of the message
 * @param senderNode     the node that sent the message
 * @author Yannik Sproll
 */
public record MessageReceivedEvent(
        P2PLinkMessageFrame message,
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

    public P2PLinkMessageFrame getMessage() {
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
