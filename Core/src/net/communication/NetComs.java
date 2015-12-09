package net.communication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * <p> This abstract class is extended when we want to communicate over network. </p> <p> Two classes are expected to
 * extend this class. One for receiving data from network connection and the other for sending them. </p>  <p> This
 * class is a runnable one because runs in the background while other processes are running </p><p> Created by Majid
 * Vaghari on 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public abstract class NetComs implements Callable<Report>, AutoCloseable {
    /**
     * This is a blocking queue to buffer data sent and received by hosts.
     */
    private final ArrayBlockingQueue<String> buffer;

    /**
     * Constructor to make the class
     *
     * @param bufferSize size of the buffer
     */
    protected NetComs(final int bufferSize) {
        this.buffer = new ArrayBlockingQueue<>(bufferSize);
    }

    /**
     * Adds a message to the buffer
     *
     * @param message the message to add
     *
     * @throws IllegalStateException when the buffer is full
     */
    protected final void add(final String message) throws IllegalStateException {
        buffer.add(message);
    }

    /**
     * Removes the first message arrived in the buffer.
     *
     * @return next message from buffer
     *
     * @throws IllegalStateException when the buffer is empty
     */
    protected final String remove() throws IllegalStateException {
        return buffer.remove();
    }

    /**
     * Closes the resources taken by this class (clears buffer)
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        buffer.clear();
    }
}
