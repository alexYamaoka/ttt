package Models;

import Shared.UserInformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest
{


    @Test
    void getPlayer1()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String username = "username";
        String email = "email";
        String password = "password";
        UserInformation player1 = new UserInformation(firstName, lastName, username, email, password);
        UserInformation player2 = new UserInformation(firstName, lastName, username, email, password);

        Game game = new Game(player1, player2);

        assertEquals(player1, game.getPlayer1());
    }

    @Test
    void getPlayer2()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String username = "username";
        String email = "email";
        String password = "password";
        UserInformation player1 = new UserInformation(firstName, lastName, username, email, password);
        UserInformation player2 = new UserInformation(firstName, lastName, username, email, password);

        Game game = new Game(player1, player2);

        assertEquals(player2, game.getPlayer2());
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
    void player1MakeMove()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String username = "username";
        String email = "email";
        String password = "password";
        UserInformation player1 = new UserInformation(firstName, lastName, username, email, password);
        UserInformation player2 = new UserInformation(firstName, lastName, username, email, password);

        Game game = new Game(player1, player2);

        Move move = new Move(1,1, player1);

        game.player1MakeMove(move);

        assertEquals('X', game.getCharInTile(1,1));
    }

    @Test
    void player2MakeMove()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String username = "username";
        String email = "email";
        String password = "password";
        UserInformation player1 = new UserInformation(firstName, lastName, username, email, password);
        UserInformation player2 = new UserInformation(firstName, lastName, username, email, password);

        Game game = new Game(player1, player2);
        Move move = new Move(1,1,player2);
        game.player2MakeMove(move);

        assertEquals('O', game.getCharInTile(1,1));
    }

    @Test
    void checkIfValidMove()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String username = "username";
        String email = "email";
        String password = "password";
        UserInformation player1 = new UserInformation(firstName, lastName, username, email, password);
        UserInformation player2 = new UserInformation(firstName, lastName, username, email, password);

        Game game = new Game(player1, player2);
        Move move1 = new Move(1,1,player1);
        Move move2 = new Move (1,1, player2);

        game.player1MakeMove(move1);

        assertEquals(false, game.checkIfValidMove(move2));
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