package UI.ServerUI;

import Client.ClientController;
import Models.Move;
import Shared.GameInformation;
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
import java.util.List;
import java.util.ResourceBundle;


public class GameHistoryStatsController implements Initializable {
    @FXML
    Label gameID, player1Name, player2Name, startTime, endTime, winningPlayer, zZ, zO, zT, oZ, oO, oT, tZ, tO, tT;
    @FXML
    Button backButton;

    private HashMap<Pair<Integer, Integer>, Label> labels = new HashMap<>();

    private ServerDisplay serverDisplay;

    public void importGameInformation(GameInformation information, List<Move> moves){
        this.gameID.setText(information.getId());
        this.player1Name.setText(information.getPlayer1Username());
        this.player2Name.setText(information.getPlayer2Username());
        this.startTime.setText(information.getStartTime().toString());
        this.endTime.setText(information.getEndTime().toString());
        this.winningPlayer.setText(winningPlayer.getText() + " " + information.getWinningPlayerId());

        for(Move move : moves) {
            if (move.getPlayerId().equals(information.getStartingPlayerId())) {
                move.setToken("X");
            } else {
                move.setToken("O");
            }
            Pair pair = new Pair(move.getRow(), move.getColumn());
            labels.get(pair).setText(move.getToken());
        }
    }

    public void backButtonClicked(ActionEvent event) {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == backButton) {
            stage = (Stage) backButton.getScene().getWindow();
            root = serverDisplay.getDisplay();
        }
        stage.setScene(root.getScene());
        stage.show();
    }

    public void setServerDisplay(ServerDisplay serverDisplay) {
        this.serverDisplay = serverDisplay;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labels.put(new Pair<>(0, 0), zZ);
        labels.put(new Pair<>(0, 1), zO);
        labels.put(new Pair<>(0, 2), zT);
        labels.put(new Pair<>(1, 0), oZ);
        labels.put(new Pair<>(1, 1), oO);
        labels.put(new Pair<>(1, 2), oT);
        labels.put(new Pair<>(2, 0), tZ);
        labels.put(new Pair<>(2, 1), tO);
        labels.put(new Pair<>(2, 2), tT);
    }
}
