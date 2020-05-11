package DataBase.sql;

import DataBase.UUIDGenerator;
import Models.Game;
import Models.BaseModel;
import Models.Move;
import Server.ClientConnection;
import Shared.UserInformation;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.*;
import java.util.*;

public class DatabaseManager implements DataSource {  // subscribing to sign in for sign in info

    private static DatabaseManager instance = null;
    public Connection myConn;
    private Statement myState;
    private PreparedStatement UserStatement;
    private PreparedStatement GameStatement;
    private PreparedStatement MoveStatement;

    private DatabaseManager() {
        String url = "jdbc:mysql://localhost:3306/tictactoe?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST";
        String username = "test";
        String password = "test123password";
        try {
            myConn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                    System.out.println("DatabaseManager: Connection made");
                }
            }
        }
        return instance;
    }

    public void disconnect() {
        if (myConn != null) {
            try {
                myConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        StringBuilder query = new StringBuilder();
        String sql = "UPDATE user SET isDeleted = ? WHERE id = ?";
        int row = 0;

        System.out.println(sql);
        UserStatement = myConn.prepareStatement(sql);
        UserStatement.setInt(1,1);
        UserStatement.setString(2,Id);
        row = UserStatement.executeUpdate();
        System.out.println(query.toString());
        System.out.println(String.format("Rows affected %d",row));
        return row > 0;
    }

    @Override
    public Boolean update(String UpdateFirstName,String UpdateLastName,String UpdateUserName,String Id,String UpdatePassword) throws SQLException {
        String sql = "UPDATE user " + "SET FirstName = ? , LastName = ? , username = ? , password = ? WHERE id = ?";
        int row = 0;

        UserStatement = myConn.prepareStatement(sql);
        System.out.println(sql.toString());

        System.out.println("\n");
        System.out.println("FirstName "+UpdateFirstName);
        System.out.println("Last " + UpdateLastName);
        System.out.println("UserName " + UpdateUserName);
        System.out.println("Pass " + UpdatePassword);
        System.out.println("ID " + Id);
        System.out.println("\n");

        UserStatement.setString(1,UpdateFirstName);
        UserStatement.setString(2,UpdateLastName);
        UserStatement.setString(3,UpdateUserName);
        UserStatement.setString(4,UpdatePassword);
        UserStatement.setString(5,Id);

        row = UserStatement.executeUpdate();
        System.out.println(String.format("Rows affected %d",row));

        return row > 0;
    }

    @Override
    public ArrayList<UserInformation> get(String password) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM user WHERE password = ").append(password);
        System.out.println("Print out " + query.toString());

        UserStatement = myConn.prepareStatement(query.toString());
        ResultSet rs;
        rs = UserStatement.executeQuery(query.toString());
        ArrayList<UserInformation> items = new ArrayList<>();
        while (rs.next()) {
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
    public boolean addGameViewers(BaseModel gameObj,BaseModel userObj) throws SQLException {
        StringBuilder query = new StringBuilder();
        Game game = (Game) gameObj;
        UserInformation user = (UserInformation) userObj;

        query.append("INSERT INTO gameViewers ");
        int row = 0;
        query.append("(GameId, UserId)");
        query.append(" values (?,?)");
        GameStatement = myConn.prepareStatement(query.toString());
        System.out.println(query.toString());
        GameStatement.setString(1, game.getId());
        GameStatement.setString(2, user.getId());
        System.out.println(GameStatement);
        row = GameStatement.executeUpdate();
        System.out.println("Row = " + row);

        return row != 0;
    }

    @Override
    public boolean insertGame(BaseModel obj) throws SQLException {
        StringBuilder query = new StringBuilder();
        Game game = (Game) obj;
        query.append("INSERT INTO game ");
        int row = 0;
        query.append("(gameID, StartTime, EndTime, Player1Id, Player2Id, StartingPlayerId, WinningPlayerId)");
        query.append(" values (?,?,?,?,?,?,?)");
        GameStatement = myConn.prepareStatement(query.toString());
        System.out.println(query.toString());

        GameStatement.setString(1, game.getId());
        GameStatement.setTimestamp(2, game.getStartTime());
        GameStatement.setTimestamp(3, game.getEndTime());
        GameStatement.setString(4, game.getPlayer1Info().getId());
        GameStatement.setString(5, game.getPlayer2Info().getId());
        GameStatement.setString(6, game.getWinningPlayerId());
        GameStatement.setString(7, game.getWinningPlayerId());

        System.out.println(GameStatement);
        row = GameStatement.executeUpdate();
        System.out.println("Row = " + row);

        return row != 0;
    }

    @Override
    public boolean insertMove(Move obj, String gameId) throws SQLException {
        StringBuilder query = new StringBuilder();
        Move move = obj;
        query.append("INSERT INTO moves");
        int row = 0;

        System.out.println("Id:  " + move.getGameId() + move.getId());
        query.append("(GameId, PlayerId, X_coord, Y_coord, Time)");
        query.append(" values (?,?,?,?,?)");

        MoveStatement = myConn.prepareStatement(query.toString());
        System.out.println(query.toString());
        MoveStatement.setString(1, gameId);
        MoveStatement.setString(2, move.getUserInformation().getId());
        MoveStatement.setInt(3, move.getColumn());
        MoveStatement.setInt(4, move.getRow());
        MoveStatement.setTimestamp(5, move.getMoveTime());

        System.out.println(MoveStatement.toString());
        row = MoveStatement.executeUpdate();
        System.out.println("Row = " + row);

        return row != 0;
    }

    @Override
    public boolean insert(BaseModel obj) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        int row = 0;

        if (obj instanceof UserInformation) {
            UserInformation userObj = (UserInformation) obj;
            UUIDGenerator newID = new UUIDGenerator();
            System.out.println("Name " + userObj.getUserName());
            query.append("user ");
            query.append("(id, username, password, FirstName, LastName, isDeleted)");
            query.append(" values (?,?,?,?,?,?)");
            UserStatement = myConn.prepareStatement(query.toString());
            System.out.println(query.toString());
            UserStatement.setString(1, newID.getNewId());
            UserStatement.setString(2, userObj.getUserName());
            UserStatement.setString(3, userObj.getPassword());
            UserStatement.setString(4, userObj.getFirstName());
            UserStatement.setString(5, userObj.getLastName());
            UserStatement.setInt(6, userObj.getIsDeleted());
            System.out.println(UserStatement.toString());
            System.out.println(query.toString());
            row = UserStatement.executeUpdate();
            System.out.println("Row = " + row);
        }


        return row != 0;
    }

    @Override
    public List<BaseModel> query(Class obj, String filter) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        System.out.println("Class obj = " + obj.toString());
        if (obj.getCanonicalName().equalsIgnoreCase("Shared.UserInformation")) {
            query.append("user ");
        } else if (obj.getCanonicalName().equalsIgnoreCase("Models.Game")) {
            query.append("games");
        }
        if (!filter.trim().equals("")) {
            query.append("WHERE " + filter);
        }
        System.out.println(query.toString());
        PreparedStatement ps;
        ps = myConn.prepareStatement(query.toString());
        ResultSet rs = ps.executeQuery(query.toString());
        System.out.println(" RS = " + rs.toString());

        List<BaseModel> items = new ArrayList<>();
        while (rs.next()) {
            if (obj.getCanonicalName().equalsIgnoreCase("Shared.UserInformation")) {
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