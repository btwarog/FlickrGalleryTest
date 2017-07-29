package com.example.flickrgallery.screen.photosearch.presenter;

import android.os.Bundle;

import com.example.flickrgallery.base.presenterfactory.RetainablePresenterFactory;
import com.example.flickrgallery.screen.photosearch.PhotoSearchView;
import com.example.flickrgallery.screen.photosearch.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import java.util.ArrayList;

public class PhotoSearchPresenterFactory extends RetainablePresenterFactory<PhotoSearchPresenter, PhotoSearchView> {

    private LoadPhotosAction loadPhotosAction;
    private ThrowableTranslator throwableTranslator;

    protected PhotoSearchPresenterFactory(
            String factoryName,
            LoadPhotosAction loadPhotosAction,
            ThrowableTranslator throwableTranslator) {
        super(factoryName);
        this.loadPhotosAction = loadPhotosAction;
        this.throwableTranslator = throwableTranslator;
    }

    static PhotoSearchPresenterFactory instance = null;

    public static PhotoSearchPresenterFactory getInstance(
            LoadPhotosAction loadPhotosAction,
            ThrowableTranslator throwableTranslator
    ) {
        if (instance == null) {
            instance = new PhotoSearchPresenterFactory(
                    "PhotoSearchPresenterFactory",
                    loadPhotosAction,
                    throwableTranslator
            );
        }

        return instance;
    }

    @Override
    protected PhotoSearchPresenter create() {
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                loadPhotosAction,
                throwableTranslator
        );
        return presenter;
    }

    @Override
    protected PhotoSearchPresenter loadretainablePresenterFromBundle(Bundle bundle) {
        PhotoSearchPresenter presenter = create();

        presenter.state.errorMessage = bundle.getString("errorMessage", null);
        presenter.state.tags = bundle.getString("tags", null);
        presenter.state.isLoading = bundle.getBoolean("isLoading", false);
        presenter.state.loadedPhotos = bundle.getBoolean("loadedPhotos", false) ? new ArrayList<Photo>() : null;
        return presenter;
    }

    @Override
    protected Bundle writeretainablePresenterToBundle(PhotoSearchPresenter presenter) {
        Bundle bundle = new Bundle();
        bundle.putString("errorMessage", presenter.state.errorMessage);
        bundle.putString("tags", presenter.state.tags);
        bundle.putBoolean("isLoading", presenter.state.isLoading);
        bundle.putBoolean("loadedPhotos", presenter.state.loadedPhotos != null);
        return bundle;
    }
}
