package ObserverPatterns;

import Models.Game;

import java.util.HashSet;
import java.util.List;

public interface HistoryListener {
    void updateHistory(List<Game> list);
}
