package net.communication;

/**
 * Created by Majid Vaghari on 12/4/2015.
 */
public abstract class MalformedMessageException extends ProtocolException {
    public MalformedMessageException(String message) {
        super(message);
    }
}
