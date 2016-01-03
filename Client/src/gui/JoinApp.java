package gui;/**
 * Created by Amin on 12/9/2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("statics/joining.fxml"));
        } catch (IOException e) {}

        Scene scene = new Scene(root);
        primaryStage.setTitle("Join Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
