package com.example.flickrgallery.screen.photosearch.view;

import android.graphics.Bitmap;

import com.example.flickrgallery.base.BaseView;
import com.example.flickrgallery.screen.photosearch.model.PhotoMeadata;

public interface PhotoItemView extends BaseView {
    void showBitmap(Bitmap bitmap);

    void showLoading(boolean show);

    void showFailed(boolean show, String message);

    void showPhotoMetadata(PhotoMeadata loadedPhotoMetadata);
}
