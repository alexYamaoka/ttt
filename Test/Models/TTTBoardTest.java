package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TTTBoardTest
{

    @Test
    void setX_shouldReturn_X()
    {
        TTTBoard tttBoard = new TTTBoard();

        tttBoard.setX(1,1);

        assertEquals('X', tttBoard.getCharInCell(1,1));
    }

    @Test
    void setO_shouldReturn_O()
    {
        TTTBoard tttBoard = new TTTBoard();

        tttBoard.setO(2,2);

        assertEquals('O', tttBoard.getCharInCell(2,2));
    }


    @Test
    void isCellEmpty_shouldReturn_UnderscoreCharacter()
    {
        TTTBoard tttBoard = new TTTBoard();
        assertEquals('_', tttBoard.getCharInCell(1,1));
    }


    @Test
    void isWinner_mainDiagonal_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(1,1);
        tttBoard.setX(2,2);

        System.out.println("main diagonal:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(2,2,'X'));
    }

    @Test
    void isWinner_secondaryDiagonal_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,2);
        tttBoard.setX(1,1);
        tttBoard.setX(2,0);

        System.out.println("secondary diagonal:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(2,0,'X'));
    }

    @Test
    void isWinner_horizontalTop_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(0,1);
        tttBoard.setX(0,2);

        System.out.println("horizontal top:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(0,2,'X'));
    }

    @Test
    void isWinner_horizontalMiddle_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(1,0);
        tttBoard.setX(1,1);
        tttBoard.setX(1,2);

        System.out.println("horizontal middle:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(1,2,'X'));
    }

    @Test
    void isWinner_horizontalBottom_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(2,0);
        tttBoard.setX(2,1);
        tttBoard.setX(2,2);

        System.out.println("horizontal bottom:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(2,2,'X'));
    }

    @Test
    void isWinner_verticalLeft_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(1,0);
        tttBoard.setX(2,0);

        System.out.println("vertical left:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(2,0,'X'));
    }

    @Test
    void isWinner_verticalMiddle_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,1);
        tttBoard.setX(1,1);
        tttBoard.setX(2,1);

        System.out.println("vertical middle:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(2,1,'X'));
    }

    @Test
    void isWinner_verticalRight_shouldReturn_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,2);
        tttBoard.setX(1,2);
        tttBoard.setX(2,2);

        System.out.println("vertical right:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isWinner(2,2,'X'));
    }



    @Test
    void isTieGame_shouldReturn_true()
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

        System.out.println("Tie Game:");
        tttBoard.printBoard();

        assertEquals(true, tttBoard.isTieGame());
    }
}