package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class GameBoardController
{
    @FXML
    Label player1Name, player2Name;

    @FXML
    Label playerOverallStatsWins, playerOverallStatsLosses, playerOverallStatsTies;

    @FXML
    Label currentGameStatsWins, currentGameStatsLosses, currentGameStatsTies;

    @FXML
    Label showChatHistory;

    @FXML
    TextArea userChatInput;

    @FXML
    Button userChatEnter, mainMenu, newGame, startNewChat;

    @FXML
    Label panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9;

    @FXML
    VBox myGamesContainer;      // container to place labels for all the games the user has going on.




}
