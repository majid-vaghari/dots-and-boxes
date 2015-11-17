package net.communication;

import java.util.Scanner;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public final class InputReader extends NetComs {
    final private Scanner stream;

    public InputReader(final Scanner stream, final int bufferSize) {
        super(bufferSize);
        this.stream = stream;
    }

    public String getMessage() throws IllegalStateException {
        return remove();
    }

    @Override
    public void close() throws Exception {
        super.close();
        stream.close();
    }

    @Override
    public void run() {
        while (stream.hasNextLine())
            add(stream.nextLine());
    }
}
