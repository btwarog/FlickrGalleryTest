package com.example.flickrgallery.imageloader.network;

import android.graphics.Bitmap;

import java.io.InputStream;

public interface BitmapDecoder {
    Bitmap decode(InputStream inputStream);
}
