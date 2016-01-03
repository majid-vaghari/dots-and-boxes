package controller;

import cons.Constants;
import core.Game;
import core.data.model.GraphicalSquare;
import javafx.stage.WindowEvent;
import net.client.ClientCom;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Majid Vaghari on 11/16/2015.
 */
public class Main {
    private final static ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
    private static InetAddress         serverAddress;
    private static ClientCom           clientCom;
    private static GraphicalSquare[][] boxes;

    static {
        try {
            serverAddress = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkConnection() {
        return serverAddress != null;

        //        try (
//                Socket socket = new Socket(serverAddress, Constants.PORT_NUMBER)
//        ) {
//            return socket.isConnected();
//        } catch (IOException e) {
//            return false;
//        }
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

    public static void connect() throws IOException {
        clientCom = new ClientCom(new Socket(serverAddress, Constants.PORT_NUMBER));
        submitTask(clientCom);
    }

    public static <V> Future<V> submitTask(Callable<V> task) {
        return THREAD_POOL.submit(task);
    }

    public static ClientCom getCom() {
        return clientCom;
    }

    public static Game getGame() {
        return null;
    }

    public static void setGame(Game joinedGame) {
    }

    public static GraphicalSquare[][] getBoxes() {
        return boxes;
    }

    public static void setBoxes(GraphicalSquare[][] boxes) {
        Main.boxes = boxes;
    }
}
