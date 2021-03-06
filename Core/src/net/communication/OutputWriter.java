package net.communication;

import net.communication.data.Report;
import net.communication.data.protocol.Protocol;

import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * <p> This class is used to run in the background and send messages when needed. </p> <p> Created by Majid Vaghari on
 * 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public final class OutputWriter extends NetComs implements AutoCloseable {
    private final PrintStream stream;
    private       boolean     running;

    /**
     * Used to make instances of this class
     *
     * @param stream     a PrintStream object to send messages to the network
     * @param bufferSize size of the buffer
     */
    public OutputWriter(final PrintStream stream, final int bufferSize) {
        super(bufferSize);
        this.stream = stream;
        this.running = true;
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
        synchronized (this) {
            notify();
        }
//        notify();
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
        running = false;
    }

    /**
     * runs in the background and checks the queue in specific intervals if it was not empty sends the message to the
     * network.
     */
    @Override
    public Report call() throws InterruptedException {
        while (running) {
            String remove;
            try {
                remove = remove();
            } catch (NoSuchElementException e) {
                synchronized (this) {
                    wait();
                }
                remove = remove();
            }
            stream.println(Protocol.get("message-indicator.start"));
            stream.println(remove);
            stream.println(Protocol.get("message-indicator.end"));
        }
//        while (running) {
//            try {
//                stream.println(" hvaij");
//                final String remove = remove();
//                System.out.println("WTF + " + remove);
//                stream.println(Protocol.get("message-indicator.start"));
//                System.out.println(remove);
//                stream.println(remove);
//                stream.println(Protocol.get("message-indicator.end"));
//            } catch (IllegalStateException e) {
//                try {
//                    Thread.sleep(Constants.SENDER_WAITING_TIME);
//                } catch (InterruptedException ignored) {
//
//                }
//            }
//        }

        return null;
    }
}
