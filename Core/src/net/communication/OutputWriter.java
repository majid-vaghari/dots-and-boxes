package net.communication;

import cons.Constants;

import java.io.PrintStream;

/**
 * <p> This class is used to run in the background and send messages when needed. </p> <p> Created by Majid Vaghari on
 * 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public final class OutputWriter extends NetComs {
    private final PrintStream stream;

    /**
     * Used to make instances of this class
     *
     * @param stream     a PrintStream object to send messages to the network
     * @param bufferSize size of the buffer
     */
    public OutputWriter(final PrintStream stream, final int bufferSize) {
        super(bufferSize);
        this.stream = stream;
    }

    /**
     * puts the given message in the queue
     *
     * @param message the message to send
     *
     * @throws IllegalStateException if the buffer is full
     */
    public void sendMessage(final String message) throws IllegalStateException {
        add(message);
        notify();
    }

    /**
     * Closes the connection and clears the buffer.
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        stream.close();
    }

    /**
     * runs in the background and checks the queue in specific intervals if it was not empty sends the message to the
     * network.
     */
    @Override
    public void run() {
        while (true) {
            try {
                stream.println(remove());
            } catch (IllegalStateException e) {
                try {
                    wait(Constants.SENDER_WAITING_TIME);
                } catch (InterruptedException e1) {
                    // do nothing
                }
            }
        }
    }
}
