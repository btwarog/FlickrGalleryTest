package com.example.flickrgallery.screen.photosearch.view;

import android.graphics.Bitmap;

import com.example.flickrgallery.base.BaseView;

public interface PhotoItemView extends BaseView {
    void showBitmap(Bitmap bitmap);

    void showLoading(boolean show);

    void showFailed(boolean show, String message);
}
