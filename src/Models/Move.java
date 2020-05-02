package Models;

import Shared.UserInformation;
import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Move extends BaseModel {
    private String token;
    private int row;
    private int column;
    private UserInformation userInformation;
    private String dateAndTime;
    private String gameName;

    public Move(int row, int column, UserInformation userInformation, String gameName)
    {
        this.row = row;
        this.column = column;
        this.userInformation = userInformation;
        this.gameName = gameName;
        dateAndTime = createDateAndTime();
    }

    private String createDateAndTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public int getRow()
    {
        return row;
    }

    public int getColumn()
    {
        return column;
    }

    public String getDateAndTime()
    {
        return dateAndTime;
    }

    public UserInformation getUserInformation()
    {
        return userInformation;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getMove() {
        return row + " " + column + " " + token;
    }

    public String getGameName()
    {
        return gameName;
    }
}
