package app;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Shared.UserInformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private DataSource ds = DatabaseManager.getInstance();

    public void f1() {
        //ds.delete();
    }

    public boolean DeleteUser(String username, String password) throws SQLException {
        //ds.delete(user);
        return true;
    }

    public boolean login(String username, String password) throws SQLException {
        List flag = new ArrayList<>();
        flag = ds.query(Shared.UserInformation.class, " username = '" + username + "' AND password = '" + password + "'");

        if(flag.isEmpty()){
            return false;
        }else return true;

    }

    public boolean updateUser(UserInformation user) throws SQLException {
        ds.update(user);
        return true;
    }

    public boolean registerUser(String username, String firstname, String lastname, String email, String password) throws SQLException {
        BaseModel user = new UserInformation(username, firstname, lastname, email, password);
        ds.insert(user);
        return true;
    }
}
