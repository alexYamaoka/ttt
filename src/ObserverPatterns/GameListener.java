package ObserverPatterns;

import Models.Move;

public interface GameListener {
    void updateMove(String message);

    void updateStatus(String message);

    void setGameName(String gameName);
}
