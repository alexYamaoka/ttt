package DataBase.sql;

import Models.BaseModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DataSource {

    BaseModel insert (BaseModel obj);
    BaseModel delete (BaseModel obj);
    BaseModel update (BaseModel obj);
    BaseModel get (String id);

    List<BaseModel> list(Class obj);
    List<BaseModel> query(Class obj,String filter) throws SQLException;

}
