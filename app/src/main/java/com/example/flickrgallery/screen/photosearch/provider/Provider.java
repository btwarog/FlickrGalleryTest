package com.example.flickrgallery.screen.photosearch.provider;

import com.example.flickrgallery.network.InputStreamProvider;
import com.example.flickrgallery.screen.photosearch.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.DataInputStreamDecoderImpl;
import com.example.flickrgallery.screen.photosearch.action.DataStreamProviderForTags;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosActionImpl;

public class Provider {
    public static LoadPhotosAction provideLoadPhotosAction() {
        return new LoadPhotosActionImpl(
                new DataStreamProviderForTags(new InputStreamProvider()),
                new DataInputStreamDecoderImpl()
        );
    }

    public static ThrowableTranslator provideThrowableTranslator() {
        return new ThrowableTranslator() {
            @Override
            public String translateThrowable(Throwable throwable) {
                return "fake error";
            }
        };
    }
}
