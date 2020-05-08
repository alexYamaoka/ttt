package DataBase.sql;

import Models.BaseModel;
import Models.Game;
import Models.Move;
import Shared.UserInformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DataSource {
    boolean addGameViewers(BaseModel obj) throws SQLException;

    boolean insertGame(BaseModel obj) throws SQLException;

    boolean insertMove(Move obj, String gameId) throws SQLException;

    boolean insert(BaseModel obj) throws SQLException;

    boolean delete(String username, String firstname, String lastname, String password) throws SQLException;

    BaseModel update(BaseModel obj) throws SQLException;

    ArrayList<UserInformation> get(String id) throws SQLException;

    List<BaseModel> list(Class obj);

    List<BaseModel> query(Class obj, String filter) throws SQLException;

}
