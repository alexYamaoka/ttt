package DataBase.sql;
import Models.BaseModel;
import Shared.UserInformation;

import javax.swing.plaf.synth.SynthMenuBarUI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements DataSource {  // subscribing to sign in for sign in info

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

    @Override
    public BaseModel delete(BaseModel obj) {
        return null;
    }

    @Override
    public BaseModel update(BaseModel obj) {
        return null;
    }

    @Override
    public BaseModel get(String id) {
        return null;
    }

    @Override
    public List<BaseModel> list(Class obj) {
        return null;
    }

    @Override
    public BaseModel insert(BaseModel obj) {
        StringBuilder query = new StringBuilder();

        return null;
    }

    @Override
    public List<BaseModel> query(Class obj, String filter) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");

        if(obj.getCanonicalName().equalsIgnoreCase("UserInformation")){
            query.append("user");
        }else if(obj.getCanonicalName().equalsIgnoreCase("Game")){
            query.append("GAMES");
        }
        System.out.println(query.toString());
        if(!filter.trim().equals("")){
            query.append(" WHERE " + filter);
        }
        System.out.println(query.toString());
        PreparedStatement ps;
        //ResultSet rs = (ResultSet) myConn.prepareStatement(query.toString());
        ResultSet rs = myState.executeQuery(query.toString());

        System.out.println( "RS = "+ rs.toString());
        List<BaseModel> items = new ArrayList<>();

        while(rs.next()){
            if(obj.getCanonicalName().equalsIgnoreCase("UserInformation")){
                UserInformation u = new UserInformation();
                u.setId(rs.getString(1));
                u.setUserName(rs.getString(2));
                u.setFirstName(rs.getString(3));
                u.setLastName(rs.getString(4));
                items.add(u);
            }
        }
        return null;
    }

    /*
    public boolean addUser(User user) throws SQLException {
        String DataBase.sql = "INSERT INTO user ("
                + "id,"
                +"username,"
                +"password ,"
                +"FirstName,"
                +"LastName ) VALUES ("
                +"null, ?, ?, ?, ?)"; //null is userID its autoincrement
        PreparedStatement ps =  myConn.prepareStatement(DataBase.sql,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,user.getUserName());
        ps.setString(2, user.getPassword());
        ps.setString(3,user.getFirstName());
        ps.setString(4,user.getLastName());
        ps.executeUpdate();
        System.out.println("Added user " + user.getUserName());
        ResultSet keys = ps.getGeneratedKeys();
        if(keys.next()){
            //user.setId(keys.getInt(1));
        }
        ps.close();
        return true;
    }


    public boolean getUser(String password,String UserName) throws SQLException { //for sign in
        ResultSet rs;
        PreparedStatement ps;
        ps = myConn.prepareStatement("SELECT FROM user WHERE password = ? , id,username,password,FirstName,LastName");
        ps.setString(1,password);
        //ps.setString(1,UserName);
        /*rs = ps.executeQuery();
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

        //String DataBase.sql = " UPDATE user SET username = ?, password = ? WHERE id = ?"

        return true;
    }



    public List<User> getAllUsers(){

        return User;
    }


    public List<User> getUsers(String filter){

        List<User> users = new ArrayList<>();
    }



    */

}
