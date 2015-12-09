package net.communication;

/**
 * <p> This class holds information about the game and the configurations which initiates the game. </p> <p> Created by
 * Majid Vaghari on 12/4/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.1.0
 * @since version 1.1.0
 */
public class GameConfigurations {
    private boolean flexibleNumOfPlayers;
    private boolean passwordProtected;
    private boolean asyncMode;
    private int     boardSize;
    private int     numOfPlayers;
    private String  name;
    private String  password;

    public boolean isFlexibleNumOfPlayers() {
        return flexibleNumOfPlayers;
    }

    public void setFlexibleNumOfPlayers(boolean flexibleNumOfPlayers) {
        this.flexibleNumOfPlayers = flexibleNumOfPlayers;
    }

    public boolean isPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }

    public boolean isAsyncMode() {
        return asyncMode;
    }

    public void setAsyncMode(boolean asyncMode) {
        this.asyncMode = asyncMode;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
