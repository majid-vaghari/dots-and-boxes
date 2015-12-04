package net.server;

import cons.Constants;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * <p> This is the main process which waits for connection from clients. After a connection was made, server accepts
 * that connection and waits for another one. </p> <p> Created by Majid Vaghari on 11/17/2015. </p>
 */
public class ServerListener implements Callable, AutoCloseable {
    private final ExecutorService threadPool;
    private       boolean         running;

    public ServerListener(ExecutorService threadPool) {
        this.threadPool = threadPool;
        this.running = false;
    }


    @Override
    public Object call() throws Exception {
        while (running) {
            try (
                    Socket socket = new ServerSocket(Constants.PORT_NUMBER).accept()
            ) {
                threadPool.submit(new ServerCom(threadPool, socket));
            }
        }

        return null;
    }

    @Override
    public void close() throws Exception {
        this.running = false;
    }
}
