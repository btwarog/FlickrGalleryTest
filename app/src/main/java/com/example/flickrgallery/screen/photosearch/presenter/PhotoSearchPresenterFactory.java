package com.example.flickrgallery.screen.photosearch.presenter;

import android.os.Bundle;

import com.example.flickrgallery.base.presenter.RetainablePresenterFactory;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.action.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.view.PhotoSearchView;
import com.example.flickrgallery.util.SerializableArrayUtil;

import java.io.Serializable;
import java.util.List;

public class PhotoSearchPresenterFactory extends RetainablePresenterFactory<PhotoSearchPresenter, PhotoSearchView> {

    static PhotoSearchPresenterFactory instance = null;

    private final LoadPhotosAction loadPhotosAction;
    private final ThrowableTranslator throwableTranslator;

    private PhotoSearchPresenterFactory(
            String factoryName,
            LoadPhotosAction loadPhotosAction,
            ThrowableTranslator throwableTranslator
    ) {
        super(factoryName);
        this.loadPhotosAction = loadPhotosAction;
        this.throwableTranslator = throwableTranslator;
    }


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
        return new PhotoSearchPresenter(
                loadPhotosAction,
                throwableTranslator
        );
    }

    @Override
    protected PhotoSearchPresenter loadretainablePresenterFromBundle(Bundle bundle) {
        PhotoSearchPresenter presenter = create();

        presenter.state.errorMessage = bundle.getString("errorMessage", null);
        presenter.state.tags = bundle.getString("tags", null);
        presenter.state.isLoading = bundle.getBoolean("isLoading", false);
        if (bundle.containsKey("loadedPhotos")) {
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
        if (presenter.state.loadedPhotos != null) {
            Serializable serializable = SerializableArrayUtil.asSerializableArrayList(presenter.state.loadedPhotos);
            bundle.putSerializable("loadedPhotos", serializable);
        }

        return bundle;
    }
}
