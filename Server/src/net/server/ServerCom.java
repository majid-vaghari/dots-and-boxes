package net.server;

import cons.Constants;
import controller.GameController;
import controller.MainController;
import core.Game;
import core.data.model.GraphicalPlayer;
import core.data.model.GraphicalSquare;
import net.communication.InputReader;
import net.communication.OutputWriter;
import net.communication.data.GameConfigurations;
import net.communication.data.Message;
import net.communication.data.Report;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class ServerCom implements Callable<Report>, AutoCloseable {
    private final Socket          socket;
    private       GameController  game;
    private       GraphicalPlayer player;
    private       boolean         running;

    {
        this.running = true;
    }

    public ServerCom(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public Report call() throws Exception {
        // TODO listen for a single connection
        try (
                InputReader input = new InputReader(
                        new Scanner(socket.getInputStream()),
                        Constants.BUFFER_SIZE
                );
                OutputWriter output = new OutputWriter(
                        new PrintStream(socket.getOutputStream()),
                        Constants.BUFFER_SIZE
                )
        ) {
            // make instances of game and do things :D

            MainController.submitTask(input);
            MainController.submitTask(output);

            while (this.running) {
                try {
                    Message message = Message.parse(input.getMessage());

                    if (message == null)
                        close();

                    if (message.getType() == Message.MessageType.HANDSHAKE) {

                    }

                    if (message.getType() == Message.MessageType.CREATE_GAME) {
                        GameConfigurations config     = ((Message.CreateGameMessage) message).getConfig();
                        game.setConfigurations(config);
                        game.setGame(new Game<GraphicalSquare>(GraphicalSquare.class, config.getBoardSize()));
                    }

                    if (message.getType() == Message.MessageType.JOIN_GAME) {

                    }

                } catch (IllegalStateException e) {
                    Thread.sleep(Constants.SENDER_WAITING_TIME);
                }
            }
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        running = false;
        socket.close();

    }
}
