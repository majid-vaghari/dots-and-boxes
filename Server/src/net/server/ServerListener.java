package net.server;

import cons.Constants;
import controller.MainController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * <p> This is the main process which waits for connection from clients. After a connection was made, server accepts
 * that connection and waits for another one. </p> <p> Created by Majid Vaghari on 11/17/2015. </p>
 */
public class ServerListener implements Callable, AutoCloseable {
    private       boolean         running;

    public ServerListener() {
        this.running = false;
    }


    @Override
    public Object call() throws Exception {
        while (running) {
            try (
                    Socket socket = new ServerSocket(Constants.PORT_NUMBER).accept()
            ) {
                MainController.submitTask(new ServerCom(socket));
            }
        }

        return null;
    }

    @Override
    public void close() throws Exception {
        this.running = false;
    }
}
