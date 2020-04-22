package DataBase.sql;

import Models.BaseModel;
import Shared.UserInformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DataSource {

    boolean insert (BaseModel obj) throws SQLException;
    BaseModel delete (BaseModel obj) throws SQLException;
    BaseModel update (BaseModel obj) throws SQLException;
    ArrayList<UserInformation> get (String id) throws SQLException;

    List<BaseModel> list(Class obj);
    List<BaseModel> query(Class obj,String filter) throws SQLException;

}
