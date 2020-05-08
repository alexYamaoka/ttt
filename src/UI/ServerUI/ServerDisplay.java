package UI.ServerUI;

import ObserverPatterns.ServiceListener;
import Shared.Packet;
import app.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerDisplay implements ServiceListener {

    @FXML
    private ListView serverMessages = new ListView();

    @FXML
    private ListView onlinePlayers = new ListView();

    private Main main;

    private BlockingQueue<Packet> packetsReceived = new LinkedBlockingQueue<>();







    public void display(ActionEvent event) {

    }


    @Override
    public void onDataChanged(Packet packet) {

        packetsReceived.add(packet);
        updateUI();


    }

    private synchronized void updateUI()
    {
        try
        {
            Packet packet = packetsReceived.take();

            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append(packet.getInformation().getUserName());
                    sb.append(" : ");
                    sb.append(packet.getRequest());
                    sb.append(" : ");
                    sb.append(packet.getData().toString());

                    serverMessages.getItems().add(sb.toString());
                }
            });
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }






    public void setMain(Main main)
    {
        this.main = main;
    }
}
