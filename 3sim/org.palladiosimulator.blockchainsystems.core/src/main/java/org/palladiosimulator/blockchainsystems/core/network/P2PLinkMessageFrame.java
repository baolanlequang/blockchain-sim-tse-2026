package org.palladiosimulator.blockchainsystems.core.network;

import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message;

public class P2PLinkMessageFrame {

    private final Message _content;
    private final long _issuedAt;

    public P2PLinkMessageFrame(
            Message content,
            long issuedAt) {
        _content = content;
        _issuedAt = issuedAt;
    }


    public Message getContent() {
        return _content;
    }

    public long getIssuedAt() {
        return _issuedAt;
    }
}
