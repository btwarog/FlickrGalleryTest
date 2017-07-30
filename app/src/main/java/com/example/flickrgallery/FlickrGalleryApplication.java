package com.example.flickrgallery;

import android.app.Application;

import com.example.flickrgallery.injection.Injector;
import com.example.flickrgallery.injection.ProviderImpl;

public class FlickrGalleryApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Injector.setProvider(new ProviderImpl());
    }
}
