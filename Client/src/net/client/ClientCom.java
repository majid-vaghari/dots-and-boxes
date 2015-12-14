package net.client;

import cons.Constants;
import controller.Main;
import core.Game;
import core.data.model.GraphicalSquare;
import net.communication.GameAuthenticationException;
import net.communication.GameNotFoundException;
import net.communication.InputReader;
import net.communication.OutputWriter;
import net.communication.data.GameConfigurations;
import net.communication.data.Message;
import net.communication.data.Report;

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
    private final    Socket                   socket;
    private volatile Game<GraphicalSquare>    game;
    private volatile GameConfigurations       configurations;
    private volatile List<GameConfigurations> configurationsList;
    private          OutputWriter             output;
    private          InputReader              input;
    private          boolean                  running;

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
        return Optional.ofNullable(game);
    }

    public Optional<GameConfigurations> getConfig() {
        return Optional.ofNullable(configurations);
    }

    public List<GameConfigurations> listGames() {
        Message request = Message.RequestListMessage.newMessage();
        output.sendMessage(request.toString());
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException ignored) {

        }


        return configurationsList;
    }

    @Override
    public Report call() throws Exception {
        // TODO connect to server and implement methods to run on server
        input = new InputReader(
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

                if (message.getType() == Message.MessageType.LIST) {
                    this.configurationsList = ((Message.ListGamesMessage) message).getList();
                    synchronized (this) {
                        notify();
                    }
                }

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