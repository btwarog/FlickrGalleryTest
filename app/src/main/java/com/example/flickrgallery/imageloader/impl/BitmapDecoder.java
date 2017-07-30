package com.example.flickrgallery.imageloader.impl;

import android.graphics.Bitmap;

import java.io.InputStream;

public interface BitmapDecoder {
    Bitmap decode(InputStream inputStream);
}
