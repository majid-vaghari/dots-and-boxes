package core.data.model;

import core.data.structure.Box;
import javafx.scene.Scene;

/**
 * <p> This class implements the original Box class located in the data model section of the Core of the game. This
 * abstract class models a Box Completely and also extends Scene class in JavaFX So a Graphical Square can later be
 * extended from it and implemented independently.</p> <p> Created by Majid Vaghari on 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.2.0
 * @since version 1.2.0
 */
public abstract class AbstractSquare extends Scene implements Box {
    /**
     * Owner of the box
     */
    private Player owner;

    /**
     * The player who played the top line
     */
    private Player top;
    /**
     * The player who played the bottom line
     */
    private Player bottom;
    /**
     * The player who played the left line
     */
    private Player left;
    /**
     * The player who played the right line
     */
    private Player right;

    /**
     * Default constructor to make instances of the box.
     */
    public AbstractSquare() { // TODO do instantiation stuff :P
        super(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getTop() {
        return this.top;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getBottom() {
        return this.bottom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getLeft() {
        return this.left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getRight() {
        return this.right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setTop(Player top) {
        this.top = top;
        if (right != null && bottom != null && left != null) {
            this.owner = top;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setBottom(Player bottom) {
        this.bottom = bottom;
        if (top != null && right != null && left != null) {
            this.owner = bottom;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setLeft(Player left) {
        this.left = left;
        if (top != null && bottom != null && right != null) {
            this.owner = left;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setRight(Player right) {
        this.right = right;
        if (top != null && bottom != null && left != null) {
            this.owner = right;
            return true;
        }
        return false;
    }
}
