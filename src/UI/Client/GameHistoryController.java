package UI.Client;

import Client.ClientController;
import Models.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;


public class GameHistoryController implements Initializable {
    @FXML
    private TableView gameHistoryTable;
    @FXML
    private TableColumn<Game, String> gameID, opponent, startTime, endTime, results;
    @FXML
    private Button returnToMMButton;

    private ClientController clientController;

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

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void returnToMMButtonClicked(ActionEvent event) {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == returnToMMButton) {
            stage = (Stage) returnToMMButton.getScene().getWindow();
            root = clientController.getGameHistoryPane();
        }
        stage.setScene(root.getScene());
        stage.show();
    }
}
