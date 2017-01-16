package rss.manager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rss.manager.MainApp;

public class LoginViewController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    private MainApp mainApp;

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainStage) {
        this.mainApp = mainStage;
    }

    @FXML
    private void login() {
        if (isInputValid()) {
            mainApp.showFeedView();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        String name = loginField.getText();
        String link = passwordField.getText();

        if (name == null || name.length() == 0) {
            errorMessage += "Invalid login\n";
        }
        if (link == null || link.length() == 0) {
            errorMessage += "Invalid password\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
