package DataBase;

import Client.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance = null;
    public Connection myConn;
    private Statement myState;
    public ArrayList<User> user = new ArrayList<>();

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
                    System.out.println("Connection made");
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

    public boolean addUser(User user) throws SQLException {

        String sql = "INSERT INTO user ("
                + "id,"
                +"username,"
                +"password ,"
                +"FirstName,"
                +"LastName ) VALUES ("
                +"null, ?, ?, ?, ?)"; //null is userID its autoincrement
        PreparedStatement ps =  myConn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,user.getUserName());
        ps.setString(2, user.getPassword());
        ps.setString(3,user.getFirstName());
        ps.setString(4,user.getLastName());
        ps.executeUpdate();

        System.out.println("Added user " + user.getUserName());

        ResultSet keys = ps.getGeneratedKeys();
        if(keys.next()){
            user.setId(keys.getInt(1));
        }
        ps.close();
        return true;
    }


    public boolean getUser(String password) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        ps = myConn.prepareStatement("SELECT id,username,password,FirstName,LastName Where password = ? FROM user");
        ps.setString(1,password);
        rs = ps.executeQuery();

        while(rs.next()){
            int id = rs.getInt(1);
            String username = rs.getString(2);
            String Password = rs.getString(3);
            String FirstName = rs.getString(4);
            String LastName = rs.getString(5);
            System.out.println(username + " " + Password + " " + FirstName + " " + LastName);
        }

        System.out.println("User retrieved");

        return true;
    }

    public boolean deleteUser(String username) throws SQLException {
        PreparedStatement ps = myConn.prepareStatement("Delete FROM user Where username = ?");
        ps.setString(1,username);
        ps.executeUpdate();
        System.out.println("Removed User : " + username);
        return true;
    }

    public boolean deleteUser(int userID) throws SQLException {
        PreparedStatement ps = myConn.prepareStatement("Delete FROM user Where id = ?");
        ps.setInt(1,userID);
        ps.executeUpdate();
        System.out.println("Removed User with id : " + userID);
        return true;
    }

    public boolean updateUser(User user){

        //String sql = " UPDATE user SET username = ?, password = ? WHERE id = ?"

        return true;
    }

    /*
    public List<User> getAllUsers(){

        return User;
    }


    public List<User> getUsers(String filter){

        List<User> users = new ArrayList<>();
    }

     */


}
