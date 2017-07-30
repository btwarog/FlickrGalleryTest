package com.example.flickrgallery.injection;

import com.example.flickrgallery.Navigator;
import com.example.flickrgallery.imageloader.ImageLoader;
import com.example.flickrgallery.screen.photosearch.action.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotoMetadataAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.PhotoUrlProvider;

public interface Provider {
    LoadPhotosAction provideLoadPhotosAction();
    LoadPhotoMetadataAction provideLoadPhotoMetadataAction();
    ThrowableTranslator provideThrowableTranslator();
    ImageLoader provideImageLoader();
    PhotoUrlProvider providePhotoUrlProvider();
}
