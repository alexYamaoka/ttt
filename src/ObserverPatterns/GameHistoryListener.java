package ObserverPatterns;

import Shared.GameInformation;

public interface GameHistoryListener {
    void updateGameHistory(GameInformation gameInformation);
}
