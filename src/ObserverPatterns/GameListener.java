package ObserverPatterns;

import Models.Move;

public interface GameListener {
    void updateMove(Move move);

    void updateStatus(String message);

    void setGameName(String gameName);

    void setPlayer1Username(String player1Username);
    void setPlayer2Username(String player2Username);
}
