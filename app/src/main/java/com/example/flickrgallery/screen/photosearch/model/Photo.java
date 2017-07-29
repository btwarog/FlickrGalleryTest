package com.example.flickrgallery.screen.photosearch.model;

import java.io.Serializable;

public class Photo implements Serializable {
    private String title;
    private String link;
    private String media;
    private String dateTaken;
    private String description;
    private String published;

    public Photo(String title, String link, String media, String dateTaken, String description, String published) {
        this.title = title;
        this.link = link;
        this.media = media;
        this.dateTaken = dateTaken;
        this.description = description;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getMedia() {
        return media;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public String getDescription() {
        return description;
    }

    public String getPublished() {
        return published;
    }
}
