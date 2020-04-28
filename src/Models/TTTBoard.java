package Models;

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
    }

    public void setO(int row, int col)
    {
        board[row][col] = O;
    }

    public boolean isMoveValid(int row, int col)
    {
        return board[row][col] == emptyCell;
    }

}
