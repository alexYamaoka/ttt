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
    private TableView activeGames;

    @FXML
    private TableColumn player1Column, player2Column, statusColumn, optionsColumn;

    @FXML
    private Button updateTableButton, newGameAgainstComputerButton, newGameButton;

    private ClientController clientController;


    Set<String> listOfGames = new HashSet<>();
    Set<String> listOfOnlinePlayers = new HashSet<>();




    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

//    public void onJoinGameButtonClicked(ActionEvent event){
//        if (event.getSource() == JoinGameButton) {
//            String selectedGame = activeGames.getSelectionModel().getSelectedItem().toString();
////            String selectedGame = LobbyGameListview.getSelectionModel().getSelectedItem().toString();
//            System.out.println("selected item in listView: " + selectedGame);
//
//
//
//            Packet packet = new Packet(Packet.JOIN_GAME, clientController.getAccountClient().getUserInformation(), selectedGame);
//            clientController.getGameClient().addRequestToServer(packet);
//
//
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    Stage stage = null;
//                    Parent root = null;
//
//                    stage = (Stage) NewGameButton.getScene().getWindow();
//                    root = clientController.getGameBoardPane();
//                    stage.setScene(root.getScene());
//                    stage.show();
//
//                    System.out.println("Displaying new game window");
//                }
//            });
//        }
//    }

//    public void onSpectateGameButtonClicked(ActionEvent event){
//        if (event.getSource() == SpectateGameButton) {
//
//        }
//    }

    public void onCreateGameButtonClicked(ActionEvent event) {
        if (event.getSource() == newGameButton) {
            String gameName = clientController.getAccountClient().getUserInformation().getUserName() + ":" + System.currentTimeMillis();
            Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientController.getAccountClient().getUserInformation(), gameName);

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

                    stage = (Stage) newGameButton.getScene().getWindow();
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
                activeGames.getItems().add(gameName);
            }
        });
    }

    @Override
    public void getListOfGames(HashSet<String> listOfGames)
    {
        this.listOfGames.clear();

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
                        activeGames.getItems().add(gameName);
                    }
                }
            });
        }
    }

    @Override
    public void getListOfOnlinePlayers(HashSet<String> listOfOnlinePlayers)
    {
        System.out.println("inside game lobby get list of online players");

        this.listOfOnlinePlayers.clear();

        if (listOfOnlinePlayers != null)
        {
            this.listOfOnlinePlayers = listOfOnlinePlayers;

            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    for (String username: listOfOnlinePlayers)
                    {
                        activeGames.getItems().add(username);
                    }
                }
            });
        }
    }

}
