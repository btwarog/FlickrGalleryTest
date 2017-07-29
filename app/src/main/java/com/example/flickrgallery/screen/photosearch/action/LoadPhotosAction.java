package com.example.flickrgallery.screen.photosearch.action;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import java.util.List;

public interface LoadPhotosAction {
    Cancelable loadPhotos(String tags, Callback callback);

    interface Callback {
        void onLoaded(List<Photo> photos);
        void onFailed(Throwable throwable);
    }
}
