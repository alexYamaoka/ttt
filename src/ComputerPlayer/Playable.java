package ComputerPlayer;

import Models.Game;

public interface Playable {
    MinimaxAi.Move play(Game game);
}
