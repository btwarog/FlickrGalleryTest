package com.example.flickrgallery.imageloader.impl;

import java.io.IOException;
import java.io.InputStream;

public interface DataStreamProvider {
    InputStream provideInputStream(String url) throws IOException;
}
