package net.client;

import net.communication.InputReader;
import net.communication.OutputWriter;
import net.communication.data.Report;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class ClientCom implements Callable<Report> {
    private final ExecutorService threadPool;
    private       InputReader     input;
    private       OutputWriter    output;

    public ClientCom(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public Report call() throws Exception {
        // TODO connect to server and implement methods to run on server
        return null;
    }
}
