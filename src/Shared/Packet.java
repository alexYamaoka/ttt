package Shared;

import java.io.Serializable;

public class Packet implements Serializable {
    private String request;
    private UserInformation information;
    Serializable data;

    public Packet(String request, UserInformation information, Serializable data) {
        this.request = request;
        this.information = information;
        this.data = data;
    }

    public String getRequest() {
        return request;
    }

    public Serializable getData() {
        return data;
    }

    public UserInformation getInformation() { return information; }
}
