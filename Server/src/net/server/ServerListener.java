package net.server;

import cons.Constants;
import controller.MainController;
import net.communication.data.Report;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * <p> This is the main process which waits for connection from clients. After a connection was made, server accepts
 * that connection and waits for another one. </p> <p> Created by Majid Vaghari on 11/17/2015. </p>
 */
public class ServerListener implements Callable<Report>, AutoCloseable {
    private boolean running;

    public ServerListener() {
        this.running = true;
    }


    @Override
    public Report call() throws Exception {
        Report       report = new Report();
        ServerSocket server = new ServerSocket(Constants.PORT_NUMBER);
        while (running) {
            try {
                report.add(
                        "Listening on port " + server.getLocalPort() + ".\n" +
                        "Waiting for connection."
                );
                Socket socket = server.accept();
                report.add(
                        "Connection Established...\n"                   +
                        "Client IP: "                                   +
                        socket.getInetAddress().getHostAddress() + "\n" +
                        "Client Port: "                                 +
                        socket.getPort()
                );
                MainController.submitTask(new ServerCom(socket));
            } catch (IOException e) {
                report.add(
                        "Exception caught while trying to connect to new client." +
                        "IOException: "                                           +
                        e.getMessage()
                );
            }
        }

        return report;
    }

    @Override
    public void close() throws Exception {
        this.running = false;
    }
}
