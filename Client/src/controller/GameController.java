package controller;

import cons.Constants;
import core.Game;
import core.InvalidMoveException;
import core.data.model.GraphicalPlayer;
import core.data.model.GraphicalSquare;
import core.data.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.communication.data.GameConfigurations;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Amin on 12/9/2015.
 */


public class GameController implements Initializable {

    private static final int BOX_DIM = 500;
    Game<GraphicalSquare> game;
    //    GameConfigurations gc;
    GameConfigurations    gameConfig;
    @FXML
    private Label               gameName;
    @FXML
    private ListView<Player>    playersList;
    @FXML
    private Group               boxWrapper;
    private CanvasBox[][]       boxes;
    private GraphicalSquare[][] gBoxes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Optional<Game<GraphicalSquare>> optGame   = Main.getCom().getGame();
        Optional<GameConfigurations>    optConfig = Main.getCom().getConfig();
        if (!optConfig.isPresent() || !optGame.isPresent()) {
            System.err.println("No configuration is present");
            return;
            //TODO handle the exceptions
        }
        gameConfig = optConfig.get();
        game = optGame.get();
        gameName.setText(gameConfig.getName());
        boxes = new CanvasBox[gameConfig.getBoardSize()][gameConfig.getBoardSize()];

        new Thread(() -> {
            while (true) {
                for (int i = 0; i < game.numOfPlayers(); i++)
                    if (!playersList.getItems().contains(game.getPlayer(i)))
                        playersList.getItems().add(game.getPlayer(i));

                update();

                try {
                    Thread.sleep(Constants.SENDER_WAITING_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        this.gBoxes = Main.getBoxes();

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                boxes[i][j] = new CanvasBox(i, j, gameConfig.getBoardSize());

                boxWrapper.getChildren().add(boxes[i][j]);
            }

        }
    }

    private void update() {
        for (int i = 0; i < boxes.length; i++)
            for (int j = 0; j < boxes[i].length; j++)
                if (boxes[i][j] != null)
                    boxes[i][j].update();
    }

    private class CanvasBox extends Canvas {
        public static final double DOT_DIM = 10;
        private final GraphicalSquare current;
        private final GraphicsContext gc = getGraphicsContext2D();
        private final int col;
        private final int row;

        public CanvasBox(int row, int col, int num) {
            super(((double) BOX_DIM) / ((double) num), ((double) BOX_DIM) / ((double) num));

            this.setTranslateX(col * getWidth());
            this.setTranslateY(row * getHeight());

            gc.setStroke(Color.DARKGRAY);
            gc.strokeRect(0, 0, getWidth(), getHeight());

            this.row = row;
            this.col = col;
            this.current = GameController.this.gBoxes[row][col];

            setHandler();
        }

        public void setHandler() {
            setOnMouseClicked(event -> {
                if (!game.getCurrentPlayer().getName().equals(Main.getCom().getPlayer().get().getName())) {
                    System.err.println("Nobate shoma bood? :|");
                    return;
                }
                if (isLeft(event)) {
                    try {
                        game.addVerticalLine(row, col);
                    } catch (InvalidMoveException e) {
                        e.printStackTrace();
                    }
                    Main.getCom().putLine(false, row, col);
                }
                if (isRight(event)) {
                    try {
                        game.addVerticalLine(row, col + 1);
                    } catch (InvalidMoveException e) {
                        e.printStackTrace();
                    }
                    Main.getCom().putLine(false, row, col + 1);
                }
                if (isTop(event)) {
                    try {
                        game.addHorizontalLine(row, col);
                    } catch (InvalidMoveException e) {
                        e.printStackTrace();
                    }
                    Main.getCom().putLine(true, row, col);
                }
                if (isBottom(event)) {
                    try {
                        game.addHorizontalLine(row + 1, col);
                    } catch (InvalidMoveException e) {
                        e.printStackTrace();
                    }
                    Main.getCom().putLine(true, row + 1, col);
                }

                playersList.getItems().clear();
            });
        }

        private boolean isLeft(MouseEvent event) {
            if (event.getY() > DOT_DIM && event.getY() < getHeight() - DOT_DIM)
                if (event.getX() > 0 && event.getX() < DOT_DIM)
                    return true;
            return false;
        }

        private boolean isRight(MouseEvent event) {
            if (event.getY() > DOT_DIM && event.getY() < getHeight() - DOT_DIM)
                if (event.getX() > getWidth() - DOT_DIM && event.getX() < getWidth())
                    return true;
            return false;
        }

        private boolean isTop(MouseEvent event) {
            if (event.getX() > DOT_DIM && event.getX() < getWidth() - DOT_DIM)
                if (event.getY() > 0 && event.getY() < DOT_DIM)
                    return true;
            return false;
        }

        private boolean isBottom(MouseEvent event) {
            if (event.getX() > DOT_DIM && event.getX() < getWidth() - DOT_DIM)
                if (event.getY() > getHeight() - DOT_DIM && event.getY() < getHeight())
                    return true;
            return false;
        }

        public void update() {
            GraphicalPlayer left   = ((GraphicalPlayer) current.getLeft());
            GraphicalPlayer right  = ((GraphicalPlayer) current.getRight());
            GraphicalPlayer top    = ((GraphicalPlayer) current.getTop());
            GraphicalPlayer bottom = ((GraphicalPlayer) current.getBottom());
            GraphicalPlayer owner  = ((GraphicalPlayer) current.getOwner());

            if (left != null) {
                gc.setStroke(left.getColor());
                gc.setLineWidth(5);
                gc.strokeLine(0, DOT_DIM, 0, getHeight() - DOT_DIM);
            }
            if (right != null) {
                gc.setStroke(right.getColor());
                gc.setLineWidth(5);
                gc.strokeLine(getWidth(), DOT_DIM, getWidth(), getHeight() - DOT_DIM);
            }
            if (top != null) {
                gc.setStroke(top.getColor());
                gc.setLineWidth(5);
                gc.strokeLine(DOT_DIM, 0, getWidth() - DOT_DIM, 0);
            }
            if (bottom != null) {
                gc.setStroke(bottom.getColor());
                gc.setLineWidth(5);
                gc.strokeLine(DOT_DIM, getHeight(), getWidth() - DOT_DIM, getHeight());
            }
            if (owner != null) {
                gc.setFill(
                        new Color(
                                owner.getColor().getRed(),
                                owner.getColor().getGreen(),
                                owner.getColor().getBlue(),
                                0.2
                        ).brighter()
                );
                gc.fillRect(0, 0, getWidth(), getHeight());
                gc.setFill(Color.BLACK);
                gc.fillText(
                        owner.getName().substring(0, 1).toUpperCase(),
                        0,
                        0
                );
            }
        }
    }
}
