package com.example.flickrgallery.screen.photosearch.presenter;

import android.graphics.Bitmap;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.base.presenter.BasePresenter;
import com.example.flickrgallery.imageloader.ImageLoader;
import com.example.flickrgallery.screen.photosearch.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.PhotoUrlProvider;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.view.PhotoItemView;

public class PhotoItemPresenter extends BasePresenter<PhotoItemView> {
    protected Photo item;
    private final ImageLoader imageLoader;
    private final PhotoUrlProvider photoUrlProvider;
    private final ThrowableTranslator throwableTranslator;
    private Cancelable cancelableLoadingPhoto;

    public PhotoItemPresenter(
            Photo item,
            ImageLoader imageLoader,
            PhotoUrlProvider photoUrlProvider,
            ThrowableTranslator throwableTranslator
    ) {
        this.item = item;
        this.imageLoader = imageLoader;
        this.photoUrlProvider = photoUrlProvider;
        this.throwableTranslator = throwableTranslator;
    }

    @Override
    public void start() {
        loadPhoto();
    }

    public void retry() {
        loadPhoto();
    }

    private void loadPhoto() {
        getView().showBitmap(null);
        getView().showFailed(false, null);
        getView().showLoading(true);
        String url = photoUrlProvider.getPhotoUrl(item);
        cancelableLoadingPhoto = imageLoader.loadImage(url, new ImageLoader.Callback() {
            @Override
            public void onLoaded(Bitmap bitmap) {
                getView().showBitmap(bitmap);
                getView().showLoading(false);
            }

            @Override
            public void onFailed(Throwable throwable) {
                String errorMessage = throwableTranslator.translateThrowable(throwable);
                getView().showFailed(true, errorMessage);
                getView().showLoading(false);
            }
        });
    }

    @Override
    public void destroy() {
        if(cancelableLoadingPhoto != null) {
            cancelableLoadingPhoto.cancel();
        }
    }
}
