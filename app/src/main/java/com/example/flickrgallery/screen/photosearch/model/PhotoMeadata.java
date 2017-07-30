package com.example.flickrgallery.screen.photosearch.model;

import java.io.Serializable;

public class PhotoMeadata implements Serializable {
    private String title;
    private String dateTaken;
    private String published;

    public PhotoMeadata(String title, String dateTaken, String published) {
        this.title = title;
        this.dateTaken = dateTaken;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public String getPublished() {
        return published;
    }
}
