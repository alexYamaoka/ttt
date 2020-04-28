package Models;

import Shared.UserInformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Move extends BaseModel {
    // row
    // col
    // person who made the move
    // user
    // constructor would have a game and the player, row and col


    private int row;
    private int column;
    private UserInformation userInformation;
    private String dateAndTime;

    public Move(int row, int column, UserInformation userInformation)
    {
        this.row = row;
        this.column = column;
        this.userInformation = userInformation;
        dateAndTime = getCurrentDateAndTime();
    }

    private String getCurrentDateAndTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
