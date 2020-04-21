package DataBase.sql;

import Models.BaseModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DataSource {

    BaseModel insert (BaseModel obj) throws SQLException;
    BaseModel delete (BaseModel obj) throws SQLException;
    BaseModel update (BaseModel obj) throws SQLException;
    BaseModel get (String id) throws SQLException;

    List<BaseModel> list(Class obj);
    List<BaseModel> query(Class obj,String filter) throws SQLException;

}
