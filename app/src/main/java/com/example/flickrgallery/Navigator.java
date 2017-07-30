package com.example.flickrgallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.flickrgallery.screen.photosearch.action.PhotoUrlProvider;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.util.IntentUtils;

public class Navigator {
    private final Context context;
    private final PhotoUrlProvider photoUrlProvider;

    public Navigator (
            Context context,
            PhotoUrlProvider photoUrlProvider
    ) {
        this.context = context;
        this.photoUrlProvider = photoUrlProvider;
    }

    public boolean openInGallery(Photo photo) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        String url = photoUrlProvider.getPhotoUrl(photo, PhotoUrlProvider.Size.Gallery);
        Uri photoUri = Uri.parse(url);
        intent.setDataAndType(photoUri, "image/*");

        if (IntentUtils.isCallable(context, intent)) {
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }
}
