package rss.manager.model.request;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleFeed {
    private final StringProperty link;
    private final StringProperty name;

    public SimpleFeed() {
        this(null, null);
    }

    public SimpleFeed(String link, String name) {
        this.link = new SimpleStringProperty(link);
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return (this.name.get());
    }

    public StringProperty nameProperty() {
        return (this.name);
    }

    public String getLink() {
        return (this.link.get());
    }

    public StringProperty linkProperty() {
        return (this.link);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public boolean save() {
        return (true);
    }
}
