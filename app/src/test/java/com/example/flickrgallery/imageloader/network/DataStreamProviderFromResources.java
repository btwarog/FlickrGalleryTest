package com.example.flickrgallery.imageloader.network;

import java.io.InputStream;

public class DataStreamProviderFromResources implements DataStreamProvider {

    @Override
    public InputStream provideInputStream(String url) {
        return this.getClass().getClassLoader().getResourceAsStream("test.jpg");
    }
}
