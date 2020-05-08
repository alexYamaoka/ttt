package UI.ServerUI;

import ObserverPatterns.ServiceListener;
import Shared.Packet;
import app.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerDisplay implements ServiceListener {

    @FXML
    private ListView activeGames = new ListView();

    @FXML
    private ListView playersOnline = new ListView();

    @FXML
    private ListView allGames = new ListView();

    @FXML
    private ListView allAccounts = new ListView();


    private Main main;
    private BlockingQueue<Packet> packetsReceived = new LinkedBlockingQueue<>();




    @FXML
    public void onOnlinePlayerClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String username = playersOnline.getSelectionModel().getSelectedItem().toString();
            System.out.println("username selected: " + username);
        }
    }

    @FXML
    public void onAccountClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String username = allAccounts.getSelectionModel().getSelectedItem().toString();
            System.out.println("username selected: " + username);
        }
    }

    @FXML
    public void onActiveGameClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String game = activeGames.getSelectionModel().getSelectedItem().toString();
            System.out.println("game selected: " + game);
        }
    }

    @FXML
    public void onAllGameClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String game = allGames.getSelectionModel().getSelectedItem().toString();
            System.out.println("game selected: " + game);
        }
    }





    public void display(ActionEvent event) {

    }


    @Override
    public void onDataChanged(Packet packet) {

        packetsReceived.add(packet);
        updateUI();


    }

    private synchronized void updateUI()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                Packet packet = null;
                try
                {
                    packet = packetsReceived.take();

                    String message = packet.getData().toString();


                    switch (packet.getRequest())
                    {
                        case Packet.REGISTER_CLIENT:
                            allAccounts.getItems().add(message);

                        case Packet.SIGN_IN:
                            playersOnline.getItems().add(message);
                            break;

                        case Packet.SIGN_OUT:
                            playersOnline.getItems().remove(message);
                            break;

                        case Packet.ACTIVE_GAME:
                            activeGames.getItems().add(message);
                            allGames.getItems().add(message);
                            break;

                        case Packet.GAME_CLOSE:
                            activeGames.getItems().remove(message);

                    }



                    //serverMessages.getItems().add(sb.toString());
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }


            }
        });

    }






    public void setMain(Main main)
    {
        this.main = main;
    }
}
