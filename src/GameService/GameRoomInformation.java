package GameService;

import DataBase.UUIDGenerator;
import Server.ClientConnection;
import Shared.UserInformation;

import java.sql.Timestamp;
import java.util.ArrayList;

public class GameRoomInformation {

    ClientConnection player1;
    ClientConnection player2;
    UserInformation player1ID;
    UserInformation player2ID;
    private String Id;
    private String gameName;
    private Timestamp startTime;
    private Timestamp endTime;

    private ArrayList<ClientConnection> GameObservers = new ArrayList<>();

    public GameRoomInformation (ClientConnection player1,String gameName) {
        UUIDGenerator gameId = new UUIDGenerator();
        this.Id = gameId.getNewId();
        this.gameName = gameName;
        this.player1 = player1;
    }

    public void start(){
        GameThread gameThread = new GameThread(player1,player2,GameObservers);
        gameThread.start();
    }
    public void join(ClientConnection player2){
        this.player2 = player2;
    }

    public String getId(){
        return this.Id;
    }

    public String getGameName(){
        return gameName;
    }

    public void addGameObserver(ClientConnection client){
        GameObservers.add(client);
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime() {
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        this.endTime = endTime;
    }
}
