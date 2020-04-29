package Models;

import ObserverPatterns.GameObserver;
import Shared.Packet;
import Shared.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class Game extends BaseModel {
    // game logic
    // list of observers
    // player 1
    // player 2

    private TTTBoard tttBoard;
    private UserInformation player1;
    private UserInformation player2;
    private List<GameObserver> gameObserversList;

    public Game(UserInformation player1, UserInformation player2)
    {
        TTTBoard tttBoard = new TTTBoard();
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

    public void makeMove(Move move)
    {
        tttBoard.setX(move.getRow(), move.getColumn());
    }

    public boolean checkIfValidMove(Move move)
    {
        return tttBoard.isMoveValid(move.getRow(), move.getColumn());
    }

}
