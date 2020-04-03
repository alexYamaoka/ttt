package sample;

import java.sql.*;

public class DatabaseManager {

    private static DatabaseManager instance = null;
    public Connection myConn;
    private Statement myState;


    private DatabaseManager(){
        String url = "jdbc:mysql://localhost:3306/tictactoe?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST";
        String username = "test";
        String password = "test123password";
        try{
            myConn = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance(){
        if(instance == null){
            synchronized (DatabaseManager.class){
                if(instance == null){
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public void disconnect(){
        if(myConn != null){
            try{
                myConn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /*

    public boolean addUser(User user){
        return true;
    }

    public boolean updateUser(User user){
        return true;
    }

    public boolean deleteUser(String username){
        return true;
    }

    public boolean deleteUser(int userID){
        return true;
    }

    public List<User> getAllUsers(){
    }


    public List<User> getUsers(String filter){
        List<User> users = new ArrayList<>;
    }

     */
}
