package com.example.flickrgallery.injection;

import com.example.flickrgallery.imageloader.ImageLoader;
import com.example.flickrgallery.imageloader.impl.BitmapDecoderImpl;
import com.example.flickrgallery.imageloader.impl.DataStreamProviderImpl;
import com.example.flickrgallery.imageloader.impl.ImageLoaderFromUrl;
import com.example.flickrgallery.network.InputStreamProvider;
import com.example.flickrgallery.screen.photosearch.action.DataInputStreamDecoderImpl;
import com.example.flickrgallery.screen.photosearch.action.DataStreamProviderForTags;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotoMetadataAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotoMetadataActionSimpleImpl;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosActionImpl;
import com.example.flickrgallery.screen.photosearch.action.PhotoUrlProvider;
import com.example.flickrgallery.screen.photosearch.action.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.model.Photo;

public class ProviderImpl implements Provider {
    static ImageLoader imageLoaderInstance = null;

    public InputStreamProvider provideInputStreamProvider() {
        return new InputStreamProvider();
    }

    @Override
    public LoadPhotosAction provideLoadPhotosAction() {
        return new LoadPhotosActionImpl(
                new DataStreamProviderForTags(provideInputStreamProvider()),
                new DataInputStreamDecoderImpl()
        );
    }

    @Override
    public LoadPhotoMetadataAction provideLoadPhotoMetadataAction() {
        return new LoadPhotoMetadataActionSimpleImpl();
    }

    @Override
    public ThrowableTranslator provideThrowableTranslator() {
        return new ThrowableTranslator() {
            @Override
            public String translateThrowable(Throwable throwable) {
                return throwable.getLocalizedMessage();
            }
        };
    }


    @Override
    public ImageLoader provideImageLoader() {
        if (imageLoaderInstance == null) {
            imageLoaderInstance = new ImageLoaderFromUrl(
                    new DataStreamProviderImpl(provideInputStreamProvider()),
                    new BitmapDecoderImpl()
            );
        }

        return imageLoaderInstance;
    }

    @Override
    public PhotoUrlProvider providePhotoUrlProvider() {
        return new PhotoUrlProvider() {
            @Override
            public String getPhotoUrl(Photo photo) {
                return photo.getMedia();
            }
        };
    }
}
