package controller;

import core.data.model.GraphicalSquare;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import net.communication.GameAuthenticationException;
import net.communication.GameNotFoundException;
import net.communication.data.GameConfigurations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Majid Vaghari on 12/28/2015.
 */
public class JoinController implements Initializable {
    @FXML
    private ListView<GameConfigurations> gamesList;
    @FXML
    private Label                        boardSize;
    @FXML
    private Label                        gameName;
    @FXML
    private Label                        maxNumOfPlayer;
    @FXML
    private RadioButton                  isPasswordProtected;
    @FXML
    private RadioButton                  isAsync;
    @FXML
    private PasswordField                joinPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamesList.getItems().addAll(Main.getCom().listGames());

    }

    public void join(ActionEvent event) {
        GameConfigurations gc         = gamesList.getSelectionModel().getSelectedItem();
        String             gameToJoin = gc.getName();
        String             passToJoin = joinPass.getText();
        try {
            // initializing boxes
            int                 n     = gc.getBoardSize();
            GraphicalSquare[][] boxes = new GraphicalSquare[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    boxes[i][j] = new GraphicalSquare();
                }
            }

            Main.setBoxes(boxes);

            // joining game
            Main.getCom().joinGame(gc, passToJoin, boxes);
            gamesList.getScene().getWindow().hide();

            Parent root  = FXMLLoader.load(getClass().getResource("../gui/statics/game.fxml"));
            Scene  scene = new Scene(root);
            Stage  stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(Main:: close);
            stage.setTitle("Dots and Boxes");
            stage.show();


        } catch (GameAuthenticationException e) {
            e.printStackTrace();
        } catch (GameNotFoundException e) {
            e.printStackTrace();
            System.err.println("Wrong password or undefined game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(Event event) {
        GameConfigurations selectedGame = gamesList.getSelectionModel().getSelectedItem();

        if (selectedGame == null)
            return;

        boardSize.setText(selectedGame.getBoardSize() + " x " + selectedGame.getBoardSize());
        gameName.setText(selectedGame.getName());
        maxNumOfPlayer.setText(String.valueOf(selectedGame.getNumOfPlayers()));
        isPasswordProtected.setSelected(selectedGame.isPasswordProtected());
        isAsync.setSelected(selectedGame.isAsyncMode());
    }
}
