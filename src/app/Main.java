package app;

import AccountService.AccountService;
import Client.ClientController;
import GameService.GameService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // start AccountService
        AccountService service = new AccountService();
        service.start();

        GameService game = new GameService();
        game.start();

        // start client
        ClientController clientController = new ClientController(stage);
        stage.setTitle("EndFrame TicTacToe");
        clientController.run();

/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/Client/SignIn.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();


 */

    }
}
