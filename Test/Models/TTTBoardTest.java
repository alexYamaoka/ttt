package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TTTBoardTest
{

    @Test
    void printBoard()
    {
        TTTBoard tttBoard = new TTTBoard();

        tttBoard.printBoard();
    }

    @Test
    void setX()
    {
        TTTBoard tttBoard = new TTTBoard();

        tttBoard.setX(1,1);
        tttBoard.printBoard();

        assertEquals('X', tttBoard.getCharInCell(1,1));
    }

    @Test
    void setO()
    {
        TTTBoard tttBoard = new TTTBoard();

        tttBoard.setO(2,2);
        tttBoard.printBoard();

        assertEquals('O', tttBoard.getCharInCell(2,2));

    }


    @Test
    void isCellEmpty()
    {
        TTTBoard tttBoard = new TTTBoard();
        assertEquals('_', tttBoard.getCharInCell(1,1));
    }


    @Test
    void isWinner()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(1,1);
        tttBoard.setX(2,2);

        assertEquals(true, tttBoard.isWinner(2,2,'X'));
    }

    @Test
    void isTieGame()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(0,2);
        tttBoard.setX(1,0);
        tttBoard.setX(1,2);
        tttBoard.setX(2,1);
        tttBoard.setO(0,1);
        tttBoard.setO(1,1);
        tttBoard.setO(2,0);
        tttBoard.setO(2,2);
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isTieGame());
    }
}