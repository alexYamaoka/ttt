package ObserverPatterns;

import Models.Game;
import Shared.GameInformation;

import java.util.List;

public interface GameHistoryListener {
    void updateHistory(List<GameInformation> list);
}
