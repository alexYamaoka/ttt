package ClientUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void playButtonClicked(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == playButton) {
            stage = (Stage) playButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Options Button Clicked!");
        System.out.println("Play Button Clicked!");
    }

    public void watchMatchesButtonClicked(ActionEvent event) {
        System.out.println("Watch Matches Button Clicked!");
    }

    public void optionsButtonClicked(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == optionsButton) {
            stage = (Stage) optionsButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Options.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Options Button Clicked!");
    }

    public void exitButtonClicked(ActionEvent event) {
        System.out.println("Exit Button Clicked!");
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
