package controller;

import net.communication.DuplicateNameException;
import net.communication.data.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Majid Vaghari on 11/16/2015.
 */
public class MainController {
    private static final List<GameController> GAMES;
    private static final ExecutorService      THREAD_POOL;
    private static       boolean              player1;

    private static Message message;

    static {
        GAMES = new ArrayList<>();
        THREAD_POOL = Executors.newCachedThreadPool();
    }

    public static void add(GameController newGame) throws DuplicateNameException {
        GameController game;
        if ((game = get(newGame.getConfigurations().getName())) != null)
            throw new DuplicateNameException(game.getConfigurations());
        else
            GAMES.add(newGame);
    }

    public static GameController get(String name) {
        Predicate<GameController> matchGivenName = controller ->
                controller.getConfigurations().getName().equalsIgnoreCase(name);

        final Optional<GameController> result = GAMES.parallelStream().filter(matchGivenName).findAny();
        if (result.isPresent())
            return result.get();
        return null;
    }

    public static Stream<GameController> stream() {
        return GAMES.parallelStream();
    }

    public static <V> Future<V> submitTask(Callable<V> task) {
        return THREAD_POOL.submit(task);
    }

    public static void putMessage(Message message1) {
        message = message1;
    }

    public static Message getMessage() {
        return message;
    }

    public static boolean getRcpt() {
        return MainController.player1;
    }

    public static void setRcpt(boolean player1) {
        MainController.player1 = player1;
    }

    public void close() {
        THREAD_POOL.shutdownNow();
    }
}
