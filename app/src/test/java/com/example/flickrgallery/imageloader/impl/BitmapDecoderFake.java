package com.example.flickrgallery.imageloader.impl;

import android.graphics.Bitmap;

import java.io.InputStream;

public class BitmapDecoderFake implements BitmapDecoder {
    Bitmap returnedValue;

    public BitmapDecoderFake(Bitmap returnedValue) {
        this.returnedValue = returnedValue;
    }

    @Override
    public Bitmap decode(InputStream inputStream) {
        return returnedValue;
    }
}
