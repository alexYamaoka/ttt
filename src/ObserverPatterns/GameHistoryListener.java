package ObserverPatterns;

import Models.Game;
import Shared.GameInformation;

import java.util.List;

public interface GameHistoryListener {
    void updateGameHistory(GameInformation gameInformation);

    void updateHistory(List<Game> list);
}
