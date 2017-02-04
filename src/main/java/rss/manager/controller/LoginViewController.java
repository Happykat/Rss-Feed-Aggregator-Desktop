package rss.manager.controller;

import com.hfabre.rss.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.commons.codec.binary.Base64;
import rss.manager.MainApp;
import rss.manager.ObjectDB;
import rss.manager.Pair;

import java.io.*;
import java.util.Objects;

public class LoginViewController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    private MainApp mainApp;

    private User user_ = null;
    private ObjectDB db_ = null;

    @FXML
    private void initialize() {
        // Get logs from file.
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MainApp.FILENAME))) {
            db_ = (ObjectDB) ois.readObject();
            String pwdDecoded = new String(Base64.decodeBase64((String)db_.getPair().getSecond()));
            user_ = new User((String) db_.getPair().getFirst(), pwdDecoded);
            loginField.setText((String) db_.getPair().getFirst());
            passwordField.setText(pwdDecoded);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setMainApp(MainApp mainStage) {
        this.mainApp = mainStage;
    }

    @FXML
    private void login() {
        if (user_ == null || !Objects.equals(loginField.getText(), user_.getUsername()) ||
                !Objects.equals(passwordField.getText(), user_.getPassword())) {
            user_ = new User(loginField.getText(), passwordField.getText());
        }

        try {
            user_.login();
            mainApp.setUser(user_);
            mainApp.showFeedView();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid credentials");
            alert.setHeaderText("Your credentials don't match any account.");
            alert.setContentText("Please correct your username and password.");
            alert.showAndWait();
        }

        // Writing logs in file
        if (mainApp.getUser() != null) {
            db_ = new ObjectDB();
            String pwdEncoded = new String(Base64.encodeBase64(mainApp.getUser().getPassword().getBytes()));
            db_.setPair(new Pair<>(mainApp.getUser().getUsername(), pwdEncoded));
            try {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MainApp.FILENAME))) {
                    oos.writeObject(db_);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void register() {
        mainApp.showRegisterView();
    }
}
