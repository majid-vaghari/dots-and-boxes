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
    private final String message;

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
     * Looks in the message and returns message type.
     *
     * @return type of this message
     */
    public MessageType getType() {
        return null;
    }

    /**
     * This nested enum type used to determine types of messages which are being transmitted between client and server.
     */
    public enum MessageType {
        /**
         * shows that the message is sent to create a game
         */
        CREATE_GAME,
        /**
         * shows that the message is sent to join a game
         */
        JOIN_GAME,
        /**
         * shows that the message is sent to put a line (move)
         */
        PUT_LINE,
        /**
         * sent by server to all clients to tell them the game has finished
         */
        END_GAME
    }
}