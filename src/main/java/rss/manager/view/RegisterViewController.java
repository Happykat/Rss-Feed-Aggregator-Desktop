package rss.manager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import rss.manager.MainApp;
import rss.manager.model.request.SignUpRequest;

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
        if (isInputValid() && isAvailable()) {
            mainApp.showLoginView();
        }
    }

    @FXML
    private void back() {
        mainApp.showLoginView();
    }

    private boolean isAvailable() {
        SignUpRequest request = new SignUpRequest(pseudo.getText(), password.getText());
        return request.send();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        final String loginText = pseudo.getText();
        final String passwordText = password.getText();
        final String passwordConfirmationText = passwordConfirmation.getText();

        if (loginText == null || loginText.length() == 0) {
            errorMessage += "Invalid login.\n";
        }

        if (passwordText == null || passwordText.length() == 0) {
            errorMessage += "Invalid password.\n";
        }

        if (!passwordText.equals(passwordConfirmationText)) {
            errorMessage += "Passwords does not match.\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
