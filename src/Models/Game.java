package Models;

import DataBase.UUIDGenerator;
import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import ObserverPatterns.GameObserver;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Game extends BaseModel implements Serializable {

    private transient List<GameObserver> gameObserversList;
    private transient ArrayList<ClientConnection> GameObservers = new ArrayList<>();
    private transient TTTBoard tttBoard;
    private transient ClientConnection player1;
    private transient ClientConnection player2;
    private transient UserInformation player1Info;
    private transient UserInformation player2Info;

    private String player1Username;
    private String player2Username;
    private String id;
    private String gameStatus;
    private Timestamp startTime;
    private Timestamp endTime;
    private String nextMoveId;
    private DataSource ds = DatabaseManager.getInstance();
    private String startingPlayerId;
    private String winningPlayerId;


    public Game(ClientConnection player1) {
        UUIDGenerator gameId = new UUIDGenerator();
        this.id = gameId.getNewId();
        this.player1 = player1;
        this.player1Info = player1.getInformation();
        this.player1Username = player1Info.getUserName();
        tttBoard = new TTTBoard();
        this.gameStatus = "WAITING FOR ANOTHER PLAYER";
    }

    public Game(Game game) {
        this.player1Username = game.getPlayer1Username();
        this.player2Username = game.getPlayer2Username();
        this.id = game.getId();
        this.gameStatus = game.getGameStatus();
        this.startTime = game.getStartTime();
        this.endTime = game.getEndTime();
    }

    public void join(ClientConnection player2) {
        this.player2 = player2;
        player2Info = player2.getInformation();
        player2Username = player2Info.getUserName();
    }

    public String getWinningPlayerId() {
        return winningPlayerId;
    }

    public void setWinningPlayerId(String winningPlayerId) {
        this.winningPlayerId = winningPlayerId;
    }

    public String getId() {
        return this.id;
    }

    public void addGameObserver(ClientConnection client) {
        GameObservers.add(client);
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime() {
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        this.endTime = endTime;
    }


    public void addGameObserver(GameObserver observer) {
        gameObserversList.add(observer);
    }

    public void notifyObservers(Packet packet) {
        try {
            for (ClientConnection clientConnection : GameObservers) {
                clientConnection.getOutputStream().writeObject(packet);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void player1MakeMove(Move move) {
        move.setToken("X");

        tttBoard.setX(move.getRow(), move.getColumn());

        tttBoard.printBoard();
        System.out.println();
    }

    public void player2MakeMove(Move move) {
        move.setToken("O");
        tttBoard.setO(move.getRow(), move.getColumn());

        tttBoard.printBoard();
        System.out.println();
    }

    public char getCharInTile(int row, int col) {
        return tttBoard.getCharInCell(row, col);
    }

    public boolean checkIfValidMove(Move move) {
        return tttBoard.isCellEmpty(move.getRow(), move.getColumn());
    }

    public boolean isPlayer1Winner(Move move) {
        return tttBoard.isWinner(move.getRow(), move.getColumn(), 'X');
    }

    public boolean isPlayer2Winner(Move move) {
        return tttBoard.isWinner(move.getRow(), move.getColumn(), 'O');
    }

    public boolean isTieGame() {
        return tttBoard.isTieGame();
    }

    public boolean isOver() {
        if (tttBoard.isOver()) {
            setEndTime();
            return true;
        } else return false;

    }

    public UserInformation getPlayer1Info() {
        return player1Info;
    }

    public void setPlayer1Info(UserInformation player1Info) {

        this.player1Info = player1Info;
    }

    public UserInformation getPlayer2Info() {
        return player2Info;
    }

    public void setPlayer2Info(UserInformation player2Info) {
        this.player2Info = player2Info;
    }


    public ClientConnection getPlayer1ClientConnection() {
        return player1;
    }

    public ClientConnection getPlayer2ClientConnection() {
        return player2;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getPlayer1Username() {
        return player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public TTTBoard getTttBoard() {
        return tttBoard;
    }
}
