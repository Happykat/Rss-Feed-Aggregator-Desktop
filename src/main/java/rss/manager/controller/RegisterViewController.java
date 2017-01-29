package rss.manager.controller;

import com.hfabre.rss.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import rss.manager.MainApp;

public class RegisterViewController {
    @FXML
    private TextField login;
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
        User user = new User(login.getText(), password.getText());
        final String pwdConfText = passwordConfirmation.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (!user.getPassword().equals(pwdConfText)) {
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Invalid fields.");
            alert.setContentText("Passwords does not match.\n");
            alert.showAndWait();
        } else {
            try {
                user.signup();
                mainApp.setUser(user);
                mainApp.showLoginView();
            } catch (Exception e) {
                alert.setTitle("Invalid credentials");
                alert.setHeaderText("This account already exists.");
                alert.setContentText("Please change your username and password.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void back() {
        mainApp.showLoginView();
    }

}
