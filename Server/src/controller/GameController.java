package controller;

import core.Game;
import core.data.model.GraphicalPlayer;
import core.data.model.GraphicalSquare;
import net.communication.data.GameConfigurations;

/**
 * <p> This class controls an instance of the game. </p> <p> Created by Majid Vaghari on 12/4/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.2.0
 * @since version 1.2.0
 */
public class GameController {
    private Game<GraphicalSquare> game;
    private GameConfigurations    configurations;
    private GraphicalPlayer       admin;

    public GameController() {

    }

    public Game<GraphicalSquare> getGame() {
        return game;
    }

    public GameConfigurations getConfigurations() {
        return configurations;
    }

    public GraphicalPlayer getAdmin() {
        return admin;
    }
}
