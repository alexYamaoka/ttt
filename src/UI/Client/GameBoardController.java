package UI.Client;

import AccountService.AccountService;
import Client.ClientController;
import Models.Game;
import Models.Move;
import Models.TTTBoard;
import ObserverPatterns.GameListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class GameBoardController implements Initializable, GameListener {
    @FXML
    Label player1Name, player2Name, time, gameStatus;
    @FXML
    Button backToLobby, rematch, quit, zZ, zO, zT, oZ, oO, oT, tZ, tO, tT;

    private HashMap<Pair<Integer, Integer>, Button> buttons = new HashMap<>();

    private Game game;

    private ClientController clientController;
    private String gameId;
    private String player1Username;
    private String player2Username;

    public void playerMoved(int x, int y) {
        Move move = new Move(x, y, clientController.getAccountClient().getUserInformation(), gameId);
        Packet packet = new Packet(Packet.GAME_MOVE, clientController.getAccountClient().getUserInformation(), move);
        clientController.getGameClient().addRequestToServer(packet);
        resetTime();
    }
    public void playerMovedzZ(ActionEvent actionEvent) {
        playerMoved(0, 0);
    }
    public void playerMovedzO(ActionEvent actionEvent) {
        playerMoved(0, 1);
    }
    public void playerMovedzT(ActionEvent actionEvent) {
        playerMoved(0, 2);
    }
    public void playerMovedoZ(ActionEvent actionEvent) {
        playerMoved(1, 0);
    }
    public void playerMovedoO(ActionEvent actionEvent) {
        playerMoved(1, 1);
    }
    public void playerMovedoT(ActionEvent actionEvent) {
        playerMoved(1, 2);
    }
    public void playerMovedtZ(ActionEvent actionEvent) {
        playerMoved(2, 0);
    }
    public void playerMovedtO(ActionEvent actionEvent) {
        playerMoved(2, 1);
    }
    public void playerMovedtT(ActionEvent actionEvent) {
        playerMoved(2, 2);
    }

    public void rematch(ActionEvent actionEvent) {
        if (actionEvent.getSource() == rematch) {

        }
    }

    public void quit(ActionEvent actionEvent) {
        if (actionEvent.getSource() == quit) {
            Stage stage = null;
            Parent root = null;
            Packet packet = new Packet(Packet.GAME_CLOSE, clientController.getAccountClient().getUserInformation() , gameId);
            clientController.getGameClient().addRequestToServer(packet);

            stage = (Stage) quit.getScene().getWindow();
            root = clientController.getLobbyPane();
            stage.setScene(root.getScene());
            stage.show();
        }
    }

    public void backToLobby(ActionEvent actionEvent) {
        if (actionEvent.getSource() == backToLobby) {
            Stage stage = null;
            Parent root = null;

            stage = (Stage) quit.getScene().getWindow();
            root = clientController.getLobbyPane();
            stage.setScene(root.getScene());
            stage.show();
        }
    }

    private void countdownTime() {

    }

    public void resetTime() {
        countdownTime();
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void updateMove(Move move) {
        Platform.runLater(() -> {
            Pair pair = new Pair<>(move.getRow(), move.getColumn());
            buttons.get(pair).setText(move.getToken());
        });
    }

    @Override
    public void updateStatus(String message) {
        System.out.println("GAME STATUS:" + message);
        if (clientController.getAccountClient().getUserInformation().getUsername().equals(message)){
            gameStatus.setStyle("-fx-background-color: green");
            gameStatus.setText("You win!");
        }
        else if (message.equals("Tie!")){
            gameStatus.setStyle("-fx-background-color: blue");
            gameStatus.setText(message);
        }
        else if (message.equals("invalid-move")){
            gameStatus.setText(message);
        }
        else{
            gameStatus.setStyle("-fx-background-color: red");
            gameStatus.setText("You lose!");
        }

        FadeTransition ft = new FadeTransition(Duration.millis(3000), gameStatus);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }

    @Override
    public void setPlayer1Username(String player1Username) {
        this.player1Username = player1Username;
        player1Name.setText(player1Username);
    }

    @Override
    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
        player2Name.setText(player2Username);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons.put(new Pair<>(0, 0), zZ);
        buttons.put(new Pair<>(0, 1), zO);
        buttons.put(new Pair<>(0, 2), zT);
        buttons.put(new Pair<>(1, 0), oZ);
        buttons.put(new Pair<>(1, 1), oO);
        buttons.put(new Pair<>(1, 2), oT);
        buttons.put(new Pair<>(2, 0), tZ);
        buttons.put(new Pair<>(2, 1), tO);
        buttons.put(new Pair<>(2, 2), tT);
    }

    public void setGame(Game game) {
        this.game = game;
        this.gameId = game.getId();
    }

    private void playWinAnimation() {
    }
}
