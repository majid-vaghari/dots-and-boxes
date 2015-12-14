package controller;

import com.sun.deploy.util.SessionState;
import core.Game;
import core.data.model.GraphicalSquare;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.client.ClientCom;
import net.communication.GameConfigurations;

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

    public void createGame(ActionEvent actionEvent) {
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
                boxes[i][j] = new GraphicalSquare();
            }
        }

        Main.getCom().createGame(gc, boxes);


    }
}
