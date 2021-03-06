package controller;

import cons.Constants;
import core.data.model.GraphicalPlayer;
import gui.GameApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.UnaryOperator;

/**
 * <p> This is the controller class of welcome screen </p> <p> Created by Majid Vaghari on 12/8/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.3.0
 * @since version 1.3.0
 */
public class WelcomeScreenController implements Initializable {
    private static Tooltip successMessage = new Tooltip("Ready to connect.");
    private static Tooltip failureMessage = new Tooltip("Connection error. Check settings.");

    private static String ipRegex = makePartialIPRegex();
    @FXML
    private TextField           username;
    @FXML
    private ColorPicker         color;
    @FXML
    private Button              connectButton;
    @FXML
    private ChoiceBox<GameMode> mode;

    private Future<?> testConnection;
    private boolean testing = true;

    private static String makePartialIPRegex() {
        String partialBlock           = "(([01]?[0-9]{0,2})|(2[0-4][0-9])|(25[0-5]))";
        String subsequentPartialBlock = "(\\." + partialBlock + ")";
        String ipAddress              = partialBlock + "?" + subsequentPartialBlock + "{0,3}";
        return "^" + ipAddress;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mode.getItems().addAll(GameMode.CREATE, GameMode.JOIN);
        mode.setValue(GameMode.CREATE);

        connectButton.setTooltip(failureMessage);

        testConnection = Main.submitTask((Callable<?>) () -> {
            while (testing) {
                if (Main.checkConnection()) {
                    connectButton.setDisable(false);
                    connectButton.setTooltip(successMessage);
                } else {
                    connectButton.setDisable(true);
                    connectButton.setTooltip(failureMessage);
                }

                Thread.sleep(Constants.SENDER_WAITING_TIME);
            }

            return null;
        });
    }

    @FXML
    public void connect(ActionEvent event) throws IOException {
        testing = false;
        testConnection.cancel(true);

        Main.connect();
        Main.getCom().handshake(new GraphicalPlayer(username.getText(), color.getValue(), username.getText()));

        connectButton.getScene().getWindow().hide();

        FXMLLoader loader = null;

        if (mode.getValue() == GameMode.CREATE) {
            loader = new FXMLLoader(GameApp.class.getResource("../gui/statics/create.fxml"));
            Parent root            = loader.load();
            Scene  scene           = new Scene(root);
            Stage  createGameStage = new Stage();
            createGameStage.setScene(scene);
            createGameStage.setOnCloseRequest(Main:: close);
            createGameStage.setTitle("Dots and Boxes - Create Game");
            createGameStage.show();
        } else if (mode.getValue() == GameMode.JOIN) {
            loader = new FXMLLoader(GameApp.class.getResource("../gui/statics/join.fxml"));
            Parent root          = loader.load();
            Scene  scene         = new Scene(root);
            Stage  joinGameStage = new Stage();
            joinGameStage.setScene(scene);
            joinGameStage.setOnCloseRequest(Main:: close);
            joinGameStage.setTitle("Dots and Boxes - Join Game");
            joinGameStage.show();
        }


    }

    @FXML
    public void showConfig(ActionEvent event) {
        Dialog<InetAddress> dialog      = new Dialog<>();
        FlowPane            content     = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        TextField           ipTextField = new TextField();
        content.setPadding(new Insets(25));
        final UnaryOperator<TextFormatter.Change> ipAddressFilter = c -> {
            String text = c.getControlNewText();
            if (text.matches(ipRegex)) {
                return c;
            } else {
                return null;
            }
        };
        ipTextField.setTextFormatter(new TextFormatter<>(ipAddressFilter));
        ipTextField.setText(Main.getServerAddress() == null ? "" : Main.getServerAddress().getHostAddress());
        content.getChildren().add(new Label("Server IP:"));
        content.getChildren().add(ipTextField);
        DialogPane pane = new DialogPane();
        pane.setHeaderText("Server IP Configuration");
        pane.setContent(content);
        pane.getButtonTypes().add(new ButtonType("Save", ButtonBar.ButtonData.FINISH));
        pane.getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setDialogPane(pane);
        dialog.setResultConverter(
                buttonType -> {
                    try {
                        return buttonType.getButtonData().isCancelButton()
                               ? null : InetAddress.getByName(ipTextField.getText());
                    } catch (UnknownHostException e) {
                        return null;
                    }
                });
        Optional<InetAddress> serverAddress = dialog.showAndWait();
        if (serverAddress.isPresent())
            Main.setServerAddress(serverAddress.get());
    }

    private enum GameMode {
        CREATE,
        JOIN;

        @Override
        public String toString() {
            if (this == CREATE)
                return "Create";
            if (this == JOIN)
                return "Join";

            return "";
        }
    }
}