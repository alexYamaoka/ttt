package Models;

import java.io.Serializable;

public class BaseModel implements Serializable {

    private String id;
    private int isDeleted;  // in DataBase default val is 0

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
