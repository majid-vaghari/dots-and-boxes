package core;

import core.data.model.Player;

/**
 * <p> This exception is thrown when the move was already made and can't be done. </p> <p> Created by Majid Vaghari on
 * 11/23/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.1.0
 * @since version 1.1.0
 */
public class InvalidMoveException extends Exception {
    /**
     * This constructor is used if the game has already ended and therefore no moves can be made.
     */
    public InvalidMoveException() {
        super("The game has already ended.");
    }

    /**
     * This constructor is used if the selected line has already owned by another player and therefore the move cannot
     * be made
     *
     * @param p the player owning the line
     */
    public InvalidMoveException(Player p) {
        super("Move already made by player: " + p.toString());
    }
}
