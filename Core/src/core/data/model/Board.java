package core.data.model;

import core.data.structure.Box;
import core.data.structure.List2D;

/**
 * Created by Majid Vaghari on 11/16/2015.
 */
public class Board<B extends Box> {
    private List2D<B> boxes;

    public Board(B[][] boxes) {
        this.boxes = new List2D<>(boxes);
    }

    public Board(Class<B> type, int size) {
        boxes = new List2D<>(type, size);
    }

    public int size() {
        return boxes.size();
    }

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

    public Player getVerticalLine(int row, int col) {
        B left = boxes.getBox(row, col - 1);

        return left == null ? boxes.getBox(row, col).getLeft() : left.getRight();
    }

    public Player getHorizontalLine(int row, int col) {
        B top = boxes.getBox(row - 1, col);

        return top == null ? boxes.getBox(row, col).getTop() : top.getBottom();
    }

    public Player getBoxOwner(int row, int col) {
        return boxes.getBox(row, col).getOwner();
    }
}
