package com.example.flickrgallery.injection;

import com.example.flickrgallery.imageloader.ImageLoader;
import com.example.flickrgallery.screen.photosearch.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;

public interface Provider {
    LoadPhotosAction provideLoadPhotosAction();
    ThrowableTranslator provideThrowableTranslator();
    ImageLoader provideImageLoader();
}
