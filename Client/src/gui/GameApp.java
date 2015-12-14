package gui;/**
 * Created by Amin on 12/9/2015.
 */

import controller.GameController;
import controller.Main;
import core.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statics/game.fxml"));
        Parent root = loader.load();
        GameController controller = loader.<GameController>getController();
        controller.initData(getParameters().getNamed().get("name"), Integer.valueOf(getParameters().getNamed().get("boardsize")));
    }
}
