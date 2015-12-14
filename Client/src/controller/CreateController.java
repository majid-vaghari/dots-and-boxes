package controller;

import com.sun.deploy.util.SessionState;
import core.Game;
import core.data.model.GraphicalSquare;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.client.ClientCom;
import net.communication.data.GameConfigurations;

import java.io.IOException;

/**
 * Created by Amin on 12/9/2015.
 */
public class CreateController {


    @FXML
    public ChoiceBox boardSize;
    @FXML
    public CheckBox flex;
    @FXML
    public PasswordField password;
    @FXML
    public TextField gameName;
    @FXML
    public CheckBox async;
    public Button createButton;

    public void createGame(ActionEvent actionEvent) throws IOException {
        GameConfigurations gc = new GameConfigurations();
        gc.setAsyncMode(async.isSelected());
        gc.setFlexibleNumOfPlayers(flex.isSelected());
        int n = Integer.parseInt(((String) boardSize.getSelectionModel().getSelectedItem()).substring(0, 1));
        gc.setBoardSize(n);
        gc.setName(gameName.getText());
        gc.setPassword(password.getText());
        GraphicalSquare[][] boxes = new GraphicalSquare[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boxes[i][j] = new GraphicalSquare(i,j);
            }
        }


        Main.getCom().createGame(gc, boxes);

        createButton.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("../gui/statics/game.fxml"));
        Scene scene = new Scene(root, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(Main::close);
        stage.setTitle("Dots and Boxes");
        stage.show();


    }
}
