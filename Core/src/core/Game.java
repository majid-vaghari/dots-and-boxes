package core;

import core.data.model.Board;
import core.data.model.Player;
import core.data.structure.Box;

import java.util.LinkedList;

/**
 * Created by Majid Vaghari on 11/16/2015.
 */
public class Game<B extends Box> {
    private final Board<B>           gameBoard;
    private final LinkedList<Player> players;
    private       int                availableMoves;

    {
        players = new LinkedList<>();
    }

    public Game(B[][] boxes) {
        this.gameBoard = new Board<>(boxes);
        availableMoves = 2 * boxes.length * (boxes.length + 1);
    }

    public Game(Class<B> type, int size) {
        this.gameBoard = new Board<>(type, size);
        this.availableMoves = 2 * size * (size + 1);
    }

    public void playerJoin(Player newPlayer) {
        players.addLast(newPlayer);
    }

    public void playerLeave(Player player) {
        players.remove(player);
    }

    public Player getPlayer(int playerIdx) {
        return players.get(playerIdx);
    }

    public int numOfPlayers() {
        return players.size();
    }

    public void addVerticalLine(int row, int col) {
        if (getVerticalLine(row, col) == null) {
            if (!gameBoard.addVerticalLine(getCurrentPlayer(), row, col))
                nextPlayer();
            else
                getCurrentPlayer().setNumOfOwnedBoxes(numOfBoxes(getCurrentPlayer()));
            availableMoves--;
        }
    }

    public Player getVerticalLine(int row, int col) {
        return gameBoard.getVerticalLine(row, col);
    }

    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    public void nextPlayer() {
        players.addLast(players.removeFirst());
    }

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

    public int boardSize() {
        return gameBoard.size();
    }

    public Player getBoxOwner(int row, int col) {
        return gameBoard.getBoxOwner(row, col);
    }

    public void addHorizontalLine(int row, int col) {
        if (getHorizontalLine(row, col) == null) {
            if (!gameBoard.addHorizontalLine(getCurrentPlayer(), row, col))
                nextPlayer();
            else
                getCurrentPlayer().setNumOfOwnedBoxes(numOfBoxes(getCurrentPlayer()));
            availableMoves--;
        }
    }

    public Player getHorizontalLine(int row, int col) {
        return gameBoard.getHorizontalLine(row, col);
    }

    public void sortPlayers() {
        if (isEndGame())
            players.sort(Player:: compareTo);
    }

    public boolean isEndGame() {
        return availableMoves <= 0;
    }
    // write methods to handle game play
    // controller class must call and invoke methods of this class.
}
