package core;

import core.data.model.Board;
import core.data.model.Player;
import core.data.structure.Box;

import java.util.LinkedList;

/**
 * <p> This is the main class that handles the game play. instances of this class can also handle multiple games. </p>
 * <p> Created by Majid Vaghari on 11/16/2015. </p>
 *
 * @param <B> a Box type given to the class which represents the boxes in the game (can be graphical components later in
 *            the design process)
 *
 * @author Majid Vaghari
 * @version 1.0.0
 * @see Box
 */
public class Game<B extends Box> {
    /**
     * This is a Board object representing the game board
     *
     * @see Board
     * @since version 1.0.0
     */
    private final Board<B>           gameBoard;
    /**
     * This field holds the players currently on the game. In every instant of time the head of this list is the next
     * player to play the game.
     *
     * @see Player
     * @see LinkedList
     * @since version 1.0.0
     */
    private final LinkedList<Player> players;
    /**
     * Holds the number of available moves can be played. Used later to check if the game has ended.
     *
     * @since version 1.0.0
     */
    private       int                availableMoves;

    /**
     * Initializer block creates a linked list for players
     *
     * @since version 1.0.0
     */ {
        players = new LinkedList<>();
    }

    /**
     * Will create a new instance of the game.
     *
     * @param boxes a 2D array (matrix) holding boxes of the game.
     *
     * @since version 1.0.0
     */
    public Game(B[][] boxes) {
        this.gameBoard = new Board<>(boxes);
        availableMoves = 2 * boxes.length * (boxes.length + 1);
    }

    /**
     * Create a new instance of the game of the known board size.
     *
     * @param type type of Boxes (used to create array of this type in lower levels)
     * @param size board size
     *
     * @since version 1.0.0
     */
    public Game(Class<B> type, int size) {
        this.gameBoard = new Board<>(type, size);
        this.availableMoves = 2 * size * (size + 1);
    }

    /**
     * Every player who wants to join the game should use this method. Simply adds the new player to end of the turn
     * queues (allows players to join in middle of the game)
     *
     * @param newPlayer player who wants to join the game.
     *
     * @since version 1.0.0
     */
    public void playerJoin(Player newPlayer) {
        players.addLast(newPlayer);
    }

    /**
     * Players in the game can leave (resign) any time they want. invoking this method and giving the reference to that
     * player will do the job. Players can leave in middle of the game.
     *
     * @param player player who wants to leave the game.
     *
     * @since version 1.0.0
     */
    public void playerLeave(Player player) {
        if (players.contains(player))
            players.remove(player);
    }

    /**
     * Get's the player who will play after playerIdx moves.
     *
     * @param playerIdx index of desired player.
     *
     * @return the player playing after playerIdx moves.
     *
     * @since version 1.0.0
     */
    public Player getPlayer(int playerIdx) {
        return players.get(playerIdx);
    }

    /**
     * @return number of players currently in the game.
     *
     * @since version 1.0.0
     */
    public int numOfPlayers() {
        return players.size();
    }

    /**
     * Adds a vertical line to the given row and column.
     *
     * @param row row of the line to add.
     * @param col column of the line to add.
     *
     * @since version 1.0.0
     */
    public void addVerticalLine(int row, int col) {
        if (getVerticalLine(row, col) == null) {
            if (!gameBoard.addVerticalLine(getCurrentPlayer(), row, col))
                nextPlayer();
            else
                getCurrentPlayer().setNumOfOwnedBoxes(numOfBoxes(getCurrentPlayer()));
            availableMoves--;
        }
    }

    /**
     * Gets the vertical line in the position specified.
     *
     * @param row row of the desired line.
     * @param col column of the desired line.
     *
     * @return the player owning that line.
     *
     * @since version 1.0.0
     */
    public Player getVerticalLine(int row, int col) {
        return gameBoard.getVerticalLine(row, col);
    }

    /**
     * @return the player who should play in the next move
     *
     * @since version 1.0.0
     */
    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    /**
     * gives the turn to the next player
     *
     * @since version 1.0.0
     */
    public void nextPlayer() {
        players.addLast(players.removeFirst());
    }

    /**
     * Gets the number of boxes belonging to a specified player.
     *
     * @param player the player
     *
     * @return number of boxes belonging to that player.
     *
     * @since version 1.0.0
     */
    public int numOfBoxes(Player player) {
        if (player == null)
            return 0;

        int size  = boardSize();
        int count = 0;

        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
                if (player.equals(getBoxOwner(row, col)))
                    count++;

        return count;
    }

    /**
     * @return size of the game board.
     *
     * @since version 1.0.0
     */
    public int boardSize() {
        return gameBoard.size();
    }

    /**
     * Gets the owner of a specified box.
     *
     * @param row row of the desired box
     * @param col column of the desired box
     *
     * @return the owner of that box (null if there isn't a box there, yet)
     *
     * @since version 1.0.0
     */
    public Player getBoxOwner(int row, int col) {
        return gameBoard.getBoxOwner(row, col);
    }

    /**
     * Adds a horizontal line in the specific location.
     *
     * @param row row of the desired line to place
     * @param col column of that line
     *
     * @since version 1.0.0
     */
    public void addHorizontalLine(int row, int col) {
        if (getHorizontalLine(row, col) == null) {
            if (!gameBoard.addHorizontalLine(getCurrentPlayer(), row, col))
                nextPlayer();
            else
                getCurrentPlayer().setNumOfOwnedBoxes(numOfBoxes(getCurrentPlayer()));
            availableMoves--;
        }
    }

    /**
     * Gets the owner of the specified line in the given location
     *
     * @param row row of the desired line
     * @param col column of the desired line
     *
     * @return the owner of that line
     *
     * @since version 1.0.0
     */
    public Player getHorizontalLine(int row, int col) {
        return gameBoard.getHorizontalLine(row, col);
    }

    /**
     * sorts the players with respect to the number of boxes they own. (checks to see if the game has finished yet)
     *
     * @since version 1.0.0
     */
    public void sortPlayers() {
        if (isEndGame())
            players.sort(Player:: compareTo);
    }

    /**
     * @return true if the game has ended. false otherwise.
     *
     * @since version 1.0.0
     */
    public boolean isEndGame() {
        return availableMoves <= 0;
    }
    // write methods to handle game play
    // controller class must call and invoke methods of this class.
}
