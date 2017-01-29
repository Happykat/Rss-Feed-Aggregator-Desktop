package rss.manager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rss.manager.MainApp;
import rss.manager.model.Feed;
import rss.manager.model.Item;
import rss.manager.model.request.SimpleFeed;

public class FeedViewController {
    @FXML
    private TableView<Feed> feedTable;
    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Feed, String> nameColumn;
    @FXML
    private TableColumn<Item, String> itemColumn;


    private MainApp mainApp;

    public FeedViewController() {

    }

    private void showItems(Feed feed) {
        // TO DO: Request to get items' feed.

        if (feed != null){
            itemTable.setItems(mainApp.getItemList());
            itemColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        }
    }

    @FXML
    private void initialize() {
        // Initialize the feed table with the column.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Listen for selection changes and show the person details when changed.
        feedTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showItems(newValue));
    }

    @FXML
    private void deleteFeed() {
        int selectedIndex = feedTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            feedTable.getItems().remove(selectedIndex);
            itemTable.getItems().removeAll();
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Feed Selected");
            alert.setContentText("Please select a feed in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void addFeed() {
        SimpleFeed newFeed = new SimpleFeed();
        boolean okClicked = mainApp.showAddFeedView(newFeed);
        if (okClicked) {
            Feed feed = new Feed(newFeed.getName(), newFeed.getLink(), "default desc", "default title");
            mainApp.getFeedList().add(feed);
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        feedTable.setItems(mainApp.getFeedList());
    }
}
