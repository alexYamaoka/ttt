package app;

import Client.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Temp extends Application
{


    @Override
    public void start(Stage stage) throws Exception
    {
        // start client
        ClientController clientController = new ClientController(stage);
        stage.setTitle("EndFrame TicTacToe");
        clientController.run();
    }

}
