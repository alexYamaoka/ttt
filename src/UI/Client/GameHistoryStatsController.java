package UI.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class GameHistoryStatsController {
    @FXML
    Label gameID, player1Name, player2Name, startTime, endTime, result, zZ, zO, zT, oZ, oO, oT, tZ, tO, tT;

    public void importGameInformation(String gameID, String player1Name, String player2Name, String startTime, String endTime, String result, String zZ, String zO, String zT, String oZ, String oO, String oT, String tZ, String tO, String tT){
        this.gameID.setText(gameID);
        this.player1Name.setText(player1Name);
        this.player2Name.setText(player2Name);
        this.startTime.setText(startTime);
        this.endTime.setText(endTime);
        this.result.setText(result);
        this.zZ.setText(zZ);
        this.zO.setText(zO);
        this.zT.setText(zT);
        this.oZ.setText(oZ);
        this.oO.setText(oO);
        this.oT.setText(oT);
        this.tZ.setText(tZ);
        this.tO.setText(tO);
        this.tT.setText(tT);
    }
}
