package com.example.flickrgallery.screen.photosearch;

import com.example.flickrgallery.base.BaseView;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import java.util.List;

public interface PhotoSearchView extends BaseView {
    void showLoading(boolean show);
    void showError(String message);
    void showPhotos(List<Photo> photoList);
}
