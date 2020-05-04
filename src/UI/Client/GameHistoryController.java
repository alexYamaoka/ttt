package UI.Client;

import Models.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;


public class GameHistoryController implements Initializable {
    @FXML
    TableView gameHistoryTable;
    @FXML
    TableColumn<Game, String> gameID, opponent, startTime, endTime, results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    private void initializeTable() {
        gameID.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        opponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        results.setCellValueFactory(new PropertyValueFactory<>("results"));
    }

    // import an ObservableList of all game history from server
    private void loadGames(HashSet<Game> listOfGames) {
        System.out.println("Load games called!");
        gameHistoryTable.getItems().addAll(listOfGames);
    }

    public void getListOfGames(HashSet<Game> listOfGames) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameHistoryTable.getItems().clear();
                loadGames(listOfGames);
            }
        });
    }
}
