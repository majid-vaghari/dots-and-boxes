package core.data.model;

import core.data.structure.Box;
import core.data.structure.List2D;

/**
 * <p> This class models the board using a List2D. </p> <p> Created by Majid Vaghari on 11/16/2015. </p>
 *
 * @param <B> type of boxes
 *
 * @author Majid Vaghari
 * @version 1.0.0
 * @see List2D
 */
public class Board<B extends Box> {
    /**
     * a two dimensional list of boxes
     */
    private List2D<B> boxes;

    /**
     * Constructor to make a board using a two dimensional array of boxes
     *
     * @param boxes initial value of boxes
     */
    public Board(B[][] boxes) {
        this.boxes = new List2D<>(boxes);
    }

    /**
     * This constructor will create a board knowing the type of boxes and board size.
     *
     * @param type type of boxes (used to make new array using reflection API)
     * @param size board size
     */
    public Board(Class<B> type, int size) {
        boxes = new List2D<>(type, size);
    }

    /**
     * @return size of the board
     */
    public int size() {
        return boxes.size();
    }

    /**
     * Adds a vertical line to this board
     *
     * @param player player who makes the move
     * @param row    row of the selected line
     * @param col    column of the selected line
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean addVerticalLine(Player player, int row, int col) {
        B left  = boxes.getBox(row, col - 1);
        B right = boxes.getBox(row, col);

        boolean complete = false;
        if (left != null)
            complete = left.setRight(player);
        if (right != null)
            complete |= right.setLeft(player);

        return complete;
    }

    /**
     * Adds a horizontal line to this board
     *
     * @param player player who makes the move
     * @param row    row of the selected line
     * @param col    column of the selected line
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean addHorizontalLine(Player player, int row, int col) {
        B top    = boxes.getBox(row - 1, col);
        B bottom = boxes.getBox(row, col);

        boolean complete = false;
        if (top != null)
            complete = top.setBottom(player);
        if (bottom != null)
            complete |= bottom.setTop(player);

        return complete;
    }

    /**
     * Gets the line in a specified location
     *
     * @param row row of the selected line
     * @param col column of the selected line
     *
     * @return the owner of the line
     */
    public Player getVerticalLine(int row, int col) {
        B left = boxes.getBox(row, col - 1);

        return left == null ? boxes.getBox(row, col).getLeft() : left.getRight();
    }

    /**
     * Gets the line in a specified location
     *
     * @param row row of the selected line
     * @param col column of the selected line
     *
     * @return the owner of the line
     */
    public Player getHorizontalLine(int row, int col) {
        B top = boxes.getBox(row - 1, col);

        return top == null ? boxes.getBox(row, col).getTop() : top.getBottom();
    }

    /**
     * Gets the owner of a specific box
     *
     * @param row row of the box
     * @param col column of the box
     *
     * @return the owner of that box (null if it's empty)
     */
    public Player getBoxOwner(int row, int col) {
        return boxes.getBox(row, col).getOwner();
    }
}
