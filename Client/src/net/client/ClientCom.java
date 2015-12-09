package net.client;

import cons.Constants;
import controller.Main;
import core.Game;
import core.data.model.GraphicalSquare;
import net.communication.*;

import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class ClientCom implements Callable<Report>, AutoCloseable {
    private final    Socket                socket;
    private volatile Game<GraphicalSquare> game;
    private volatile GameConfigurations    configurations;
    private          OutputWriter          output;
    private          boolean               running;

    {
        running = true;
    }

    public ClientCom(final Socket socket) {
        this.socket = socket;
    }

    public void createGame(GameConfigurations config, GraphicalSquare[][] boxes) {
        this.configurations = config;
        game = new Game<>(boxes);
        Message message = Message.CreateGameMessage.newMessage(config);
        output.sendMessage(message.toString());
    }

    public void joinGame(String name, String password, GraphicalSquare[][] boxes) throws GameAuthenticationException,
                                                                                         GameNotFoundException {
        game = new Game<>(boxes);
        configurations = null;
    }

    public Optional<Game<GraphicalSquare>> getGame() {
        return Optional.ofNullable(this.game);
    }

    public GameConfigurations getConfig() {
        return configurations;
    }

    public List<GameConfigurations> listGames() {
        return null;
    }

    @Override
    public Report call() throws Exception {
        // TODO connect to server and implement methods to run on server
        InputReader input = new InputReader(
                new Scanner(socket.getInputStream()),
                Constants.BUFFER_SIZE
        );
        output = new OutputWriter(
                new PrintStream(socket.getOutputStream()),
                Constants.BUFFER_SIZE
        );

        Main.submitTask(input);
        Main.submitTask(output);

        output.sendMessage(Message.HandshakeMessage.newMessage().toString());

        while (running) {
            try {
                Message message = Message.parse(input.getMessage());

                // TODO respond to server
            } catch (IllegalStateException e) {
                Thread.sleep(Constants.SENDER_WAITING_TIME);
            }

            return null;
        }

        return null;
    }

    @Override
    public void close() throws Exception {
        this.running = false;
    }
}