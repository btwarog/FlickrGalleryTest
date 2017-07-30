package com.example.flickrgallery.screen.photosearch.action;

import com.example.flickrgallery.screen.photosearch.model.Photo;

public interface PhotoUrlProvider {
    String getPhotoUrl(Photo photo, Size size);

    enum Size {
        Thumb,
        Gallery
    }
}
