package com.example.flickrgallery.screen.photosearch.action;

import android.os.Handler;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoadPhotosActionFake implements LoadPhotosAction {

    @Override
    public Cancelable loadPhotos(String tags, Callback callback) {
        Handler handler = new Handler();
        LoadCancelableFake loadCancelableFake = new LoadCancelableFake(handler, tags, callback);
        loadCancelableFake.load();
        return loadCancelableFake;
    }

    class LoadCancelableFake implements Cancelable {
        AtomicBoolean isCanceled = new AtomicBoolean(false);
        String tags;
        Handler handler;
        Callback callback;

        LoadCancelableFake(Handler handler, String tags, Callback callback) {
            this.handler = handler;
            this.tags = tags;
            this.callback = callback;
        }

        void load() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(!isCanceled()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onLoaded(new ArrayList<Photo>());
                            }
                        });
                    }
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
    }
}