package Shared;

import java.io.Serializable;

public class Packet implements Serializable {
    private String type;
    private UserInformation information;
    Serializable data;

    public Packet(String type, UserInformation information, Serializable data) {
        this.type = type;
        this.information = information;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public Serializable getData() {
        return data;
    }

    public UserInformation getInformation() { return information; }
}
