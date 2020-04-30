package Models;

import Shared.UserInformation;

public class TTTBoard
{
    private final char X = 'X';
    private final char O = 'O';
    private final char emptyCell = '_';
    private final int numOfRows = 3;
    private final int numOfColumns = 3;
    private int numOfMovesLeft;
    private char[][] board;


    public TTTBoard()
    {
        board = new char[numOfRows][numOfColumns];
        initializeBoard();
        numOfMovesLeft = 9;
    }

    private void initializeBoard()
    {
        for (int i = 0; i < numOfRows; i++)
        {
            for (int j = 0; j < numOfColumns; j++)
            {
                board[i][j] = emptyCell;
            }
        }
    }

    public void printBoard()
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void setX(int row, int col)
    {
        board[row][col] = X;
        numOfMovesLeft--;
    }

    public void setO(int row, int col)
    {
        board[row][col] = O;
        numOfMovesLeft--;
    }

    public char getCharInCell(int row, int col)
    {
        return board[row][col];
    }

    public boolean isCellEmpty(int row, int col)
    {
        return board[row][col] == emptyCell;
    }

    // function to check if there is a winner
    public boolean isWinner(int x, int y, char symbol)
    {
        int countRow = 0;
        int countCol = 0;
        int countLDiag = 0;
        int countRDiag = 0;

        for(int i = 0; i < board.length; i++)
        {
            if(board[x][i] == symbol)
                countRow++;

            if(board[i][y] == symbol)
                countCol++;

            if(board[i][i] == symbol)
                countRDiag++;

            if(board[board.length-1-i][i] == symbol)
                countLDiag++;
        }

        if(countCol == board.length || countRow == board.length || countLDiag == board.length || countRDiag == board.length)
            return true;

        return false;
    }



    public boolean isTieGame()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == emptyCell)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isOver(){
        if(numOfMovesLeft == 0)
            return true;
        else return false;

    }

}
