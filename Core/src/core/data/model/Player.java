package core.data.model;

/**
 * Created by Majid Vaghari on 11/16/2015.
 */
public abstract class Player implements Comparable<Player> {
    private String name;
    private int    numOfOwnedBoxes;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Player p) {
        return this.getNumOfOwnedBoxes() - p.getNumOfOwnedBoxes();
    }

    public int getNumOfOwnedBoxes() {
        return numOfOwnedBoxes;
    }

    public void setNumOfOwnedBoxes(int numOfOwnedBoxes) {
        this.numOfOwnedBoxes = numOfOwnedBoxes;
    }
}
