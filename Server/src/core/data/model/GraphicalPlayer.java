package core.data.model;

import javafx.scene.paint.Color;

/**
 * Created by Majid Vaghari on 11/17/2015.
 */
public class GraphicalPlayer extends Player {
    private final Color color;

    public GraphicalPlayer(String name, int id) {
        super(name, id);
        this.color = null;
    }
}
