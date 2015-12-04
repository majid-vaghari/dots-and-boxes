package core.data.model;

import javafx.scene.paint.Color;

/**
 * <p> This is a Graphical implementation of player and the only thing it has in addition to normal Player is a field
 * for its color. </p> <p> Created by Majid Vaghari on 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.1.0 Updated constructor and added setters and getters for color field
 * @see Player
 * @since version 1.0.0
 */
public class GraphicalPlayer extends Player {
    private Color color;

    public GraphicalPlayer(String name, Color color) {
        super(name);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
