package com.example.flickrgallery.screen.photosearch.view;

import com.example.flickrgallery.screen.photosearch.model.Photo;

import java.util.List;

public class PhotoSearchViewDummy implements PhotoSearchView {

    public static PhotoSearchViewDummy instance = new PhotoSearchViewDummy();

    private PhotoSearchViewDummy() {};

    public static PhotoSearchViewDummy getInstance() {
        return instance;
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showPhotos(List<Photo> photoList) {

    }

    @Override
    public void showFailedOpenGallery() {

    }
}
