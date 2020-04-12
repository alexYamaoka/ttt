package Models;

import java.util.UUID;

public class BaseModel {
    private String id;

    public BaseModel() {
        id = UUID.randomUUID().toString();
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
