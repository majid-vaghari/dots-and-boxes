package net.data;

/**
 * <p> This class holds one message that is being transmitted between the server and the client.</p> <p> Created by
 * Majid Vaghari on 12/4/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.1.0
 * @since version 1.1.0
 */
public class Message {
    /**
     * the message
     */
    private String message;

    /**
     * Sets the message one time only (the Message object is immutable)
     *
     * @param message the message to sett
     */
    public Message(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Looks in the message and
     *
     * @return type of this message
     */
    public MessageType getType() {
        return null;
    }

    public enum MessageType {
        CREATE_GAME,
        JOIN_GAME,
        PUT_LINE,
        END_GAME
    }
}