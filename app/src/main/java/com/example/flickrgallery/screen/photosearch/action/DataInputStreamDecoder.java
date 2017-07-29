package com.example.flickrgallery.screen.photosearch.action;

import java.io.InputStream;

public interface DataInputStreamDecoder<T> {
    T decode(InputStream inputStream);
}
