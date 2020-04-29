package Models;

import Shared.UserInformation;

import java.sql.Date;

public class Game extends BaseModel {

    private Date startTime;
    private Date endTime;
    private long time;
    private UserInformation player1;
    private UserInformation player2;

    public UserInformation getPlayer1() {
        return player1;
    }

    public void setPlayer1(UserInformation player1) {
        this.player1 = player1;
    }

    public UserInformation getPlayer2() {
        return player2;
    }

    public void setPlayer2(UserInformation player2) {
        this.player2 = player2;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        startTime.setTime(this.time = System.currentTimeMillis());;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        endTime.setTime(this.time = System.currentTimeMillis());;
    }


}
