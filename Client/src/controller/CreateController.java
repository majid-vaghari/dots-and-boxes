package controller;

import core.data.model.GraphicalSquare;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.communication.data.GameConfigurations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Amin on 12/9/2015.
 */
public class CreateController implements Initializable {


    @FXML
    private ChoiceBox<BoardSize> boardSize;
    @FXML
    private CheckBox             flex;
    @FXML
    private PasswordField        password;
    @FXML
    private TextField            gameName;
    @FXML
    private CheckBox             turns;
    @FXML
    private Spinner<Integer>     numOfPlayers;

    public void createGame(ActionEvent actionEvent) throws IOException {
        GameConfigurations gc = new GameConfigurations();
        gc.setAsyncMode(!turns.isSelected());
        gc.setFlexibleNumOfPlayers(flex.isSelected());
        gc.setPasswordProtected(!password.getText().isEmpty());
        gc.setBoardSize(boardSize.getValue().getValue());
        gc.setName(gameName.getText());
        gc.setPassword(password.getText());
        gc.setNumOfPlayers(numOfPlayers.getValue());
        GraphicalSquare[][] boxes = new GraphicalSquare[gc.getBoardSize()][gc.getBoardSize()];

        for (int i = 0; i < gc.getBoardSize(); i++) {
            for (int j = 0; j < gc.getBoardSize(); j++) {
                boxes[i][j] = new GraphicalSquare();
            }
        }

        Main.setBoxes(boxes);

        Main.getCom().createGame(gc, boxes);

        boardSize.getScene().getWindow().hide();

        Parent root  = FXMLLoader.load(getClass().getResource("../gui/statics/game.fxml"));
        Scene  scene = new Scene(root);
        Stage  stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(Main:: close);
        stage.setTitle("Dots and Boxes");
        stage.show();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardSize.getItems().addAll(BoardSize.SIX, BoardSize.SEVEN, BoardSize.EIGHT);
        boardSize.setValue(BoardSize.EIGHT);

        numOfPlayers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10));
    }

    private enum BoardSize {
        SIX(6), SEVEN(7), EIGHT(8);

        private int value;

        BoardSize(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value + " x " + value;
        }
    }
}
