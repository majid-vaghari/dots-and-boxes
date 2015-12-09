package controller;

import gui.JoinApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Amin on 12/9/2015.
 */
public class JoinController implements Initializable{
    @FXML
    public PasswordField joinpass;
    @FXML
    public ListView<String> gameslist;
    ObservableList<String> games = FXCollections.observableArrayList("gg", "wp");
    public void joinGame(ActionEvent actionEvent) {
        System.out.println(gameslist.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameslist.setItems(games);

    }
}
