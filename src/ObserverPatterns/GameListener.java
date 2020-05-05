package ObserverPatterns;

import Models.Move;

public interface GameListener {
    void updateStatus(String message);

    void setGameId(String gameId);

    void setPlayer1Username(String player1Username);

    void setPlayer2Username(String player2Username);

    void updateMove(Move move);
}
