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

    public static final String JOIN_GAME = "JOIN_GAME";
    public static final String NEW_GAME_CREATED = "NEW-GAME-CREATED";
    public static final String MAKE_NEXT_MOVE = "MAKE-NEXT-MOVE";
    public static final String OBSERVE_GAME = "OBSERVE-GAME";
    public static final String GET_GAMES = "GET_GAMES"; // Joinable games
    public static final String GAME_MOVE = "GAME-MOVE";


    public Packet(String request, UserInformation information, Serializable data) {
        this.request = request;
        this.information = information;
        this.data = data;
    }

    public Packet() {

    }

    public String getRequest() {
        return request;
    }

    public Serializable getData() {
        return data;
    }

    public UserInformation getInformation() { return information; }
}
