package core.data.structure;

import java.lang.reflect.Array;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class List2D<B extends Box> {
    private final B[][] boxes;

    public List2D(B[][] boxes) {
        this.boxes = boxes;
    }

    public List2D(Class<B> boxType, int boardSize) {
        boxes = (B[][]) Array.newInstance(boxType, boardSize, boardSize);

        for (int row = 0; row < boxes.length; row++)
            for (int col = 0; col < boxes[row].length; col++)
                try {
                    boxes[row][col] = boxType.newInstance(); // calling default constructor
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
    }

    public int size() {
        return boxes.length;
    }

    public B getBox(int row, int col) {
        try {
            return boxes[row][col];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
