package net.server;

import cons.Constants;
import net.communication.InputReader;
import net.communication.OutputWriter;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class ServerListener implements Callable {
    private final ExecutorService threadPool;

    public ServerListener(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public Object call() throws Exception {
        // TODO listen on the 6859 port (multiple connections)
        while (true) {
            try (
                    Socket socket = new ServerSocket(Constants.PORT_NUMBER).accept();
                    InputReader input = new InputReader(new Scanner(socket.getInputStream()), Constants.BUFFER_SIZE);
                    OutputWriter output = new OutputWriter(new PrintStream(socket.getOutputStream()),
                                                           Constants.BUFFER_SIZE)
            ) {
                threadPool.submit(input);
                threadPool.submit(output);
            }
        }
    }
}
