package UI.Client;

import Client.ClientController;
import Models.Move;
import ObserverPatterns.GameListener;
import Shared.Packet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class GameBoardController implements Initializable, GameListener
{
    @FXML
    Label player1Name, player2Name, time;
    @FXML
    Button rematch, quit, zZ, zO, zT, oZ, oO, oT, tZ, tO, tT;

    private ClientController clientController;

    public void playerMoved(int x, int y){
        Move move = new Move(x, y, clientController.getClient().getUserInformation());
        Packet packet = new Packet(Packet.GAME_MOVE, clientController.getClient().getUserInformation(), move);
        clientController.getClient().addRequestToServer(packet);
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
    }

    private void countdownTime(){

    }
    public void resetTime(){
        time.setText("00:30");
        countdownTime();
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void updateMove(Move move) {

    }

    @Override
    public void updateStatus(String message) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
