package com.example.flickrgallery.imageloader.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DataStreamProviderImpl implements DataStreamProvider {
    @Override
    public InputStream provideInputStream(String url) throws IOException {
        return new URL(url).openStream();
    }
}
