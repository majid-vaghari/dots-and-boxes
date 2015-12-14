package controller;

import cons.Constants;
import core.Game;
import javafx.stage.WindowEvent;
import net.client.ClientCom;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Majid Vaghari on 11/16/2015.
 */
public class Main {
    private final static ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
    private static InetAddress serverAddress;

    public static <V> Future<V> submitTask(Callable<V> task) {
        return THREAD_POOL.submit(task);
    }

    public static <V> Future<V> submitDaemon(Callable<V> daemon) {
        return null;
    }

    public static boolean checkConnection() {
        if (serverAddress == null)
            return false;

        try (
                Socket socket = new Socket(serverAddress, Constants.PORT_NUMBER)
        ) {
            return socket.isConnected();
        } catch (IOException e) {
            return false;
        }
    }

    public static InetAddress getServerAddress() {
        return Main.serverAddress;
    }

    public static void setServerAddress(InetAddress serverAddress) {
        Main.serverAddress = serverAddress;
    }

    public static void close(WindowEvent event) {
        THREAD_POOL.shutdownNow();
    }

    public static void connect() {

    }

    public static ClientCom getCom() {
        return null;
    }

    public static void setGame(Game joinedGame) {
    }

    public static Game getGame() {
        return null;
    }
}
