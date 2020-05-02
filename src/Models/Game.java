package Models;

import DataBase.UUIDGenerator;
import GameService.GameThread;
import Models.BaseModel;
import Models.Move;
import Models.TTTBoard;
import ObserverPatterns.GameObserver;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Game extends BaseModel {

    private List<GameObserver> gameObserversList;
    private ArrayList<ClientConnection> GameObservers = new ArrayList<>();
    private TTTBoard tttBoard;
    ClientConnection player1;
    ClientConnection player2;
    UserInformation player1Info;
    UserInformation player2Info;
    private String Id;
    private String gameName;
    private Timestamp startTime;
    private Timestamp endTime;



    public Game(ClientConnection player1, String gameName) {
        UUIDGenerator gameId = new UUIDGenerator();
        this.Id = gameId.getNewId();
        this.gameName = gameName;
        this.player1 = player1;
        tttBoard = new TTTBoard();
    }

    //    public void start(){
//        GameThread gameThread = new GameThread(player1,player2,GameObservers);
//        gameThread.start();
//        setStartTime();
//    }
    public void join(ClientConnection player2){
        this.player2 = player2;
    }

    public String getId(){
        return this.Id;
    }

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void addGameObserver(ClientConnection client){
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


    public void addGameObserver(GameObserver observer)
    {
        gameObserversList.add(observer);
    }

    public void notifyObservers(Packet packet)
    {
        for (GameObserver observer: gameObserversList)
        {
            // observer.update(packet);     // broadcast packet with move or game status ex: tie game, winner
        }
    }

    public void player1MakeMove(Move move)
    {
        move.setToken("O");
        System.out.println("Row: " + move.getRow());
        System.out.println("column: " + move.getColumn());
        System.out.println("userInformation: " + move.getUserInformation());

        System.out.println("tttBoard: " + tttBoard);

        tttBoard.setX(move.getRow(), move.getColumn());

        tttBoard.printBoard();
        System.out.println();
    }

    public void player2MakeMove(Move move)
    {
        move.setToken("O");
        tttBoard.setO(move.getRow(), move.getColumn());

        tttBoard.printBoard();
        System.out.println();
    }

    public char getCharInTile(int row, int col)
    {
        return tttBoard.getCharInCell(row, col);
    }

    public boolean checkIfValidMove(Move move)
    {
        return tttBoard.isCellEmpty(move.getRow(), move.getColumn());
    }

    public boolean isPlayer1Winner(Move move){
        return tttBoard.isWinner(move.getRow(), move.getColumn(), 'X');
    }

    public boolean isPlayer2Winner(Move move)
    {
        return tttBoard.isWinner(move.getRow(), move.getColumn(), 'O');
    }

    public boolean isTieGame()
    {
        return tttBoard.isTieGame();
    }

    public boolean isOver(){
        if(tttBoard.isOver()) {
            setEndTime();
            return true;
        }else return false;

    }

    public UserInformation getPlayer1Info() {
        return player1Info;
    }

    public UserInformation getPlayer2Info(){
        return player2Info;
    }

    public void setPlayer1Info(UserInformation player1Info) {

        this.player1Info = player1Info;
    }

    public void setPlayer2Info(UserInformation player2Info) {
        this.player2Info = player2Info;
    }


    public ClientConnection getPlayer1ClientConnection()
    {
        return player1;
    }

    public ClientConnection getPlayer2ClientConnection()
    {
        return player2;
    }

}
