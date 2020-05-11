package app;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Models.Game;
import Models.Move;
import Shared.UserInformation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Server {

    private DataSource ds = DatabaseManager.getInstance();

    public boolean DeleteUser(String Id) throws SQLException {
        return ds.delete(Id);
    }

    public boolean login(String username, String password) throws SQLException {
        List flag;
        flag = ds.query(Shared.UserInformation.class, " username = '" + username + "' AND password = '" + password + "'");
        if(flag.isEmpty()){
            return false;
        }else return true;

    }

    public boolean updateUser(String UpdateFirstName,String UpdateLastName,String UpdateUserName,String Id,String UpdatePassword) throws SQLException {
        ds.update(UpdateFirstName,UpdateLastName,UpdateUserName,Id,UpdatePassword);

        //List User = (List) ds.update(user);
        //if(User.isEmpty()){
          //  return false;
       // }else{
            return true;
        //}
    }
    public boolean registerUser(String username, String firstname, String lastname,String password) throws SQLException {
        BaseModel user = new UserInformation(username, firstname, lastname,null, password);
        ds.insert(user);
            return true;
    }
}
