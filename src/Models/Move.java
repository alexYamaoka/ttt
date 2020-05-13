package Models;

import Shared.UserInformation;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Move extends BaseModel {
    private String token;
    private int row;
    private int column;
    private UserInformation userInformation;
    private Timestamp moveTime;
    private String gameId;
    private String PlayerId;

    public void setMoveTime(Timestamp moveTime) {
        this.moveTime = moveTime;
    }

    public Move(){};

    public Move(int row, int column, UserInformation userInformation, String gameId) {
        this.row = row;
        this.column = column;
        this.userInformation = userInformation;
        this.gameId = gameId;
        moveTime = new Timestamp(System.currentTimeMillis());
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    private String createDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Timestamp getMoveTime() {
        return moveTime;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMove() {
        return row + " " + column + " " + token;
    }

    public String getGameId() {
        return gameId;
    }
}
