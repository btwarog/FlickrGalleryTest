package com.example.flickrgallery.screen.photosearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.flickrgallery.R;
import com.example.flickrgallery.base.activity.RetainableActivity;
import com.example.flickrgallery.base.presenter.RetainablePresenterFactory;
import com.example.flickrgallery.screen.photosearch.adapter.PhotosListAdapter;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoSearchPresenter;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoSearchPresenterFactory;
import com.example.flickrgallery.injection.Injector;
import com.example.flickrgallery.screen.photosearch.view.PhotoSearchView;
import com.example.flickrgallery.util.StringUtils;

import java.util.List;

public class PhotoSearchActivity extends RetainableActivity<PhotoSearchPresenter, PhotoSearchView> implements PhotoSearchView {
    TextView error;
    Button retry;
    RecyclerView photos;
    ProgressBar progressBar;
    EditText search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_search);
        Binding.bind(this);

        init(savedInstanceState, this);

        initSearchByTags();
        initRetryButton();
    }

    private void initRetryButton() {
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().retry();
            }
        });
    }

    private void initSearchByTags() {
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId , KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(textView.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void performSearch(String searchInput) {
        String[] splits = StringUtils.splitBySpace(searchInput);
        String commaSeparatedTags = StringUtils.joinWithDelimiter(splits, ",");
        getPresenter().searchForTags(commaSeparatedTags);
    }

    @Override
    public void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        if(show) {
            error.setVisibility(View.GONE);
            retry.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        error.setVisibility(View.VISIBLE);
        error.setText(message);
        retry.setVisibility(View.VISIBLE);
        photos.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPhotos(List<Photo> photoList) {
        photos.setVisibility(View.VISIBLE);
        photos.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        photos.setLayoutManager(linearLayoutManager);

        PhotosListAdapter adapter = new PhotosListAdapter(photoList);
        photos.setAdapter(adapter);
    }

    @Override
    protected RetainablePresenterFactory<PhotoSearchPresenter, PhotoSearchView> providePresenterFactory() {
        return PhotoSearchPresenterFactory.getInstance(
                Injector.provideLoadPhotosAction(),
                Injector.provideThrowableTranslator()
        );
    }
}