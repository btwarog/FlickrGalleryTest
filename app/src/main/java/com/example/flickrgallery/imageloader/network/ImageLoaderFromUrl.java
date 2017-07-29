package com.example.flickrgallery.imageloader.network;

import android.graphics.Bitmap;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.imageloader.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageLoaderFromUrl implements ImageLoader {

    private DataStreamProvider dataStreamProvider;
    private BitmapDecoder bitmapDecoder;

    public ImageLoaderFromUrl(
            DataStreamProvider dataStreamProvider,
            BitmapDecoder bitmapDecoder
    ) {
        this.dataStreamProvider = dataStreamProvider;
        this.bitmapDecoder = bitmapDecoder;
    }

    @Override
    public CancelableJob loadImage(String url, Callback callback) {
        CancelableJob cancelableJob = new CancelableJob(url, callback);
        cancelableJob.performLoading();
        return cancelableJob;
    }

    class CancelableJob implements Cancelable {
        String url;
        Callback callback;
        AtomicBoolean isCanceled = new AtomicBoolean(false);
        AtomicBoolean isRunning = new AtomicBoolean(false);
        public CancelableJob(String url, Callback callback) {
            this.url = url;
            this.callback = callback;
        }

        void performLoading() {
            isRunning.set(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = dataStreamProvider.provideInputStream(url);
                        Bitmap bitmap = bitmapDecoder.decode(inputStream);
                        if (!isCanceled()) {
                            callback.onLoaded(bitmap);
                        }
                    } catch (IOException e) {
                        callback.onFailed(e);
                    }
                    isRunning.set(false);
                }
            }).start();
        }

        @Override
        public boolean isCanceled() {
            return isCanceled.get();
        }

        @Override
        public void cancel() {
            isCanceled.set(true);
        }

        public boolean isRunning() {
            return isRunning.get();
        }
    }
}