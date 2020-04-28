package Models;

import ObserverPatterns.GameObserver;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game extends BaseModel {
    // game logic
    // list of observers
    // player 1
    // player 2

    private UserInformation player1;
    private UserInformation player2;
    private List<GameObserver> gameObserversList;

    public Game(UserInformation player1, UserInformation player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        gameObserversList = new ArrayList<>();
    }


    public UserInformation getPlayer1()
    {
        return player1;
    }

    public UserInformation getPlayer2()
    {
        return player2;
    }

    public void addGameObserver(GameObserver observer)
    {
        gameObserversList.add(observer);
    }

    public void notifyObservers(Packet packet)
    {
        for (GameObserver observer: gameObserversList)
        {
            // observer.update(packet);     // broadcast packet with move or game status ex: tie game, winner
        }
    }


}
