package rss.manager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import rss.manager.model.Feed;
import rss.manager.MainApp;
import rss.manager.model.SimpleFeed;

public class FeedViewController {
    @FXML
    private TableView<Feed> feedTable;
    @FXML
    private TableColumn<Feed, String> descColumn;
    @FXML
    private TableColumn<Feed, String> nameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label linkLabel;

    private MainApp mainApp;

    public FeedViewController() {

    }

    private void showFeedDetail(Feed feed) {
        if (feed != null) {
            nameLabel.setText(feed.getName());
            titleLabel.setText(feed.getTitle());
            descLabel.setText(feed.getDecription());
            linkLabel.setText(feed.getLink());
        } else {
            nameLabel.setText("");
            titleLabel.setText("");
            descLabel.setText("");
            linkLabel.setText("");
        }
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        descColumn.setCellValueFactory(cellData -> cellData.getValue().shortDescriptionProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Clear person details.
        showFeedDetail(null);

        // Listen for selection changes and show the person details when changed.
        feedTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFeedDetail(newValue));
    }

    @FXML
    private void deleteFeed() {
        int selectedIndex = feedTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            feedTable.getItems().remove(selectedIndex);
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
