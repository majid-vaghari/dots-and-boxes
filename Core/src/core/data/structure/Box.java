package core.data.structure;

import core.data.model.Player;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public interface Box {
    Player getOwner();


    Player getUp();

    Player getDown();

    Player getLeft();

    Player getRight();


    void setUp(Player up);

    void setDown(Player down);

    void setLeft(Player left);

    void setRight(Player right);
}
