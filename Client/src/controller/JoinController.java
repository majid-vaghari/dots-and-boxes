package controller;

import core.data.model.GraphicalSquare;
import gui.GameApp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import net.communication.GameAuthenticationException;
import net.communication.GameNotFoundException;
import net.communication.data.GameConfigurations;

import java.io.IOException;
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
    public Button joinButton;
    List<GameConfigurations> configs = Main.getCom().listGames();
    GameConfigurations gc;

    public void joinGame(ActionEvent actionEvent) {
        String gameToJoin = gameslist.getSelectionModel().getSelectedItem();
        String passToJoin = joinpass.getText();
        System.out.println();
        try {
            gc = configs.get(gameslist.getSelectionModel().getSelectedIndex());
            int n = gc.getBoardSize();
            GraphicalSquare[][] boxes = new GraphicalSquare[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    boxes[i][j] = new GraphicalSquare(i,j);
                }
            }


            Main.getCom().joinGame(gameToJoin, passToJoin, boxes);
            joinButton.getScene().getWindow().hide();

            Parent root = FXMLLoader.load(getClass().getResource("../gui/statics/game.fxml"));
            Scene scene = new Scene(root, 600, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(Main::close);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameslist.setItems(FXCollections.observableArrayList());
        for (GameConfigurations config: configs) {
            gameslist.getItems().add(config.getName());
        }
    }
}
