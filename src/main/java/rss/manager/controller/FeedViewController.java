package rss.manager.controller;

import com.hfabre.rss.Article;
import com.hfabre.rss.Feed;
import com.hfabre.rss.Trending;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rss.manager.MainApp;

import java.text.SimpleDateFormat;

public class FeedViewController {
    @FXML
    private TableView<Feed> feedTable;
    @FXML
    private TableView<Article> itemTable;
    @FXML
    private TableColumn<Feed, String> nameColumn;
    @FXML
    private TableColumn<Article, String> itemColumn;
    @FXML
    private TextArea    articleText_;
    @FXML
    private Button      allArticlesButton;

    private ObservableList<Article> articleList_;
    private ObservableList<Feed> feedList_;
    private int trendingPage = 1;

    private MainApp mainApp;

    public FeedViewController() {

    }

    private void showItems(Feed feed) {
        System.out.println("SHOW ITEMS FROM FEED");
            if (feed != null) {
                try {
                    System.out.println("Feed ID: " + feed.getId());
                    System.out.println("Token: " + mainApp.getUser().getToken());
                    articleList_ = FXCollections.observableArrayList(Feed.find(feed.getId(), mainApp.getUser().getToken()));

                    for (Article article : articleList_) {
                        if (!article.isRead()) {
                            article.setTitle("*" + article.getTitle());
                        }
                    }

                    itemTable.setItems(articleList_);
                    itemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

                    // Listen for selection changes and show the person details when changed.
                    itemTable.getSelectionModel().selectedItemProperty().addListener(
                            (observable, oldValue, newValue) -> showArticleText(newValue));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }

    private void showArticleText(Article article) {
        System.out.println("SHOW ARTICLE TEXT");

        if (article != null) {

            if (!article.isRead()) { // Make article as read
                try {
                    article.setRead(mainApp.getUser().getToken());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            Hyperlink link = new Hyperlink(article.getUrl());
            articleText_.setText(article.getDescription());
            String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(article.getPublished());

            articleText_.appendText("\n\n" + "Publication date: " + timeStamp);
            articleText_.appendText("\n\n" + "Link: " + article.getUrl());
        }
    }

    @FXML
    private void nextAllItems() {
        ++trendingPage;
        showAllItems();
    }

    @FXML
    private void previousAllItems() {
        if (trendingPage > 1) --trendingPage;
        showAllItems();
    }

    @FXML
    private void allItems() {
        trendingPage = 1;
        showAllItems();
    }

    @FXML
    private void showAllItems() {
        try {
            Trending trending = Trending.get(trendingPage, mainApp.getUser().getToken());
            articleList_ = FXCollections.observableArrayList(trending.getArticles());

            for (Article article : articleList_) {
                if (!article.isRead()) {
                    article.setTitle("*" + article.getTitle());
                }
            }

            allArticlesButton.setText("All Articles - Page " + trendingPage);
            itemTable.setItems(articleList_);
            itemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

            // Listen for selection changes and show the person details when changed.
            itemTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showArticleText(newValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFeeds() {
        try {
            feedList_ = FXCollections.observableArrayList(Feed.all(mainApp.getUser().getToken()));

            // Add observable list data to the table
            feedTable.setItems(feedList_);

            // Initialize the feed table with the column.
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

            // Listen for selection changes and show the person details when changed.
            feedTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showItems(newValue));
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showLoginView();
        }
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void deleteFeed() {
        int selectedIndex = feedTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                Feed feedToRemove = feedTable.getSelectionModel().getSelectedItem();
                feedTable.getItems().remove(feedToRemove);
                feedToRemove.delete(mainApp.getUser().getToken());
                articleList_.clear();
            } catch(Exception e) {
                e.printStackTrace();
            }
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
        Feed feed = new Feed();
        boolean okClicked = mainApp.showAddFeedView(feed);
        if (okClicked) {
            try {
                feed = Feed.add(feed.getUrl(), mainApp.getUser().getToken());
                System.out.println("--New Feed--");
                System.out.println("Url: " + feed.getUrl());
                System.out.println("Title: " + feed.getTitle());
                feedList_.add(feed);
            } catch (Exception e) {
                e.printStackTrace();
                // Bad URL.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Bad URL");
                alert.setHeaderText("This URL is not valid");
                alert.setContentText("Please select a valid URL.");

                alert.showAndWait();

            }
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        showFeeds();
    }
}
