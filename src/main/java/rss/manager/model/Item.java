package rss.manager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final StringProperty date;
    private final StringProperty link;
    private final StringProperty description;
    private final StringProperty title;

    public Item() {
        this(null, null, null, null);
    }

    public Item(String date, String link, String description, String title) {
        this.link = new SimpleStringProperty(link);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.title = new SimpleStringProperty(title);
    }

    public String getDate() {
        return (this.date.get());
    }

    public String getLink() {
        return (this.link.get());
    }

    public String getDescription() {
        return (this.description.get());
    }

    public String getTitle() {
        return (this.title.get());
    }

    public StringProperty dateProperty() {
        return (this.date);
    }

    public StringProperty linkProperty() {
        return (this.link);
    }

    public StringProperty descriptionProperty() {
        return (this.description);
    }

    public StringProperty titleProperty() {
        return (this.title);
    }

}
