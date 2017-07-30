package com.example.flickrgallery.imageloader.impl;

import com.example.flickrgallery.network.InputStreamProvider;

import java.io.IOException;
import java.io.InputStream;

public class DataStreamProviderImpl implements DataStreamProvider {
    InputStreamProvider provider = new InputStreamProvider();
    @Override
    public InputStream provideInputStream(String url) throws IOException {
        return provider.provideInputStreamForUrl(url);
    }
}
