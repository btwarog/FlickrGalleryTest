package com.example.flickrgallery.screen.photosearch.action;

import android.text.TextUtils;

import com.example.flickrgallery.Constants;
import com.example.flickrgallery.network.InputStreamProvider;

import java.io.IOException;
import java.io.InputStream;

public class DataStreamProviderForTags implements DataStreamProvider<String> {
    private final InputStreamProvider inputStreamProvider;

    public DataStreamProviderForTags(InputStreamProvider inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    @Override
    public InputStream provideDataInputStream(String commaDelimitedTags) throws IOException {
        String url = prepareUrlWithTags(commaDelimitedTags);

        return inputStreamProvider.provideInputStreamForUrl(url);
    }

    private String prepareUrlWithTags(String commaDelimitedTags) {
        String url;
        if (TextUtils.isEmpty(commaDelimitedTags)) {
            url = Constants.FLICKR_PUBLIC_PHOTOS_URL;
        } else {
            url = Constants.FLICKR_PUBLIC_PHOTOS_URL + "&tags=" + commaDelimitedTags;
        }

        return url;
    }
}
