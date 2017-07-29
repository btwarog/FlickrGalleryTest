package com.example.flickrgallery.imageloader;

import android.graphics.Bitmap;

import com.example.flickrgallery.base.Cancelable;

public interface ImageLoader {
    Cancelable loadImage(String uri, Callback callback);

    interface Callback {
        void onLoaded(Bitmap bitmap);
        void onFailed(Throwable throwable);
    }
}
