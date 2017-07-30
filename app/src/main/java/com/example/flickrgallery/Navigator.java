package com.example.flickrgallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.util.IntentUtils;

public class Navigator {
    private final Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    public boolean openInGallery(Photo photo) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        Uri photoUri = Uri.parse(photo.getMedia());
        intent.setDataAndType(photoUri, "image/*");

        if (IntentUtils.isCallable(context, intent)) {
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }
}
