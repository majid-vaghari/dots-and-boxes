package net.server;

import cons.Constants;
import net.communication.InputReader;
import net.communication.OutputWriter;
import net.data.Message;
import net.data.Report;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class ServerCom implements Callable<Report>, AutoCloseable {
    private final ExecutorService threadPool;
    private final Socket          socket;
    private       InputReader     input;
    private       OutputWriter    output;
    private       boolean         running;

    public ServerCom(ExecutorService threadPool, Socket socket) {
        this.threadPool = threadPool;
        this.socket = socket;
        this.running = true;
    }

    @Override
    public Report call() throws Exception {
        // TODO listen for a single connection
        InputReader input = new InputReader(new Scanner(socket.getInputStream()), Constants.BUFFER_SIZE);
        OutputWriter output = new OutputWriter(new PrintStream(socket.getOutputStream()),
                                               Constants.BUFFER_SIZE);
        // make instances of game and do things :D

        threadPool.submit(input);
        threadPool.submit(output);

        while (running) {
            try {
                Message message = new Message(input.getMessage());

            } catch (IllegalStateException e) {
                Thread.sleep(Constants.SENDER_WAITING_TIME);
            }
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        running = false;
    }
}
