package Client;

import ClientUI.MainMenuController;
import Shared.UserInformation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashSet;

public class ClientController
{
    private Client client;
    private Stage stage;
    private MainMenuController mainMenuController;

    // online list
    // server list
    private ReadMessageBus readMessageBus;


    public ClientController(UserInformation userInformation, Stage stage)
    {
        client = new Client("localhost", 8000, userInformation);
        this.stage = stage;
        setUpClientToUI();
    }


    private void setUpClientToUI()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientUI/MainMenu.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);

            mainMenuController = fxmlLoader.getController();
            // mainMenuController.setClientController(this);
            readMessageBus = new ReadMessageBus(this);
            readMessageBus.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Client getClient()
    {
        return client;
    }

}
