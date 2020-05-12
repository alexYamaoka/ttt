package UI.Client;

import Client.ClientController;
import Models.Game;
import ObserverPatterns.HistoryListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;


public class GameHistoryController implements Initializable, HistoryListener {
    @FXML
    private TableView gameHistoryTable;
    @FXML
    private TableColumn<Game, String> gameID, opponent, startTime, endTime, results;

    private ClientController clientController;

    private ObservableList<Game> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    private void initializeTable() {
        gameHistoryTable.setItems(data);
        gameID.setCellValueFactory(new PropertyValueFactory<>("id"));
        opponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        results.setCellValueFactory(new PropertyValueFactory<>("results"));
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void updateHistory(List<Game> list) {
        Platform.runLater(() -> {
            data.clear();
            data.addAll(list);
        });
    }
}
