package com.example.flickrgallery.screen.photosearch;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.flickrgallery.R;

public class Binding {
    static void bind(PhotoSearchActivity photoSearchActivity) {
        photoSearchActivity.error = (TextView) photoSearchActivity.findViewById(R.id.error);
        photoSearchActivity.retry = (Button) photoSearchActivity.findViewById(R.id.retry);
        photoSearchActivity.photos = (RecyclerView) photoSearchActivity.findViewById(R.id.photos);
        photoSearchActivity.progressBar = (ProgressBar) photoSearchActivity.findViewById(R.id.progress);
        photoSearchActivity.search = (EditText) photoSearchActivity.findViewById(R.id.search);
    }
}
