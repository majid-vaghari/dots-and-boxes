package core.data.structure;

import core.data.model.Player;

/**
 * <p> This interface models boxes in the game. the boxes should have owners (Owner is the last person who adds a line
 * to the box and completes it) and we keep track of each side of the box (top, bottom, left and right). </p> <p> <p>
 * There will be a Square class in each phase of the project implementing the methods of this interface. </p> <p> <p>
 * Important note: the class implementing this interface should have default constructor which will be used later to
 * make new instances of that class with reflection API. </p>
 * <p>
 * Created by Majid Vaghari on 11/17/2015.
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public interface Box {
    /**
     * @return the owner of the current box
     */
    Player getOwner();


    /**
     * @return the player who owns the top side of the box
     */
    Player getTop();

    /**
     * @return the player who owns the bottom side of the box
     */
    Player getBottom();

    /**
     * @return the player who owns the left side of the box
     */
    Player getLeft();

    /**
     * @return the player who owns the right side of the box
     */
    Player getRight();


    /**
     * Sets the top side of this box
     *
     * @param top the player who is making the move
     *
     * @return true if the operation was successful, false otherwise (if the line is already set)
     */
    boolean setTop(Player top);

    /**
     * Sets the bottom side of this box
     *
     * @param bottom the player who is making the move
     *
     * @return true if the operation was successful, false otherwise (if the line is already set)
     */
    boolean setBottom(Player bottom);

    /**
     * Sets the left side of this box
     *
     * @param left the player who is making the move
     *
     * @return true if the operation was successful, false otherwise (if the line is already set)
     */
    boolean setLeft(Player left);

    /**
     * Sets the right side of this box
     *
     * @param right the player who is making the move
     *
     * @return true if the operation was successful, false otherwise (if the line is already set)
     */
    boolean setRight(Player right);
}
