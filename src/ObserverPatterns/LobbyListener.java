package ObserverPatterns;

import Models.Game;
import Shared.UserInformation;

import java.util.HashSet;

public interface LobbyListener extends GameListener {
    void newGame(Game game);

    void getListOfGames(HashSet<Game> listOfGames);

    void getListOfOnlinePlayers(HashSet<UserInformation> listOfOnlinePlayers);

    void joinGame(Game game);

    void clearGameList();

    void spectateGame(Game game);
}
