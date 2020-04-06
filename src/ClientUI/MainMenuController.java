package ClientUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Button playButton;

    @FXML
    private Button watchMatchesButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void playButtonClicked(ActionEvent event) {
        System.out.println("Play Button Clicked!");
    }

    public void watchMatchesButtonClicked(ActionEvent event) {
        System.out.println("Watch Matches Button Clicked!");
    }

    public void optionsButtonClicked(ActionEvent event) {
        System.out.println("Options Button Clicked!");
    }

    public void exitButtonClicked(ActionEvent event) {
        System.out.println("Exit Button Clicked!");
    }
}
