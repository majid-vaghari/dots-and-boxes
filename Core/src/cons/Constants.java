package cons;

/**
 * <p> This class holds constants values used across our project. </p> <p> Declared final so it cannot be extended by
 * other classes. </p> <p> Created by Majid Vaghari on 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public final class Constants {
    /**
     * The port number which the application will run on.
     */
    public static final int  PORT_NUMBER         = 6859;
    /**
     * The number of messages each client and server can hold before sending to the other (buffering).
     */
    public static final int  BUFFER_SIZE         = 1024;
    /**
     * This is the amount of time OutputWriter will wait for a message to send in the network
     */
    public static final long SENDER_WAITING_TIME = 1000;


    /**
     * No one should be able to make instances of this class.
     */
    private Constants() {

    }
}
