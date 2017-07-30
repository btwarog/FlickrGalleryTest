package com.example.flickrgallery.screen.photosearch.action;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.model.PhotoMeadata;

public class LoadPhotoMetadataActionSimpleImpl implements LoadPhotoMetadataAction {
    @Override
    public Cancelable loadPhotoMetadata(Photo photo, Callback callback) {
        PhotoMeadata photoMeadata = new PhotoMeadata(
                photo.getTitle(),
                photo.getDateTaken(),
                photo.getPublished()
        );
        callback.onLoaded(photoMeadata);
        return null;
    }
}
