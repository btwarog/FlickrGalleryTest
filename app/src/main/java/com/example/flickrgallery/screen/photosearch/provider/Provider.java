package com.example.flickrgallery.screen.photosearch.provider;

import com.example.flickrgallery.screen.photosearch.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosActionFake;

public class Provider {
    public static LoadPhotosAction provideLoadPhotosAction() {
        return new LoadPhotosActionFake();
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
