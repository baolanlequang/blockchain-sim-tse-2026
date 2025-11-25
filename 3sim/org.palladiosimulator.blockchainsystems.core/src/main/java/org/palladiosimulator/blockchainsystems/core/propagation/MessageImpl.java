package org.palladiosimulator.blockchainsystems.core.propagation;

import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message;

/**
 * Implementation of the Message interface, representing a message sent in
 * the P2P network, with content, content type, and size.
 *
 * @author Yannik Sproll, Davis Riedel
 */
public class MessageImpl implements Message {

    private final Object _content;
    private final String _contentType;
    private final int _size;

    public MessageImpl(
            Object content,
            String contentType,
            int size) {
        _content = content;
        _contentType = contentType;
        _size = size;
    }

    public Object getContent() {
        return _content;
    }

    public String getContentType() {
        return _contentType;
    }

    @Override
    public int getSize() {
        return _size;
    }

}
