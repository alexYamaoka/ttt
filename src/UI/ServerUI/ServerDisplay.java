package UI.ServerUI;

import ObserverPatterns.ServiceListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class ServerDisplay implements ServiceListener {


//    @FXML
//    private TextField txt_output;

    public void display(ActionEvent event) {


        //txt_output.setText();
    }


    @Override
    public void onDataChanged(String info) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // update label
            }
        });
    }
}
