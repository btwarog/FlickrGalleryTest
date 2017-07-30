package com.example.flickrgallery.injection;

import com.example.flickrgallery.Navigator;
import com.example.flickrgallery.imageloader.ImageLoader;
import com.example.flickrgallery.screen.photosearch.action.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotoMetadataAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.PhotoUrlProvider;

public class Injector {

    private static Provider provider;

    public static void setProvider(Provider provider) {
        Injector.provider = provider;
    }

    public static LoadPhotosAction provideLoadPhotosAction() {
        return provider.provideLoadPhotosAction();
    }

    public static LoadPhotoMetadataAction provideLoadPhotoMetadataAction() {
        return provider.provideLoadPhotoMetadataAction();
    }

    public static ThrowableTranslator provideThrowableTranslator() {
        return provider.provideThrowableTranslator();
    }

    public static ImageLoader provideImageLoader() {
        return provider.provideImageLoader();
    }

    public static PhotoUrlProvider providePhotoUrlProvider() {
        return provider.providePhotoUrlProvider();
    }
}
