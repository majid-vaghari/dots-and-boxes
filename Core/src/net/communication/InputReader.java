package net.communication;

import net.communication.data.Report;
import net.communication.data.protocol.Protocol;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * <p> Input reader is a NetComs (has a buffer). It runs in the background and receives input messages as they arrive.
 * </p> <p> Created by Majid Vaghari on 11/17/2015.</p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public final class InputReader extends NetComs {
    private final Scanner stream;

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
    public String getMessage() {
        try {
            return remove();
        } catch (NoSuchElementException e) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        throw new NoSuchElementException();
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
    public Report call() throws IndicatorNotFoundException {
        while (stream.hasNextLine()) {
            String line = stream.nextLine();

            if (!line.equalsIgnoreCase(Protocol.get("message-indicator.start")))
                throw new IndicatorNotFoundException("Starting message indicator not found");

            String message = "";
            while (stream.hasNextLine() && !(line = stream.nextLine()).equalsIgnoreCase(Protocol.get
                    ("message-indicator.end")))
                message += line + "\n";

            if (!line.equalsIgnoreCase(Protocol.get("message-indicator.end")))
                throw new IndicatorNotFoundException("Ending message indicator not found");

            add(message);
            synchronized (this) {
                notify();
            }
        }

        return null;
    }
}
