package DataBase.sql;
import DataBase.UUIDGenerator;
import Models.BaseModel;
import Models.Game;
import Shared.UserInformation;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DatabaseManager implements DataSource {  // subscribing to sign in for sign in info

    private static DatabaseManager instance = null;
    public Connection myConn;
    private Statement myState;
    private PreparedStatement UserStatement;
    private PreparedStatement GameStatement;

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
                    System.out.println("DatabaseManager: Connection made");
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
    public boolean delete(String username, String firstname, String lastname,String password) throws SQLException {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE user SET");
            query.append("isDeleted = 1");
            query.append("WHERE ");
            query.append("username = '" + username + "' AND password = '" + password + "'");
            UserStatement = myConn.prepareStatement(query.toString());
            UserStatement.executeUpdate(query.toString());
            System.out.println(query.toString());

        return true;
    }

    @Override
    public BaseModel update(BaseModel obj) throws SQLException {
        StringBuilder query = new StringBuilder();
        List<BaseModel> items = new ArrayList<>();
        if (obj instanceof UserInformation) {
            UserInformation user = (UserInformation) obj;
            query.append("UPDATE ");
            query.append("user SET username = ? WHERE id = ? ");
            UserStatement = myConn.prepareStatement(query.toString());
            System.out.println(query.toString());
            UserStatement.setString(1, user.getUserName());
            UserStatement.executeQuery(query.toString());
            items.add(user);
        }
        return (BaseModel) items;
    }

    @Override
    public ArrayList<UserInformation> get(String password) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM user WHERE password = ").append(password);
        System.out.println("Print out "+query.toString());

        UserStatement = myConn.prepareStatement(query.toString());
        ResultSet rs;
        rs = UserStatement.executeQuery(query.toString());
        ArrayList<UserInformation> items = new ArrayList<>();
        while(rs.next()) {
            UserInformation u = new UserInformation();
            u.setId(rs.getString(1));
            u.setUserName(rs.getString(2));
            u.setFirstName(rs.getString(3));
            u.setLastName(rs.getString(4));
            items.add(u);
        }
        return items;
    }

    @Override
    public List<BaseModel> list(Class obj) {
        return null;
    }


    @Override
    public boolean insert(BaseModel obj) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        int row = 0;
        if(obj instanceof Game){
            Game game = (Game) obj;
            UUIDGenerator newID = new UUIDGenerator();
            query.append("game");
            query.append("(gameID, StartTime, EndTime, Player1Id, Player2Id, StartingPlayerId, WinningPlayerId)");
            query.append("values (?,?,?,?,?,?,?)");
            GameStatement.setString(1,newID.getNewId());
            //GameStatement.setDate(2,game.getStartTime());
            //GameStatement.setDate(3,game.getEndTime());
            //GameStatement.setInt(4,game.getPlayer1());
            //GameStatement.setInt(4,game.getPlayer2().;


        }
        if(obj instanceof UserInformation){
            UserInformation userObj = (UserInformation) obj;
            UUIDGenerator newID = new UUIDGenerator();
            System.out.println("Name "+userObj.getUserName());
            query.append("user ");
            query.append("(id, username, password, FirstName, LastName, isDeleted)");
            query.append("values (?,?,?,?,?,?)");

            UserStatement = myConn.prepareStatement(query.toString());
            System.out.println(query.toString());
            UserStatement.setString(1,newID.getNewId());
            UserStatement.setString(2,userObj.getUserName());
            UserStatement.setString(3,userObj.getPassword());
            UserStatement.setString(4,userObj.getFirstName());
            UserStatement.setString(5,userObj.getLastName());
            UserStatement.setInt(6,userObj.getIsDeleted());
            System.out.println(UserStatement.toString());
            System.out.println(query.toString());
            row = UserStatement.executeUpdate();
            System.out.println("Row = " + row);
        }

        if(row == 0){
            return false;
        }else
        return true;
    }

    @Override
    public List<BaseModel> query(Class obj, String filter) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        System.out.println("Class obj = "+obj.toString());
        if(obj.getCanonicalName().equalsIgnoreCase("Shared.UserInformation")){
            query.append("user ");
        }else if(obj.getCanonicalName().equalsIgnoreCase("Models.Game")){
            query.append("games");
        }if(!filter.trim().equals("")){
            query.append("WHERE " + filter);
        }
        System.out.println(query.toString());
        PreparedStatement ps;
        ps = myConn.prepareStatement(query.toString());
        ResultSet rs = ps.executeQuery(query.toString());
        System.out.println(" RS = " + rs.toString());

        List<BaseModel> items = new ArrayList<>();
        while(rs.next()){
            if(obj.getCanonicalName().equalsIgnoreCase("Shared.UserInformation")){
                UserInformation u = new UserInformation();
                u.setId(rs.getString(1));
                u.setUserName(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setFirstName(rs.getString(4));
                u.setLastName(rs.getString(5));
                u.setIsDeleted(rs.getInt(6));
                items.add(u);
            }
        }
        return items;
    }
}
