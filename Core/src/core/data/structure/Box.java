package core.data.structure;

import core.data.model.Player;

/**
 * <p> This interface models boxes in the game. the boxes should have owners (Owner is the last person who adds a line
 * to the box and completes it) and we keep track of each side of the box (top, bottom, left and right). </p>
 * <p>
 * <p> There will be a Square class in each phase of the project implementing the methods of this interface. </p>
 * <p>
 * <p> Important note: the class implementing this interface should have default constructor which will be used later to
 * make new instances of that class with reflection API. </p>
 * <p>
 * Created by Majid Vaghari on 11/17/2015.
 */
public interface Box {
    Player getOwner();


    Player getTop();

    Player getBottom();

    Player getLeft();

    Player getRight();


    boolean setTop(Player up);

    boolean setBottom(Player down);

    boolean setLeft(Player left);

    boolean setRight(Player right);
}
