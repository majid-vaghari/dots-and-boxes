package gui;/**
 * Created by Amin on 12/9/2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statics/create.fxml"));
        Parent     root   = loader.load();
        Scene      scene  = new Scene(root);
        primaryStage.setTitle("Dots and Boxes - Create Game");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
