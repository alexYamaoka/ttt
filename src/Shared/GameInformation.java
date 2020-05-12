package Shared;

import java.sql.Timestamp;

public class GameInformation {
    private String player1Username;
    private String player2Username;
    private String id;
    private String gameStatus;
    private Timestamp startTime;
    private Timestamp endTime;
    private String startingPlayerId;
    private String winningPlayerId;

    public String getPlayer1Username() {
        return player1Username;
    }

    public void setPlayer1Username(String player1Username) {
        this.player1Username = player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStartingPlayerId() {
        return startingPlayerId;
    }

    public void setStartingPlayerId(String startingPlayerId) {
        this.startingPlayerId = startingPlayerId;
    }

    public String getWinningPlayerId() {
        return winningPlayerId;
    }

    public void setWinningPlayerId(String winningPlayerId) {
        this.winningPlayerId = winningPlayerId;
    }
}
