package net.communication;

/**
 * <p> This exception is thrown whenever joining player didn't provide the correct password when connecting. </p> <p>
 * Created by Majid Vaghari on 12/9/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.3.0
 * @since version 1.3.0
 */
public class GameAuthenticationException extends CommunicationException {
    public GameAuthenticationException() {
        super("Selected Game is password protected. Please enter the correct password.");
    }
}
