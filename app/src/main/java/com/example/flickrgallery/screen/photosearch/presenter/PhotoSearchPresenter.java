package com.example.flickrgallery.screen.photosearch.presenter;

import com.example.flickrgallery.base.presenter.RetainablePresenter;
import com.example.flickrgallery.screen.photosearch.view.PhotoSearchView;
import com.example.flickrgallery.screen.photosearch.action.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import java.util.List;

public class PhotoSearchPresenter extends RetainablePresenter<PhotoSearchView> {
    private final LoadPhotosAction loadPhotosAction;
    private final ThrowableTranslator throwableTranslator;
    protected State state = new State();

    private Cancelable loadPhotosCancelable;

    public PhotoSearchPresenter (
            LoadPhotosAction loadPhotosAction,
            ThrowableTranslator throwableTranslator) {
        this.loadPhotosAction = loadPhotosAction;
        this.throwableTranslator = throwableTranslator;
    }

    public void start() {
        startLoading(null);
    }

    public void restore() {
        if(state.isLoading == true) {
            startLoading(state.tags);
        } else if(state.loadedPhotos != null) {
            getView().showPhotos(state.loadedPhotos);
        } else if(state.errorMessage != null) {
            getView().showError(state.errorMessage);
        } else {
            throw new RuntimeException("unreachable");
        }
    }

    public void destroy() {
        if(loadPhotosCancelable != null) {
            loadPhotosCancelable.cancel();
        }
    }

    public void searchForTags(String tags) {
        if(loadPhotosCancelable != null) {
            loadPhotosCancelable.cancel();
        }
        startLoading(tags);
    }

    private void startLoading(String tags) {
        getView().showLoading(true);
        state.isLoading = true;
        state.tags = tags;

        loadPhotosCancelable = loadPhotosAction.loadPhotos(tags, new LoadPhotosAction.Callback() {
            @Override
            public void onLoaded(List<Photo> photos) {
                getView().showLoading(false);
                getView().showPhotos(photos);
                state.isLoading = false;
                state.loadedPhotos = photos;
            }

            @Override
            public void onFailed(Throwable throwable) {
                getView().showLoading(false);
                state.isLoading = false;

                String message = throwableTranslator.translateThrowable(throwable);
                state.errorMessage = message;
                getView().showError(message);
            }
        });
    }

    public void retry() {
        startLoading(state.tags);
    }

    static class State {
        boolean isLoading = false;
        public String tags;
        List<Photo> loadedPhotos;
        public String errorMessage;
    }
}
