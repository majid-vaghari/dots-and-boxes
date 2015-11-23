package net.communication;

import java.util.Scanner;

/**
 * <p> Input reader is a NetComs (has a buffer). It runs in the background and receives input messages as they arrive.
 * </p> <p> Created by Majid Vaghari on 11/17/2015.</p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public final class InputReader extends NetComs {
    final private Scanner stream;

    /**
     * The constructor to make instances of Input reader
     *
     * @param stream     a Scanner object which reads data from network
     * @param bufferSize size of the buffer
     */
    public InputReader(final Scanner stream, final int bufferSize) {
        super(bufferSize);
        this.stream = stream;
    }

    /**
     * called to get messages currently residing in the buffer
     *
     * @return current message in the buffer
     *
     * @throws IllegalStateException if the buffer is empty
     */
    public String getMessage() throws IllegalStateException {
        return remove();
    }

    /**
     * Closes the connection and clears the buffer
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        stream.close();
    }

    /**
     * runs in the background, receives the messages as they arrive and puts them in the buffer.
     */
    @Override
    public void run() {
        while (stream.hasNextLine())
            add(stream.nextLine());
    }
}
