package app;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Shared.UserInformation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Server {

    private DataSource ds = DatabaseManager.getInstance();

    public boolean addgame(String gameId, Date startTime, Date endTime, int PlayerOneId, int PlayerTwoId,int StartingPlayerID,int WinningPlayerId){
        //BaseModel game = new Game(gameId,startTime,endTime,PlayerOneId,PlayerTwoId,StartingPlayerID,WinningPlayerId);
        return true;
    }

    public boolean DeleteUser(String username, String firstname, String lastname,String password) throws SQLException {
        ds.delete(username,firstname,lastname,password);
        return true;
    }

    public boolean login(String username, String password) throws SQLException {
        List flag;
        flag = ds.query(Shared.UserInformation.class, " username = '" + username + "' AND password = '" + password + "'");
        if(flag.isEmpty()){
            return false;
        }else return true;

    }

    public boolean updateUser(UserInformation user) throws SQLException {
        List User = (List) ds.update(user);
        if(User.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public boolean registerUser(String username, String firstname, String lastname,String password) throws SQLException {
        BaseModel user = new UserInformation(username, firstname, lastname,null, password);
        ds.insert(user);
            return true;
    }
}
