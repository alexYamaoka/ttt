package UI.Client;

import ObserverPatterns.LobbyListener;
import Shared.Packet;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import Client.ClientController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

public class GameLobby implements Initializable, LobbyListener {


    @FXML
    public ListView LobbyGameListview = new ListView();

    @FXML
    private Button JoinGameButton;

    @FXML
    private Button SpectateGameButton;
    @FXML
    private Button NewGameButton;
    private ClientController clientController;
    @FXML
    private Button Quit;


    Set<String> listOfGames = new HashSet<>();




    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onJoinGameButtonClicked(ActionEvent event){
        if (event.getSource() == JoinGameButton) {
            String selectedGame = LobbyGameListview.getSelectionModel().getSelectedItem().toString();
            System.out.println("selected item in listView: " + selectedGame);
        }
    }

    public void onSpectateGameButtonClicked(ActionEvent event){
        if (event.getSource() == SpectateGameButton) {

        }
    }
       
    public void onPlayButtonClicked(ActionEvent event) {
        if (event.getSource() == NewGameButton) {
            Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientController.getAccountClient().getUserInformation(), "test");

            System.out.println("New Game Created " + " Created by " + clientController.getAccountClient().getUserInformation().getFirstName() );


            clientController.getGameClient().addRequestToServer(packet);
        }

    }

    public void onQuitClicked(ActionEvent event){

    }

    @Override
    public void newGame(String message) {
        if(message.equalsIgnoreCase("SUCCESS")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //LobbyGameListview.getItems().add("test");

                    Stage stage = null;
                    Parent root = null;

                    stage = (Stage) NewGameButton.getScene().getWindow();
                    root = clientController.getGameBoardPane();
                    stage.setScene(root.getScene());
                    stage.show();

                    System.out.println("Displaying new game window");
                }
            });
        }
    }

    @Override
    public void updateUIWithNewGame(String gameName)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                listOfGames.add(gameName);
                LobbyGameListview.getItems().add(gameName);
            }
        });
    }

    @Override
    public void getListOfGames(HashSet<String> listOfGames)
    {
        if (listOfGames != null)
        {
            this.listOfGames = listOfGames;

            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    for (String gameName: listOfGames)
                    {
                        LobbyGameListview.getItems().add(gameName);
                    }
                }
            });
        }

    }


}
