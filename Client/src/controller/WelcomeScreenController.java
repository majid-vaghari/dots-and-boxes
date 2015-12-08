package controller;

import cons.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.net.URL;
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
    public TextField   username;
    @FXML
    public ColorPicker color;
    @FXML
    public Button      connectButton;

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
        connectButton.setTooltip(failureMessage);

        Main.submitTask((Callable<?>) () -> {
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
    public void connect(ActionEvent event) {
        testing = false;
        testConnection.cancel(true);
    }

    @FXML
    public void showConfig(ActionEvent event) {
        Dialog<String> dialog      = new Dialog<>();
        FlowPane       content     = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        TextField      ipTextField = new TextField();
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
        content.getChildren().add(new Label("Server IP:"));
        content.getChildren().add(ipTextField);
        DialogPane pane = new DialogPane();
        pane.setHeaderText("Server IP Configuration");
        pane.setContent(content);
        pane.getButtonTypes().add(new ButtonType("Save", ButtonBar.ButtonData.APPLY));
        pane.getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setDialogPane(pane);
        dialog.setResultConverter(
                buttonType -> buttonType.getButtonData().isCancelButton() ? null : ipTextField.getText());
        Optional<String> serverAddress = dialog.showAndWait();
        if (serverAddress.isPresent())
            Main.setServerAddress(serverAddress.get());
    }
}
