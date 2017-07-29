package com.example.flickrgallery.screen.photosearch.action;

import java.io.IOException;
import java.io.InputStream;

public interface DataStreamProvider<T> {
    InputStream provideDataInputStream(T descriptor) throws IOException;
}
