package gui;/**
 * Created by Amin on 12/4/2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WecomeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("statics/welcome-old.fxml"));
        } catch (IOException e) {}

        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();

    }
}
