package ObserverPatterns;

import Models.Move;

public interface GameListener {
    void updateMove(Move move);

    void updateStatus(String message);
}
