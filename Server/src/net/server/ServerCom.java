package net.server;

import net.communication.InputReader;
import net.communication.OutputWriter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class ServerCom implements Callable {
    private final ExecutorService threadPool;
    private       InputReader     input;
    private       OutputWriter    output;

    public ServerCom(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public Object call() throws Exception {
        // TODO listen for a single connection
        return null;
    }
}
