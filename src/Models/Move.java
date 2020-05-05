package Models;

import Shared.UserInformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Move extends BaseModel {
    private String token;
    private int row;
    private int column;
    private UserInformation userInformation;
    private String dateAndTime;
    private String gameId;

    public Move(int row, int column, UserInformation userInformation, String gameId) {
        this.row = row;
        this.column = column;
        this.userInformation = userInformation;
        this.gameId = gameId;
        dateAndTime = createDateAndTime();
    }

    private String createDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getDateAndTime() {
        return dateAndTime;
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
