package com.example.flickrgallery.imageloader.impl;

import android.graphics.Bitmap;

import com.example.flickrgallery.imageloader.ImageLoader;

import junit.framework.Assert;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class ImageLoaderFromUrlTest {

    @Test
    public void cancelTest() {
        DataStreamProvider dataStreamProvider = new DataStreamProviderFromResources();
        BitmapDecoder bitmapDecoder = new BitmapDecoderFake(null);
        ImageLoaderFromUrl imageLoaderFromUrl = new ImageLoaderFromUrl(
                dataStreamProvider,
                bitmapDecoder
        );
        final AtomicBoolean onLoadedCalled = new AtomicBoolean(false);
        final AtomicBoolean onFailedCalled = new AtomicBoolean(false);
        ImageLoaderFromUrl.CancelableJob cancelableJob = imageLoaderFromUrl.loadImage("unused", new ImageLoader.Callback() {
            @Override
            public void onLoaded(Bitmap bitmap) {
                onLoadedCalled.set(true);
            }

            @Override
            public void onFailed(Throwable throwable) {
                onFailedCalled.set(true);
            }
        });
        cancelableJob.cancel();
        while (cancelableJob.isRunning());

        Assert.assertFalse("canceled but called onLoaded", onLoadedCalled.get());
    }

    @Test
    public void onLoadedTest() {
        DataStreamProvider dataStreamProvider = new DataStreamProviderFromResources();
        BitmapDecoder bitmapDecoder = new BitmapDecoderFake(null);
        ImageLoaderFromUrl imageLoaderFromUrl = new ImageLoaderFromUrl(
                dataStreamProvider,
                bitmapDecoder
        );
        final AtomicBoolean onLoadedCalled = new AtomicBoolean(false);
        final AtomicBoolean onFailedCalled = new AtomicBoolean(false);
        ImageLoaderFromUrl.CancelableJob cancelableJob = imageLoaderFromUrl.loadImage("unused", new ImageLoader.Callback() {
            @Override
            public void onLoaded(Bitmap bitmap) {
                onLoadedCalled.set(true);
            }

            @Override
            public void onFailed(Throwable throwable) {
                onFailedCalled.set(true);
            }
        });
        while (cancelableJob.isRunning());

        Assert.assertTrue("finished running and onLoaded not called", onLoadedCalled.get());
    }
}
