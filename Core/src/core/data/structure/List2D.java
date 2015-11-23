package core.data.structure;

import java.lang.reflect.Array;

/**
 * <p> This class models a data structure to implement a two dimensional list of boxes will be used later in data model
 * section. </p>
 * <p>
 * Created by Majid Vaghari on 11/17/2015.
 *
 * @param <B> type parameter of items to hold in the matrix
 *
 * @author Majid Vaghari
 * @version 1.0.0
 * @see Box
 */
public class List2D<B extends Box> {
    /**
     * a two dimensional array to hold the boxes.
     */
    private final B[][] boxes;

    /**
     * Constructor of the class which gets a two dimensional array and initializes the List
     *
     * @param boxes boxes of the game board.
     */
    public List2D(B[][] boxes) {
        this.boxes = boxes;
    }

    /**
     * Creates a list of known size.
     *
     * @param boxType   type of the box which will be used to make new instances (using reflection API)
     * @param boardSize size of the list
     */
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

    /**
     * @return list size (the number of rows and columns of the matrix are the same.
     */
    public int size() {
        return boxes.length;
    }

    /**
     * Gets the box in a specified location.
     *
     * @param row row of the box
     * @param col column of the box
     *
     * @return the box located in the specified row and column, null if the indexes are out of range
     */
    public B getBox(int row, int col) {
        try {
            return boxes[row][col];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
