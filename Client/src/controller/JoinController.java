package controller;

import core.data.model.GraphicalSquare;
import gui.GameApp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import net.communication.GameAuthenticationException;
import net.communication.data.GameConfigurations;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Amin on 12/9/2015.
 */
public class JoinController implements Initializable{
    @FXML
    public PasswordField joinpass;
    @FXML
    public ListView<String> gameslist;
    List<GameConfigurations> configs = Main.getCom().listGames();
    GameConfigurations gc;
    public void joinGame(ActionEvent actionEvent) {
        String gameToJoin = gameslist.getSelectionModel().getSelectedItem();
        String passToJoin = joinpass.getText();
        System.out.println();
        try {
            Main.getCom().joinGame(gameToJoin, passToJoin);
//            gc = Main.getCom().getConfig();
            gc = configs.get(gameslist.getSelectionModel().getSelectedIndex());
            int n = gc.getBoardSize();
            GraphicalSquare[][] boxes = new GraphicalSquare[n][n];
            GameApp.launch("--name=" + gc.getName(), "--boardsize=" + String.valueOf(gc.getBoardSize()));
//
// for (int i = 0; i < n; i++) {
//                for (int j = 0; j < n; j++) {
//                    boxes[i][j] = new GraphicalSquare();
//                }
//            }
//
        } catch (GameAuthenticationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameslist.setItems(FXCollections.observableArrayList());
        for (GameConfigurations config: configs) {
            gameslist.getItems().add(config.getName());
        }
    }
}
