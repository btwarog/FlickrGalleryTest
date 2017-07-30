package com.example.flickrgallery.imageloader.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class BitmapDecoderImpl implements BitmapDecoder {
    @Override
    public Bitmap decode(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }
}
