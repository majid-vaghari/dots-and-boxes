package net.communication.data;

import javafx.scene.paint.Color;
import net.communication.data.protocol.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
        StringTokenizer tokenizer = new StringTokenizer(msg, "\n");
        String          header    = tokenizer.nextToken();
        if (header.equalsIgnoreCase(Protocol.get("message-headers.message-types.handshake")))
            return new HandshakeMessage(msg);
        if (header.equalsIgnoreCase(Protocol.get("message-headers.message-types.create-game")))
            return new CreateGameMessage(msg);
        if (header.equalsIgnoreCase(Protocol.get("message-headers.message-types.request-list")))
            return new RequestListMessage(msg);
        if (header.equalsIgnoreCase(Protocol.get("message-headers.message-types.list-games")))
            return new ListGamesMessage(msg);
        if (header.equalsIgnoreCase(Protocol.get("message-headers.message-types.join-game")))
            return new JoinGameMessage(msg);
        if (header.equalsIgnoreCase(Protocol.get("message-headers.message-types.put-line")))
            return new PutLineMessage(msg);
        if (header.equalsIgnoreCase(Protocol.get("message-headers.message-types.end-game")))
            return new EndGameMessage(msg);
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
         * shows that the message is sent to request list of available games
         */
        REQ_LIST,
        /**
         * the message is sent by the server and contains list of available games
         */
        LIST,
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
            message += Protocol.get("message-headers.message-types.create-game") + "\n";
            message += configurations.isFlexibleNumOfPlayers() + "\n";
            message += configurations.isPasswordProtected() + "\n";
            message += configurations.isAsyncMode() + "\n";
            message += configurations.getBoardSize() + "\n";
            message += configurations.getNumOfPlayers() + "\n";
            message += configurations.getName() + "\n";
            message += configurations.getPassword() + "\n";
            // TODO build message body
            return new CreateGameMessage(message);
        }

        public GameConfigurations getConfig() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            GameConfigurations config = new GameConfigurations();
            config.setFlexibleNumOfPlayers(Boolean.valueOf(tokenizer.nextToken()));
            config.setPasswordProtected(Boolean.valueOf(tokenizer.nextToken()));
            config.setAsyncMode(Boolean.valueOf(tokenizer.nextToken()));
            config.setBoardSize(Integer.valueOf(tokenizer.nextToken()));
            config.setNumOfPlayers(Integer.valueOf(tokenizer.nextToken()));
            config.setName(String.valueOf(tokenizer.nextToken()));
            if (config.isPasswordProtected())
                config.setPassword(String.valueOf(tokenizer.nextToken()));
            else
                config.setPassword("");

            return config;
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
    public static class RequestListMessage extends Message {
        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private RequestListMessage(String message) {
            super(message);
        }

        public static RequestListMessage newMessage() {
            String message = "";
            message += Protocol.get("message-headers.message-types.request-list") + "\n";
            return new RequestListMessage(message);
        }

        @Override
        public MessageType getType() {
            return MessageType.REQ_LIST;
        }
    }

    public static class ListGamesMessage extends Message {

        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private ListGamesMessage(String message) {
            super(message);
        }

        public static ListGamesMessage newMessage(List<GameConfigurations> configs) {
            String message = "";
            message += Protocol.get("message-headers.message-types.list-games") + "\n";
            for (GameConfigurations config : configs) {
                message += capConf(config) + "\n";
            }
            return new ListGamesMessage(message);
        }

        private static String capConf(GameConfigurations config) {
            String message = "";
            message += config.isFlexibleNumOfPlayers() + ",";
            message += config.isPasswordProtected() + ",";
            message += config.isAsyncMode() + ",";
            message += config.getBoardSize() + ",";
            message += config.getNumOfPlayers() + ",";
            message += config.getName() + ",";
            if (config.isPasswordProtected())
                message += config.getPassword();

            return message;
        }

        public List<GameConfigurations> getList() {
            ArrayList<GameConfigurations> list      = new ArrayList<>();
            StringTokenizer               tokenizer = new StringTokenizer(this.getMessage() + "\n");
            tokenizer.nextToken(); // header
            tokenizer.nextToken(); // header
            while (tokenizer.hasMoreTokens()) {
                list.add(getConf(tokenizer.nextToken()));
            }
            return list;
        }

        private static GameConfigurations getConf(String cap) {
            StringTokenizer    tokenizer = new StringTokenizer(cap, ",");
            GameConfigurations config    = new GameConfigurations();
            config.setFlexibleNumOfPlayers(Boolean.valueOf(tokenizer.nextToken()));
            config.setPasswordProtected(Boolean.valueOf(tokenizer.nextToken()));
            config.setAsyncMode(Boolean.valueOf(tokenizer.nextToken()));
            config.setBoardSize(Integer.valueOf(tokenizer.nextToken()));
            config.setNumOfPlayers(Integer.valueOf(tokenizer.nextToken()));
            config.setName(String.valueOf(tokenizer.nextToken()));
            if (config.isPasswordProtected())
                config.setPassword(String.valueOf(tokenizer.nextToken()));

            return config;
        }

        @Override
        public MessageType getType() {
            return MessageType.LIST;
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

        public static JoinGameMessage newMessage(String name, String password) {
            String message = "";
            message += Protocol.get("message-headers.message-types.join-game") + "\n";
            message += name + "\n";
            message += password + "\n";

            return new JoinGameMessage(message);
        }

        public String getName() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            return tokenizer.nextToken();
        }

        public boolean authenticate(String password) {
            return password.equals(getPassword());
        }

        private String getPassword() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            String name = tokenizer.nextToken(); // name
            return tokenizer.nextToken();
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

        public static PutLineMessage newMessage(boolean horizontal, int row, int col) {
            String message = "";
            message += Protocol.get("message-headers.message-types.put-line") + "\n";
            message += horizontal + "\n";
            message += row + "\n";
            message += col + "\n";
            return new PutLineMessage(message);
        }

        public boolean isHorizontal() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            return Boolean.valueOf(tokenizer.nextToken());
        }

        public int getRow() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            tokenizer.nextToken(); // horizontal
            return Integer.valueOf(tokenizer.nextToken());
        }

        public int getCol() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            tokenizer.nextToken(); // horizontal
            tokenizer.nextToken(); // row
            return Integer.valueOf(tokenizer.nextToken());
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

        public static HandshakeMessage newMessage(String name, Color color) {
            String message = "";
            message += Protocol.get("message-headers.message-types.handshake") + "\n";
            message += name + "\n";
            message += color.getRed() + "\n";
            message += color.getGreen() + "\n";
            message += color.getBlue() + "\n";
            return new HandshakeMessage(message);
        }

        public String getName() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            return tokenizer.nextToken();
        }

        public Color getColor() {
            StringTokenizer tokenizer = new StringTokenizer(this.getMessage(), "\n");
            tokenizer.nextToken(); // header
            tokenizer.nextToken(); // name

            double red   = Double.valueOf(tokenizer.nextToken());
            double green = Double.valueOf(tokenizer.nextToken());
            double blue  = Double.valueOf(tokenizer.nextToken());
            return new Color(red, green, blue, 1);
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
    public static class EndGameMessage extends Message {

        /**
         * Sets the message one time only (the Message object is immutable)
         *
         * @param message the message to sett
         */
        private EndGameMessage(String message) {
            super(message);
        }

        public static EndGameMessage newMessage() {
            String message = "";
            message += Protocol.get("message-headers.message-types.end-game") + "\n";
            return new EndGameMessage(message);
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