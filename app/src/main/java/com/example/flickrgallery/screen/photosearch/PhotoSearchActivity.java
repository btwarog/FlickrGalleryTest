package com.example.flickrgallery.screen.photosearch;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.flickrgallery.R;
import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.base.activity.RetainableActivity;
import com.example.flickrgallery.base.presenterfactory.RetainablePresenterFactory;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosActionFake;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoSearchPresenter;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoSearchPresenterFactory;
import com.example.flickrgallery.util.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PhotoSearchActivity extends RetainableActivity<PhotoSearchPresenter, PhotoSearchView> implements PhotoSearchView {
    TextView error;
    TextView test;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_search);
        Binding.bind(this);

        init(savedInstanceState, this);
    }

    @Override
    public void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        if(show) {
            error.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        error.setVisibility(View.VISIBLE);
        error.setText(message);
    }

    @Override
    public void showPhotos(List<Photo> photoList) {
        test.setVisibility(View.VISIBLE);
        test.setText("PHOTOS");
    }

    @Override
    protected RetainablePresenterFactory<PhotoSearchPresenter, PhotoSearchView> providePresenterFactory() {
        return PhotoSearchPresenterFactory.getInstance(
                new LoadPhotosActionFake(),
                new ThrowableTranslator() {
                    @Override
                    public String translateThrowable(Throwable throwable) {
                        return "fake error";
                    }
                }
        );
    }
}
