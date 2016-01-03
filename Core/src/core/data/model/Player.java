package core.data.model;

/**
 * <p> This is an abstract class which models player in our code (can be later extended to graphical versions) </p> <p>
 * Players are comparable. One player is considered "greater" than another if it has more boxes in the game. </p> <p>
 * Created by Majid Vaghari on 11/16/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public abstract class Player implements Comparable<Player> {
    /**
     * Name of the player
     */
    private String name;
    /**
     * The number of boxes owned by this player.
     */
    private int    numOfOwnedBoxes;

    /**
     * Creates new player with the specifed name
     *
     * @param name name of the player to set
     */
    public Player(String name) {
        setName(name);
    }

    /**
     * @return a string representation of this object (name of player)
     */
    @Override
    public String toString() {
        return getName() + " Score: " + getNumOfOwnedBoxes();
    }

    /**
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player
     *
     * @param name the player name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return number of owned boxes by this player
     */
    public int getNumOfOwnedBoxes() {
        return numOfOwnedBoxes;
    }

    /**
     * @param numOfOwnedBoxes the number of owned boxes to set
     */
    public void setNumOfOwnedBoxes(int numOfOwnedBoxes) {
        this.numOfOwnedBoxes = numOfOwnedBoxes;
    }

    /**
     * Compares this object to another player.
     *
     * @param p another player to compare with
     *
     * @return difference between number of owned boxes by the invoking object and p
     */
    @Override
    public int compareTo(Player p) {
        return this.getNumOfOwnedBoxes() - p.getNumOfOwnedBoxes();
    }
}
