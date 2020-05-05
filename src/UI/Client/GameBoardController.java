package UI.Client;

import Client.ClientController;
import Models.Move;
import ObserverPatterns.GameListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class GameBoardController implements Initializable, GameListener {
    @FXML
    Label player1Name, player2Name, time;
    @FXML
    Button rematch, quit, zZ, zO, zT, oZ, oO, oT, tZ, tO, tT;

    private HashMap<Pair<Integer, Integer>, Button> buttons = new HashMap<>();

    private ClientController clientController;
    private String gameName;
    private String player1Username;
    private String player2Username;


    public void playerMoved(int x, int y) {
        Move move = new Move(x, y, clientController.getAccountClient().getUserInformation(), gameName);
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
    }

    public void quit(ActionEvent actionEvent) {
        if (actionEvent.getSource() == quit) {
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
        time.setText("00:30");
        countdownTime();
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void updateMove(Move move) {

        System.out.println("update move has been called");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int row = move.getRow();
                int col = move.getColumn();
                UserInformation userInformation = move.getUserInformation();


                // used players username to determine who is X and who is O.
                if (player1Username.equals(userInformation.getUserName())) {
                    System.out.println("move was  mine");
                    if (row == 0 && col == 0)
                        zZ.setText("X");
                    else if (row == 0 && col == 1)
                        zO.setText("X");
                    else if (row == 0 && col == 2)
                        zT.setText("X");
                    else if (row == 1 && col == 0)
                        oZ.setText("X");
                    else if (row == 1 && col == 1)
                        oO.setText("X");
                    else if (row == 1 && col == 2)
                        oT.setText("X");
                    else if (row == 2 && col == 0)
                        tZ.setText("X");
                    else if (row == 2 && col == 1)
                        tO.setText("X");
                    else if (row == 2 && col == 2)
                        tT.setText("X");

                } else if (player2Username.equals(userInformation.getUserName())){
                    System.out.println("move was oppenents");
                    if (row == 0 && col == 0)
                        zZ.setText("O");
                    else if (row == 0 && col == 1)
                        zO.setText("O");
                    else if (row == 0 && col == 2)
                        zT.setText("O");
                    else if (row == 1 && col == 0)
                        oZ.setText("O");
                    else if (row == 1 && col == 1)
                        oO.setText("O");
                    else if (row == 1 && col == 2)
                        oT.setText("O");
                    else if (row == 2 && col == 0)
                        tZ.setText("O");
                    else if (row == 2 && col == 1)
                        tO.setText("O");
                    else if (row == 2 && col == 2)
                        tT.setText("O");
                }
            }
        });

    }

    @Override
    public void updateStatus(String message) {
        System.out.println("GAME STATUS:" + message);
    }

    @Override
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public void setPlayer1Username(String player1Username) {
        System.out.println("setPlayer1Username: " + player1Username);
        this.player1Username = player1Username;
    }

    @Override
    public void setPlayer2Username(String player2Username) {
        System.out.println("setPlayer2Username: " + player2Username);
        this.player2Username = player2Username;
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
}
