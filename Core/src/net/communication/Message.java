package net.communication;

/**
 * <p> This class holds one message that is being transmitted between the server and the client.</p> <p> Created by
 * Majid Vaghari on 12/4/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.1.0
 * @since version 1.1.0
 */
public abstract class Message {
    /**
     * the message
     */
    private final String message;

    /**
     * Sets the message one time only (the Message object is immutable)
     *
     * @param message the message to sett
     */
    private Message(String message) {
        this.message = message;
    }

    /**
     * This static method is used to parse a given message and get specific message type
     *
     * @param msg the message to be parsed
     *
     * @return parsed message object
     */
    public static Message parse(String msg) {
        Message message = null;
        // TODO parse message and return specific message type.
        return message;
    }

    /**
     * @return the message
     *
     * @sse getMessage
     */
    @Override
    public String toString() {
        return getMessage();
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
    public abstract MessageType getType();

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
         * shows that the message is a handshake between the client and server
         */
        HANDSHAKE,
        /**
         * sent by server to all clients to tell them the game has finished
         */
        END_GAME
    }

    /**
     *
     */
    public static class CreateGameMessage extends Message {

        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private CreateGameMessage(String message) {
            super(message);
        }

        /**
         * used to create a new message which creates a new game
         *
         * @param configurations the desired configuration of the game
         *
         * @return the Message to send
         */
        public static CreateGameMessage newMessage(GameConfigurations configurations) {
            String message = "";
            // TODO build message body
            return new CreateGameMessage(message);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageType getType() {
            return MessageType.CREATE_GAME;
        }
    }

    /**
     *
     */
    public static class JoinGameMessage extends Message {
        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private JoinGameMessage(String message) {
            super(message);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageType getType() {
            return MessageType.JOIN_GAME;
        }
    }

    /**
     *
     */
    public static class PutLineMessage extends Message {

        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private PutLineMessage(String message) {
            super(message);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageType getType() {
            return MessageType.PUT_LINE;
        }
    }

    /**
     *
     */
    public static class HandshakeMessage extends Message {

        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private HandshakeMessage(String message) {
            super(message);
        }

        public static Message newMessage() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageType getType() {
            return MessageType.HANDSHAKE;
        }


    }

    /**
     *
     */
    public class EndGameMessage extends Message {

        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private EndGameMessage(String message) {
            super(message);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageType getType() {
            return MessageType.END_GAME;
        }
    }
}