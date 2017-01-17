package rss.manager;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import rss.manager.model.Feed;
import rss.manager.model.Item;
import rss.manager.model.SimpleFeed;
import rss.manager.view.AddFeedViewController;
import rss.manager.view.FeedViewController;
import rss.manager.view.LoginViewController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Feed> feedList = FXCollections.observableArrayList();
    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    public ObservableList<Feed> getFeedList() {
        return (this.feedList);
    }
    public ObservableList<Item> getItemList() {
        return (this.itemList);
    }

    public MainApp(){
        this.feedList.add(new Feed("url1", "name1", "more then 10 characters", "title1"));
        this.feedList.add(new Feed("url2", "name2", "desc2", "title2"));
        this.feedList.add(new Feed("url3", "name3", "desc3", "title3"));
        this.feedList.add(new Feed("url4", "name4", "desc4", "title4"));

        this.itemList.add(new Item(
                "Tue, 17 Jan 2017 15:30:00 +0100",
                "http://www.developpez.com/actu/112593/La-fondation-Raspberry-Pi-lance-le-Compute-Module-3-la-derniere-version-du-module-Raspberry-Pi-destine-a-etre-integre-dans-d-autres-produits/",
                "La fondation Raspberry Pi lance le Compute Module 3",
                "La fondation Raspberry Pi lance le Compute Module 3, la dernière version du module Raspberry Pi destiné à être intégré dans d'autres produits"));

    }

    @Override
    public void start(Stage primaryStage) {
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
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

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
            loader.setLocation(MainApp.class.getResource("view/LoginView.fxml"));
            AnchorPane loginView = (AnchorPane) loader.load();

            rootLayout.setCenter(loginView);

            LoginViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFeedView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FeedView.fxml"));
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
    public boolean showAddFeedView(SimpleFeed feed) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AddFeedView.fxml"));
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
