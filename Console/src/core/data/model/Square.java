package core.data.model;

import core.data.structure.Box;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class Square implements Box {
    private Player owner;

    private Player up;
    private Player bottom;
    private Player left;
    private Player right;

    public Square() {

    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public Player getTop() {
        return this.up;
    }

    @Override
    public Player getBottom() {
        return this.bottom;
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
    public boolean setTop(Player top) {
        this.up = top;
        if (right != null && bottom != null && left != null) {
            this.owner = top;
            return true;
        }
        return false;
    }

    @Override
    public boolean setBottom(Player down) {
        this.bottom = down;
        if (up != null && right != null && left != null) {
            this.owner = down;
            return true;
        }
        return false;
    }

    @Override
    public boolean setLeft(Player left) {
        this.left = left;
        if (up != null && bottom != null && right != null) {
            this.owner = left;
            return true;
        }
        return false;
    }

    @Override
    public boolean setRight(Player right) {
        this.right = right;
        if (up != null && bottom != null && left != null) {
            this.owner = right;
            return true;
        }
        return false;
    }
}
