package DataBase.sql;

import Models.BaseModel;
import Models.Game;
import Models.Move;
import Shared.GameInformation;
import Shared.UserInformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DataSource {
    boolean addGameViewers(BaseModel gameObj,BaseModel userObj) throws SQLException;

    boolean insertGame(BaseModel obj) throws SQLException;

    boolean insertMove(Move obj, String gameId) throws SQLException;

    boolean insert(BaseModel obj) throws SQLException;

    boolean delete(String Id) throws SQLException;
    boolean Activate(String Id) throws SQLException;
    List<GameInformation> getPlayerGamesInfo(String PlayerId,String username) throws SQLException;

    Boolean update(String UpdateFirstName,String UpdateLastName,String UpdateUserName,String Id,String UpdatePassword) throws SQLException;

    ArrayList<UserInformation> get(String id) throws SQLException;

    List<BaseModel> list(Class obj);

    List<BaseModel> query(Class obj, String filter) throws SQLException;

}
