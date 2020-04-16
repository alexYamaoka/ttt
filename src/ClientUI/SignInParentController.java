package ClientUI;

import Client.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInParentController implements Initializable {
    @FXML
    private SignInController signInController;

    private ClientController clientController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public SignInController getSignInController() {
        return signInController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
