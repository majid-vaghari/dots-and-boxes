package net.communication;

/**
 * Created by Majid Vaghari on 12/4/2015.
 */
public abstract class ProtocolException extends CommunicationException {
    public ProtocolException(String message) {
        super(message);
    }
}
