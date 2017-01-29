package rss.manager;

import com.hfabre.rss.Feed;
import com.hfabre.rss.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rss.manager.controller.*;

import java.io.IOException;

public class MainApp extends Application {
    public static String FILENAME = "log.txt";
    private Stage primaryStage;
    private BorderPane rootLayout;

    private User user_ = null;
    public User getUser() { return user_; }
    public void setUser(User user) { user_ = user; }

    public MainApp(){
    }

    @Override
    public void start(Stage primaryStage) {
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RSS Feed Aggregator");

        initRootLayout();

        showLoginView();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutViewController controller = loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/LoginView.fxml"));
            AnchorPane loginView = (AnchorPane) loader.load();

            rootLayout.setCenter(loginView);

            LoginViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RegisterView.fxml"));
            AnchorPane registerView = (AnchorPane) loader.load();

            rootLayout.setCenter(registerView);

            RegisterViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFeedView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/FeedView.fxml"));
            AnchorPane mainView = (AnchorPane) loader.load();

            rootLayout.setCenter(mainView);

            FeedViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showAddFeedView(Feed feed) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AddFeedView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New feed");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AddFeedViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFeed(feed);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return Stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
