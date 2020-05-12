package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TTTBoardTest
{

    @Test
    void setX_should_return_X()
    {
        TTTBoard tttBoard = new TTTBoard();

        tttBoard.setX(1,1);

        assertEquals('X', tttBoard.getCharInCell(1,1));
    }

    @Test
    void setO_should_return_O()
    {
        TTTBoard tttBoard = new TTTBoard();

        tttBoard.setO(2,2);

        assertEquals('O', tttBoard.getCharInCell(2,2));
    }


    @Test
    void isCellEmpty_should_return_UnderscoreCharacter()
    {
        TTTBoard tttBoard = new TTTBoard();
        assertEquals('_', tttBoard.getCharInCell(1,1));
    }


    @Test
    void isWinner_main_diagonal_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(1,1);
        tttBoard.setX(2,2);

        System.out.println("main diagonal:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(2,2,'X'));
    }

    @Test
    void isWinner_secondary_diagonal_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,2);
        tttBoard.setX(1,1);
        tttBoard.setX(2,0);

        System.out.println("secondary diagonal:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(2,0,'X'));
    }

    @Test
    void isWinner_horizontal_top_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(0,1);
        tttBoard.setX(0,2);

        System.out.println("horizontal top:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(0,2,'X'));
    }

    @Test
    void isWinner_horizontal_middle_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(1,0);
        tttBoard.setX(1,1);
        tttBoard.setX(1,2);

        System.out.println("horizontal middle:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(1,2,'X'));
    }

    @Test
    void isWinner_horizontal_bottom_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(2,0);
        tttBoard.setX(2,1);
        tttBoard.setX(2,2);

        System.out.println("horizontal bottom:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(2,2,'X'));
    }

    @Test
    void isWinner_vertical_left_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,0);
        tttBoard.setX(1,0);
        tttBoard.setX(2,0);

        System.out.println("vertical left:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(2,0,'X'));
    }

    @Test
    void isWinner_vertical_middle_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,1);
        tttBoard.setX(1,1);
        tttBoard.setX(2,1);

        System.out.println("vertical middle:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(2,1,'X'));
    }

    @Test
    void isWinner_vertical_right_should_return_true()
    {
        TTTBoard tttBoard = new TTTBoard();
        tttBoard.setX(0,2);
        tttBoard.setX(1,2);
        tttBoard.setX(2,2);

        System.out.println("vertical right:");
        tttBoard.printBoard();

        assertTrue(tttBoard.isWinner(2,2,'X'));
    }



    @Test
    void isTieGame_should_return_true()
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

        assertTrue(tttBoard.isTieGame());
    }
}