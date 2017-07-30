package com.example.flickrgallery.imageloader.impl;

import com.example.flickrgallery.network.InputStreamProvider;

import java.io.IOException;
import java.io.InputStream;

public class DataStreamProviderImpl implements DataStreamProvider {
    private final InputStreamProvider provider;

    public DataStreamProviderImpl(InputStreamProvider provider) {
        this.provider = provider;
    }

    @Override
    public InputStream provideInputStream(String url) throws IOException {
        return provider.provideInputStreamForUrl(url);
    }
}
