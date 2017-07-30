package com.example.flickrgallery.screen.photosearch.action;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.model.PhotoMeadata;

public interface LoadPhotoMetadataAction {
    Cancelable loadPhotoMetadata(Photo photo, Callback callback);

    interface Callback {
        void onLoaded(PhotoMeadata photoMeadata);

        void onFailed(Throwable throwable);
    }
}
