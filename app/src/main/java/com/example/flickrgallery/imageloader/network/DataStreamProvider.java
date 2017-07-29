package com.example.flickrgallery.imageloader.network;

import java.io.IOException;
import java.io.InputStream;

public interface DataStreamProvider {
    InputStream provideInputStream(String url) throws IOException;
}
