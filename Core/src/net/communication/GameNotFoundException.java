package net.communication;

/**
 * Created by Majid Vaghari on 12/9/2015.
 */
public class GameNotFoundException extends CommunicationException {
    public GameNotFoundException(String gameName) {
        super("Selected game is not found: " + gameName);
    }
}
