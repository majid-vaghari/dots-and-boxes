package controller;

import core.Game;
import core.data.model.GraphicalSquare;
import core.data.model.Player;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.communication.data.GameConfigurations;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Amin on 12/9/2015.
 */


public class GameController implements Initializable{

    @FXML
    public Label gameName;
    @FXML
    public ListView playersList;
    @FXML
    public Group boxWrapper;

    Game game;
//    GameConfigurations gc;
    GameConfigurations gameConfig;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Optional<Game<GraphicalSquare>> optGame = Main.getCom().getGame();
        Optional<GameConfigurations> optConfig = Main.getCom().getConfig();
        if (!optConfig.isPresent() || !optGame.isPresent()){
            System.err.println("No configuration is present");
            return;
            //TODO handle the exceptions
        }
        gameConfig = optConfig.get();
        game = optGame.get();
        gameName.setText(gameConfig.getName());
        playersList.setItems(FXCollections.observableArrayList());
        for (int i = 0; i < game.numOfPlayers() ; i++) {
            playersList.getItems().add(game.getPlayer(i).getName() + " Score: " + game.getPlayer(i).getNumOfOwnedBoxes());
        }

        for (int i = 0; i < gameConfig.getBoardSize() ; i++) {
            for (int j = 0; j < gameConfig.getBoardSize() ; j++) {
                GraphicalSquare square = new GraphicalSquare(i,j);
                square.setTranslateX(i*square.getSize());
                square.setTranslateY(j*square.getSize());

                boxWrapper.getChildren().add(square);
            }

        }


    }

}
