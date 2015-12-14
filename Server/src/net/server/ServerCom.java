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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

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
                    if (MainController.getMessage() != null) {
                        output.sendMessage(MainController.getMessage().toString());
                        MainController.putMessage(null);
                    }

                    Message message = Message.parse(input.getMessage());
                    System.out.println("message from client: " + message);

//                    if (message == null) {
//                        close();
//                        return null;
//                    }

                    if (message.getType() == Message.MessageType.HANDSHAKE) {
                        player = new GraphicalPlayer(
                                ((Message.HandshakeMessage) message).getName(),
                                ((Message.HandshakeMessage) message).getColor()
                        );
                    }

                    if (message.getType() == Message.MessageType.CREATE_GAME) {
                        System.out.println("hi");
                        GameConfigurations config = ((Message.CreateGameMessage) message).getConfig();
                        game.setConfigurations(config);
                        game.setAdmin(player);
                        game.setGame(new Game<>(GraphicalSquare.class, config.getBoardSize()));
                        MainController.add(game);
                    }

                    if (message.getType() == Message.MessageType.REQ_LIST) {
                        List<GameConfigurations> list =
                                MainController.stream()
                                              .map(GameController:: getConfigurations).collect(Collectors.toList());
                        System.out.println(list);
                        final String mes = Message.ListGamesMessage.newMessage(list).toString();
                        output.sendMessage(mes);
                        System.out.println(mes);
                    }

                    if (message.getType() == Message.MessageType.JOIN_GAME) {
                        game = MainController.get(((Message.JoinGameMessage) message).getName());
                        if (((Message.JoinGameMessage) message).authenticate(game.getConfigurations().getPassword())) {
                            game.getGame().playerJoin(player);
                        }
                    }

                } catch (IllegalStateException | NoSuchElementException e) {
                    Thread.sleep(Constants.SENDER_WAITING_TIME);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        running = false;
        socket.close();

    }
}
