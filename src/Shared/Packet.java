package Shared;

import java.io.Serializable;

public class Packet implements Serializable {
    private String request;
    private UserInformation information;
    Serializable data;

    public static final String CONNECT = "CONNECT";
    public static final String REGISTER_CLIENT = "REGISTER";
    public static final String SIGN_IN = "SIGN-IN";
    public static final String SIGN_OUT = "SIGN-OUT";
    public static final String UPDATE_USER = "UPDATE-USER";
    public static final String DELETE_ACCOUNT = "DELETE-ACCOUNT";





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
