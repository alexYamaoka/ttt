package GameService;

import DataBase.UUIDGenerator;
import Server.ClientConnection;

import java.util.ArrayList;

public class GameRoomInformation {

    ClientConnection player1;
    ClientConnection player2;
    private String Id;
    private String gameName;

    private ArrayList<ClientConnection> GameObservers = new ArrayList<>();

    public GameRoomInformation (ClientConnection player1,String gameName) {

        UUIDGenerator gameId = new UUIDGenerator();
        this.Id = gameId.getNewId();
        this.gameName = gameName;
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


}
