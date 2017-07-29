package com.example.flickrgallery.screen.photosearch.action;

import android.os.Handler;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoadPhotosActionImpl implements LoadPhotosAction {
    private final DataStreamProvider<String> dataStreamProvider;
    private final DataInputStreamDecoder<List<Photo>> dataInputStreamDecoder;

    public LoadPhotosActionImpl(
            DataStreamProvider<String> dataStreamProvider,
            DataInputStreamDecoder<List<Photo>> dataInputStreamDecoder
    ) {
        this.dataStreamProvider = dataStreamProvider;
        this.dataInputStreamDecoder = dataInputStreamDecoder;
    }

    @Override
    public Cancelable loadPhotos(String tags, Callback callback) {
        Handler handler = new Handler();
        LoadPhotosCancelable loadPhotosCancelable = new LoadPhotosCancelable(callback, handler);
        loadPhotosCancelable.load(tags);
        return loadPhotosCancelable;
    }

    class LoadPhotosCancelable implements Cancelable {
        private final Callback callback;
        private final Handler handler;

        public LoadPhotosCancelable(Callback callback, Handler handler) {
            this.callback = callback;
            this.handler = handler;
        }

        void load(final String tags) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = dataStreamProvider.provideDataInputStream(tags);
                        final List<Photo> result = dataInputStreamDecoder.decode(inputStream);
                        if(!isCanceled()) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onLoaded(result);
                                }
                            });
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                        if (!isCanceled()) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailed(e);
                                }
                            });
                        }
                    }
                }
            }).start();
        }

        AtomicBoolean isCanceled = new AtomicBoolean(false);
        @Override
        public boolean isCanceled() {
            return isCanceled.get();
        }

        @Override
        public void cancel() {
            isCanceled.set(true);
        }
    }
}