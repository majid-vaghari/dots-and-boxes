package net.communication.data;

/**
 * This exception is thrown whenever a game is already registered on the server with given name Created by Majid Vaghari
 * on 12/5/2015.
 */
public class DuplicateNameException extends Exception {
    public DuplicateNameException(GameConfigurations configuration) {
        super("Another name with this name is already running on the server: " + configuration.getName());
    }
}
