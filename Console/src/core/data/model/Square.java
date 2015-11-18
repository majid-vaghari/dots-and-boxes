package core.data.model;

import core.data.structure.Box;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class Square implements Box {
    private Player owner;

    private Player up;
    private Player down;
    private Player left;
    private Player right;

    public Square() {

    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public Player getUp() {
        return this.up;
    }

    @Override
    public Player getDown() {
        return this.down;
    }

    @Override
    public Player getLeft() {
        return this.left;
    }

    @Override
    public Player getRight() {
        return this.right;
    }

    @Override
    public void setRight(Player right) {
        this.right = right;
    }

    @Override
    public void setLeft(Player left) {
        this.left = left;
    }

    @Override
    public void setDown(Player down) {
        this.down = down;
    }

    @Override
    public void setUp(Player up) {
        this.up = up;
    }
}
