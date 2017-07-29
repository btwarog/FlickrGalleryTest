package com.example.flickrgallery.screen.photosearch.action;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public interface DataInputStreamDecoder<T> {
    T decode(InputStream inputStream) throws IOException, JSONException;
}
