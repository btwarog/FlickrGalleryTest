package com.example.flickrgallery.screen.photosearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.flickrgallery.R;
import com.example.flickrgallery.base.activity.RetainableActivity;
import com.example.flickrgallery.base.presenterfactory.RetainablePresenterFactory;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoSearchPresenter;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoSearchPresenterFactory;
import com.example.flickrgallery.screen.photosearch.provider.Provider;
import com.example.flickrgallery.util.StringUtils;

import java.util.List;

public class PhotoSearchActivity extends RetainableActivity<PhotoSearchPresenter, PhotoSearchView> implements PhotoSearchView {
    TextView error;
    TextView test;
    ProgressBar progressBar;
    EditText search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_search);
        Binding.bind(this);

        init(savedInstanceState, this);

        initSearchByTags();
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
        }
    }

    @Override
    public void showError(String message) {
        error.setVisibility(View.VISIBLE);
        error.setText(message);
    }

    @Override
    public void showPhotos(List<Photo> photoList) {
        String photosStr = StringUtils.buildListWithSeparator(photoList, new StringUtils.StringMaker<Photo>() {
            @Override
            public String makeString(Photo item) {
                return item.getTitle();
            }
        });
        test.setVisibility(View.VISIBLE);
        test.setText(photosStr);
    }

    @Override
    protected RetainablePresenterFactory<PhotoSearchPresenter, PhotoSearchView> providePresenterFactory() {
        return PhotoSearchPresenterFactory.getInstance(
                Provider.provideLoadPhotosAction(),
                Provider.provideThrowableTranslator()
        );
    }
}