package rss.manager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import rss.manager.MainApp;

public class RegisterViewController {
    @FXML
    private TextField pseudo;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirmation;

    private MainApp mainApp;

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainStage) {
        this.mainApp = mainStage;
    }

    @FXML
    private void register() {
        if (isInputValid()) {
            mainApp.showLoginView();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        String loginText = pseudo.getText();
        String passwordText = password.getText();
        String passwordConfirmationText = passwordConfirmation.getText();

        if (loginText == null || loginText.length() == 0) {
            errorMessage += "Invalid login\n";
        }
        if (passwordText == null || passwordText.length() == 0) {
            errorMessage += "Invalid password\n";
        }
        if (!passwordText.equals(passwordConfirmationText)) {
            errorMessage += "Password does not match\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
