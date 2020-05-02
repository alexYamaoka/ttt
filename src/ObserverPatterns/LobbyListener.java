package ObserverPatterns;

import java.util.HashSet;

public interface LobbyListener {
    void newGame(String message);

    void updateUIWithNewGame(String gameName);
    void getListOfGames(HashSet<String> listOfGames);
}
