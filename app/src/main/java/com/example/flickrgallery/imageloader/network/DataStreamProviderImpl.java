package com.example.flickrgallery.imageloader.network;

import com.example.flickrgallery.network.InputStreamProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DataStreamProviderImpl implements DataStreamProvider {
    InputStreamProvider provider = new InputStreamProvider();
    @Override
    public InputStream provideInputStream(String url) throws IOException {
        return provider.provideInputStreamForUrl(url);
    }
}
