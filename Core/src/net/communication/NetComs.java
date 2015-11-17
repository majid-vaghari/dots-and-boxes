package net.communication;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * <p> This abstract class is extended when we want to communicate over network. </p> <p> Two classes are expected to
 * extend this class. One for receiving data from network connection and the other for sending them. </p> <p> Created by
 * Majid Vaghari on 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public abstract class NetComs implements Runnable, AutoCloseable {
    private final ArrayBlockingQueue<String> buffer;

    protected NetComs(final int bufferSize) {
        this.buffer = new ArrayBlockingQueue<>(bufferSize);
    }

    protected final void add(final String message) throws IllegalStateException {
        buffer.add(message);
    }

    protected final String remove() throws IllegalStateException {
        return buffer.remove();
    }

    @Override
    public void close() throws Exception {
        buffer.clear();
    }
}
