package rss.manager.view;

import com.hfabre.rss.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
        User user = new User(loginField.getText(), passwordField.getText());

        try {
            user.login();
            mainApp.setUser(user);
            mainApp.showFeedView();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid credentials");
            alert.setHeaderText("Your credentials don't match any account.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void register() {
        mainApp.showRegisterView();
    }
}
