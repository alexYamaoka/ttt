package ObserverPatterns;

import Models.Game;
import Shared.UserInformation;

import java.util.HashSet;

public interface LobbyListener {
    void newGame(String message);

    void updateUIWithNewGame(Game game);
    void getListOfGames(HashSet<Game> listOfGames);
    void getListOfOnlinePlayers(HashSet<UserInformation> listOfOnlinePlayers);
}
