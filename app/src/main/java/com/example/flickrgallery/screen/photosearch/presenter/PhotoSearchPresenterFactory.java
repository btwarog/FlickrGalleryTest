package com.example.flickrgallery.screen.photosearch.presenter;

import android.os.Bundle;

import com.example.flickrgallery.base.presenterfactory.RetainablePresenterFactory;
import com.example.flickrgallery.screen.photosearch.PhotoSearchView;
import com.example.flickrgallery.screen.photosearch.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.util.SerializableArrayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        if(bundle.containsKey("loadedPhotos")) {
            presenter.state.loadedPhotos = (List<Photo>) bundle.getSerializable("loadedPhotos");
        }
        return presenter;
    }

    @Override
    protected Bundle writeretainablePresenterToBundle(PhotoSearchPresenter presenter) {
        Bundle bundle = new Bundle();
        bundle.putString("errorMessage", presenter.state.errorMessage);
        bundle.putString("tags", presenter.state.tags);
        bundle.putBoolean("isLoading", presenter.state.isLoading);
        if(presenter.state.loadedPhotos != null) {
            Serializable serializable = SerializableArrayUtil.asSerializableArrayList(presenter.state.loadedPhotos);
            bundle.putSerializable("loadedPhotos", serializable);
        }

        return bundle;
    }
}
