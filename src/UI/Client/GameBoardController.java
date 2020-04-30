package UI.Client;

import Client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class GameBoardController
{
    @FXML
    Label player1Name, player2Name, time;
    @FXML
    Button rematch, quit, zZ, zO, zT, oZ, oO, oT, tZ, tO, tT;

    private ClientController clientController;

    public void playerMoved(Button button){
        resetTime();
    }
    public void playerMovedzZ(ActionEvent actionEvent) {
        playerMoved(zZ);
    }
    public void playerMovedzO(ActionEvent actionEvent) {
        playerMoved(zO);
    }
    public void playerMovedzT(ActionEvent actionEvent) {
        playerMoved(zT);
    }
    public void playerMovedoZ(ActionEvent actionEvent) {
        playerMoved(oZ);
    }
    public void playerMovedoO(ActionEvent actionEvent) {
        playerMoved(oO);
    }
    public void playerMovedoT(ActionEvent actionEvent) {
        playerMoved(oT);
    }
    public void playerMovedtZ(ActionEvent actionEvent) {
        playerMoved(tZ);
    }
    public void playerMovedtO(ActionEvent actionEvent) {
        playerMoved(tO);
    }
    public void playerMovedtT(ActionEvent actionEvent) {
        playerMoved(tT);
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
}
