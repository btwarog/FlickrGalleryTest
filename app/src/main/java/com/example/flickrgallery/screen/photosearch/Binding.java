package com.example.flickrgallery.screen.photosearch;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.flickrgallery.R;

public class Binding {
    static void bind(PhotoSearchActivity photoSearchActivity) {
        photoSearchActivity.error = (TextView) photoSearchActivity.findViewById(R.id.error);
        photoSearchActivity.test = (TextView) photoSearchActivity.findViewById(R.id.test);
        photoSearchActivity.progressBar = (ProgressBar) photoSearchActivity.findViewById(R.id.progress);
        photoSearchActivity.search = (EditText) photoSearchActivity.findViewById(R.id.search);
    }
}
