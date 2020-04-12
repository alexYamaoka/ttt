package app;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Shared.UserInformation;

import java.sql.SQLException;

public class Server {

    private DataSource ds = DatabaseManager.getInstance();

    public void f1() {
        //ds.delete();
    }

    public void f2() {
        //ds.delete(User);
    }

    public boolean login(String username, String password) throws SQLException {
        ds.query(Shared.UserInformation.class, " username = ' " + username + " ' AND password = ' " + password + " ' ");
        return true;
    }

    public boolean registerUser(String username, String firstname, String lastname, String email, String password) {
        BaseModel user = new UserInformation(username, firstname, lastname, email, password);
        ds.insert(user);
        return true;
    }



}
