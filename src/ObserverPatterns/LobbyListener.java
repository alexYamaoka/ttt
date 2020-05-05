package ObserverPatterns;

import Models.Game;
import Models.Move;
import Shared.UserInformation;

import java.util.HashSet;

public interface LobbyListener extends GameListener {
    void newGame(String message);
    void getListOfGames(HashSet<Game> listOfGames);
    void getListOfOnlinePlayers(HashSet<UserInformation> listOfOnlinePlayers);
    void joinGame(Game game);
}
