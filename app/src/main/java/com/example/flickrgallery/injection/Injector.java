package com.example.flickrgallery.injection;

import com.example.flickrgallery.imageloader.ImageLoader;
import com.example.flickrgallery.imageloader.network.BitmapDecoderImpl;
import com.example.flickrgallery.imageloader.network.DataStreamProviderImpl;
import com.example.flickrgallery.imageloader.network.ImageLoaderFromUrl;
import com.example.flickrgallery.network.InputStreamProvider;
import com.example.flickrgallery.screen.photosearch.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.DataInputStreamDecoderImpl;
import com.example.flickrgallery.screen.photosearch.action.DataStreamProviderForTags;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosActionImpl;

public class Injector {

    private static Provider provider;

    public static void setProvider(Provider provider) {
        Injector.provider = provider;
    }

    public static LoadPhotosAction provideLoadPhotosAction() {
        return provider.provideLoadPhotosAction();
    }

    public static ThrowableTranslator provideThrowableTranslator() {
        return provider.provideThrowableTranslator();
    }

    public static ImageLoader provideImageLoader() {
        return provider.provideImageLoader();
    }
}
