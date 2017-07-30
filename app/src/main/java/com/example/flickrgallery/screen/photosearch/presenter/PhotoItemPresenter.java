package com.example.flickrgallery.screen.photosearch.presenter;

import android.graphics.Bitmap;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.base.presenter.BasePresenter;
import com.example.flickrgallery.imageloader.ImageLoader;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotoMetadataAction;
import com.example.flickrgallery.screen.photosearch.action.PhotoUrlProvider;
import com.example.flickrgallery.screen.photosearch.action.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.model.PhotoMeadata;
import com.example.flickrgallery.screen.photosearch.view.PhotoItemView;

import java.util.concurrent.atomic.AtomicInteger;

public class PhotoItemPresenter extends BasePresenter<PhotoItemView> {
    private static final int ACTIONS_COUNT = 2;

    protected Photo photoItem;

    private final ImageLoader imageLoader;
    private final PhotoUrlProvider photoUrlProvider;
    private final ThrowableTranslator throwableTranslator;
    private final LoadPhotoMetadataAction loadPhotoMetadataAction;

    private Cancelable cancelableLoadingPhoto;
    private Cancelable cancelableLoadPhotoMetadata;
    private Bitmap loadedBitmap;
    private PhotoMeadata loadedPhotoMetadata;
    private AtomicInteger lockingInt = new AtomicInteger(0);

    public PhotoItemPresenter(
            Photo item,
            ImageLoader imageLoader,
            PhotoUrlProvider photoUrlProvider,
            ThrowableTranslator throwableTranslator,
            LoadPhotoMetadataAction loadPhotoMetadataAction
    ) {
        this.photoItem = item;
        this.imageLoader = imageLoader;
        this.photoUrlProvider = photoUrlProvider;
        this.throwableTranslator = throwableTranslator;
        this.loadPhotoMetadataAction = loadPhotoMetadataAction;
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
        getView().showPhotoMetadata(null);
        getView().showFailed(false, null);
        getView().showLoading(true);
        String url = photoUrlProvider.getPhotoUrl(photoItem, PhotoUrlProvider.Size.Thumb);

        lockingInt.set(0);

        cancelableLoadPhotoMetadata = loadPhotoMetadataAction.loadPhotoMetadata(photoItem, new LoadPhotoMetadataAction.Callback() {
            @Override
            public void onLoaded(PhotoMeadata photoMeadata) {
                loadedPhotoMetadata = photoMeadata;
                checkAndShowLoaded();
            }

            @Override
            public void onFailed(Throwable throwable) {
                if(cancelableLoadingPhoto != null) {
                    cancelableLoadingPhoto.cancel();
                }
                showError(throwable);
            }
        });
        cancelableLoadingPhoto = imageLoader.loadImage(url, new ImageLoader.Callback() {
            @Override
            public void onLoaded(Bitmap bitmap) {
                loadedBitmap = bitmap;
                checkAndShowLoaded();
            }

            @Override
            public void onFailed(Throwable throwable) {
                if(cancelableLoadPhotoMetadata != null) {
                    cancelableLoadPhotoMetadata.cancel();
                }
                showError(throwable);
            }
        });
    }

    private void checkAndShowLoaded() {
        if(lockingInt.incrementAndGet() == ACTIONS_COUNT) {
            showLoaded();
        }
    }

    private void showError(Throwable throwable) {
        String errorMessage = throwableTranslator.translateThrowable(throwable);
        getView().showFailed(true, errorMessage);
        getView().showLoading(false);
    }

    private void showLoaded() {
        getView().showBitmap(loadedBitmap);
        getView().showPhotoMetadata(loadedPhotoMetadata);
        getView().showLoading(false);
    }

    @Override
    public void destroy() {
        if(cancelableLoadingPhoto != null) {
            cancelableLoadingPhoto.cancel();
        }
        if(cancelableLoadPhotoMetadata != null) {
            cancelableLoadPhotoMetadata.cancel();
        }
    }
}
