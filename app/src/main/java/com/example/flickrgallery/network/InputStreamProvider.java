package com.example.flickrgallery.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class InputStreamProvider {
    public InputStream provideInputStreamForUrl(String url) throws IOException {
        return new URL(url).openStream();
    }
}
