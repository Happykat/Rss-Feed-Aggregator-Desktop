package rss.manager.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import rss.manager.MainApp;

public class RootLayoutViewController {

    @FXML
    private MenuItem disconnect_;
    @FXML
    private MenuItem close_;

    private MainApp mainApp;

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainStage) {
        this.mainApp = mainStage;
    }

    @FXML
    private void disconnect() {
        mainApp.showLoginView();
    }

    @FXML
    private void close() {
        Platform.exit();
    }

}
