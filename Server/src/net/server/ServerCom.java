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
    private       boolean         player1;

    {
        this.running = true;
    }

    public ServerCom(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public Report call() throws Exception {
        // TODO listen for a single connection
        Report report = new Report();
        report.add(
                "initializing input/output handlers."
        );
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

            report.add(
                    "I/O handlers set."
            );


            new Thread(() -> {
                while (this.running) {
                    if (MainController.getMessage() != null && MainController.getRcpt() == player1) {
                        output.sendMessage(MainController.getMessage().toString());
                        MainController.putMessage(null);
                    }

                    try {
                        Thread.sleep(Constants.SENDER_WAITING_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (this.running) {
                try {
                    Message message = Message.parse(input.getMessage());

                    report.add(
                            "Message from client: " +
                            message.getMessage()
                    );

//                    if (message == null) {
//                        close();
//                        return report;
//                    }

                    if (message.getType() == Message.MessageType.HANDSHAKE) {
                        player = new GraphicalPlayer(
                                ((Message.HandshakeMessage) message).getName(),
                                ((Message.HandshakeMessage) message).getColor()
                        );
                    }

                    if (message.getType() == Message.MessageType.CREATE_GAME) {
                        GameConfigurations config = ((Message.CreateGameMessage) message).getConfig();

                        game = new GameController();
                        game.setConfigurations(config);
                        game.setAdmin(player);
                        game.setGame(new Game<>(GraphicalSquare.class, config.getBoardSize()));
                        game.getGame().playerJoin(game.getAdmin());
                        MainController.add(game);
                        player1 = true;
                    }

                    if (message.getType() == Message.MessageType.REQ_LIST) {
                        List<GameConfigurations> list =
                                MainController.stream()
                                              .map(GameController:: getConfigurations).collect(Collectors.toList());
                        final String mes = Message.ListGamesMessage.newMessage(list).toString();
                        output.sendMessage(mes);
                    }

                    if (message.getType() == Message.MessageType.JOIN_GAME) {
                        game = MainController.get(((Message.JoinGameMessage) message).getName());

                        for (int i = 0; i < game.getGame().numOfPlayers(); i++) {
                            output.sendMessage(
                                    Message.HandshakeMessage.newMessage(
                                            game.getGame().getPlayer(i).getName(),
                                            ((GraphicalPlayer) game.getGame().getPlayer(i)).getColor()
                                    ).toString()
                            );
                        }

                        if (!game.getConfigurations().isPasswordProtected() ||
                            ((Message.JoinGameMessage) message).authenticate(game.getConfigurations().getPassword())) {
                            game.getGame().playerJoin(player);
                        }

                        MainController.putMessage(
                                Message.HandshakeMessage.newMessage(
                                        player.getName(),
                                        player.getColor()
                                )
                        );
                        MainController.setRcpt(!player1);
                    }

                    if (message.getType() == Message.MessageType.PUT_LINE) {
                        if (((Message.PutLineMessage) message).isHorizontal())
                            game.getGame().addHorizontalLine(
                                    ((Message.PutLineMessage) message).getRow(),
                                    ((Message.PutLineMessage) message).getCol()
                            );
                        else
                            game.getGame().addVerticalLine(
                                    ((Message.PutLineMessage) message).getRow(),
                                    ((Message.PutLineMessage) message).getCol()
                            );

                        MainController.putMessage(message);
                        MainController.setRcpt(!player1);
                    }

                } catch (IllegalStateException | NoSuchElementException e) {
                    Thread.sleep(Constants.SENDER_WAITING_TIME);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public void close() throws Exception {
        running = false;
        socket.close();

    }
}
