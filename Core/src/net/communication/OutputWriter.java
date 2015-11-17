package net.communication;

import java.io.PrintStream;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public final class OutputWriter extends NetComs {
    private final PrintStream stream;

    public OutputWriter(final PrintStream stream, final int bufferSize) {
        super(bufferSize);
        this.stream = stream;
    }

    public void sendMessage(final String message) throws IllegalStateException {
        add(message);
        notify();
    }

    @Override
    public void close() throws Exception {
        super.close();
        stream.close();
    }

    @Override
    public void run() {
        while (true) {
            try {
                stream.println(remove());
            } catch (IllegalStateException e) {
                try {
                    wait();
                } catch (InterruptedException e1) {
                    // do nothing
                }
            }
        }
    }
}
