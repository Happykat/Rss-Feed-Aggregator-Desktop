package rss.manager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Feed {
    private final StringProperty link;
    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty title;

    public Feed() {
        this(null, null, null, null);
    }

    public Feed(String link, String name, String description, String title) {
        this.link = new SimpleStringProperty(link);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.title = new SimpleStringProperty(title);
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

    public String getDescription() {
        return (this.description.get());
    }

    public String getTitle() {
        return (this.title.get());
    }

    public StringProperty linkProperty() {
        return (this.link);
    }

    public StringProperty shortDescriptionProperty() {
        int maxSize = 10;
        String description = getDescription();
        StringProperty shortDescription = new SimpleStringProperty();

        if (description.length() > maxSize) {
            shortDescription.set(description.substring(0, maxSize).concat("..."));
        } else {
            shortDescription = descriptionProperty();
        }
        return (shortDescription);
    }

    public StringProperty descriptionProperty() {
        return (this.description);
    }

    public StringProperty titleProperty() {
        return (this.title);
    }
}
