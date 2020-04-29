package Models;

import Shared.UserInformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest
{
    UserInformation player1 = new UserInformation("player1_firstName", "player1_lastName", "player1_username", "player1_email", "player1_password");
    UserInformation player2 = new UserInformation("player2_firstName", "player2_lastName", "player2_username", "player2_email", "player2_password");



    @Test
    void getPlayer1_shouldReturnUserInformation()
    {
        Game game = new Game(player1, player2);

        System.out.println("player1: " + player1);

        assertEquals(player1, game.getPlayer1());
    }

    @Test
    void getPlayer2_shouldReturnUserInformation()
    {
        Game game = new Game(player1, player2);

        System.out.println("player1: " + player1);

        assertEquals(player2, game.getPlayer2());
    }



    @Test
    void player1MakeMove_shouldReturnX()
    {
        Game game = new Game(player1, player2);

        Move move = new Move(1,1, player1);
        game.player1MakeMove(move);

        assertEquals('X', game.getCharInTile(1,1));
    }

    @Test
    void player2MakeMove_shouldReturnO()
    {
        Game game = new Game(player1, player2);

        Move move = new Move(1,1,player2);
        game.player2MakeMove(move);

        assertEquals('O', game.getCharInTile(1,1));
    }

    @Test
    void checkIfValidMove_shouldReturnTrue()
    {
        Game game = new Game(player1, player2);

        Move move1 = new Move(1,1,player1);
        Move move2 = new Move (2,1, player2);
        game.player1MakeMove(move1);

        assertEquals(true, game.checkIfValidMove(move2));
    }

    @Test
    void checkIfValidMove_shouldReturnFalse()
    {
        Game game = new Game(player1, player2);

        Move move1 = new Move(1,1,player1);
        Move move2 = new Move (1,1, player2);
        game.player1MakeMove(move1);

        assertEquals(false, game.checkIfValidMove(move2));
    }

    @Test
    void addGameObserver()
    {
    }

    @Test
    void notifyObservers()
    {
    }



    @Test
    void isPlayer1Winner()
    {

    }

    @Test
    void isPlayer2Winner()
    {
    }

    @Test
    void isTieGame()
    {

    }
}