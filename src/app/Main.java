package app;

import AccountService.AccountService;
import Client.ClientController;
import GameService.GameService;
import ObserverPatterns.ServiceListener;
import Shared.Packet;
import UI.ServerUI.ServerDisplay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main extends Application
{

    private AccountService accountService = AccountService.getInstance();
    private GameService gameService = GameService.getInstance();
    private ServerDisplay serverDisplayController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // start server ui

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/ServerUI/ServerDisplay.fxml"));
        Parent root = loader.load();
        serverDisplayController = loader.getController();
        serverDisplayController.setMain(this);

        accountService.addServiceListener(serverDisplayController);
        gameService.addServiceListener(serverDisplayController);

        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("EndFrame Server");
        stage.setScene(scene);
        stage.show();
    }

}
