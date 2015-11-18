package net.server;

import cons.Constants;

import java.net.ServerSocket;
import java.net.Socket;
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
                    Socket socket = new ServerSocket(Constants.PORT_NUMBER).accept()

            ) {
                threadPool.submit(new ServerCom(threadPool, socket));

            }
        }
    }
}
