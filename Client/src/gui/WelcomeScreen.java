package gui;

import controller.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <p> This is the main screen that loads when the application runs. </p> <p> Created by Majid Vaghari on 12/8/2015.
 * </p>
 *
 * @author Majid Vaghari
 * @version 1.3.0
 * @since version 1.3.0
 */
public class WelcomeScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        Parent     root   = loader.load();
        Scene      scene  = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dots and Boxes - Welcome");
        primaryStage.setOnCloseRequest(Main:: close);

        primaryStage.show();
    }
}
