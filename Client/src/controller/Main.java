package controller;

import cons.Constants;
import javafx.stage.WindowEvent;

import java.io.IOException;
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
    private static String serverAddress;

    public static <V> Future<V> submitTask(Callable<V> task) {
        return THREAD_POOL.submit(task);
    }

    public static boolean checkConnection() {
        try (
                Socket test = new Socket(getServerAddress(), Constants.PORT_NUMBER)
        ) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getServerAddress() {
        return Main.serverAddress;
    }

    public static void setServerAddress(String serverAddress) {
        Main.serverAddress = serverAddress;
    }

    public static void close(WindowEvent event) {
        THREAD_POOL.shutdownNow();
    }
}
