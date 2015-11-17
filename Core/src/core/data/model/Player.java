package core.data.model;

/**
 * Created by Majid Vaghari on 11/16/2015.
 */
public abstract class Player {
    private final String name;
    private final int    id;

    public Player(final String name, final int id) {
        this.name = name;
        this.id = id;
    }
}
