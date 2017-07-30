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

public class ProviderImpl implements Provider{
    @Override
    public LoadPhotosAction provideLoadPhotosAction() {
        return new LoadPhotosActionImpl(
                new DataStreamProviderForTags(new InputStreamProvider()),
                new DataInputStreamDecoderImpl()
        );
    }

    @Override
    public ThrowableTranslator provideThrowableTranslator() {
        return new ThrowableTranslator() {
            @Override
            public String translateThrowable(Throwable throwable) {
                return "fake error";
            }
        };
    }

    static ImageLoader imageLoaderInstance = null;

    @Override
    public ImageLoader provideImageLoader() {
        if (imageLoaderInstance == null) {
            imageLoaderInstance = new ImageLoaderFromUrl(
                    new DataStreamProviderImpl(),
                    new BitmapDecoderImpl()
            );
        }

        return imageLoaderInstance;
    }
}
