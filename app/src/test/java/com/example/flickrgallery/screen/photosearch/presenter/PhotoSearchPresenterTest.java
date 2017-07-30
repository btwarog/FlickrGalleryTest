package com.example.flickrgallery.screen.photosearch.presenter;

import com.example.flickrgallery.base.Cancelable;
import com.example.flickrgallery.screen.photosearch.view.PhotoSearchView;
import com.example.flickrgallery.screen.photosearch.action.ThrowableTranslator;
import com.example.flickrgallery.screen.photosearch.action.LoadPhotosAction;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PhotoSearchPresenterTest {
    @Test
    public void testStart() {
        final AtomicBoolean loadPhotosCalled = new AtomicBoolean(false);
        LoadPhotosAction loadPhotosAction = new LoadPhotosAction() {
            @Override
            public Cancelable loadPhotos(String tags, Callback callback) {
                loadPhotosCalled.set(true);
                return null;
            }
        };
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                loadPhotosAction,
                new ThrowableTranslator() {
                    @Override
                    public String translateThrowable(Throwable throwable) {
                        return "fake error";
                    }
                }
        );

        PhotoSearchViewFake photoSearchViewFake = new PhotoSearchViewFake();
        presenter.setView(photoSearchViewFake);

        presenter.start();

        Assert.assertTrue(loadPhotosCalled.get());
        Assert.assertTrue(photoSearchViewFake.showLoadingCalled.get());
    }

    @Test
    public void testOnLoaded() {
        final AtomicBoolean loadPhotosCalled = new AtomicBoolean(false);
        LoadPhotosAction loadPhotosAction = new LoadPhotosAction() {
            @Override
            public Cancelable loadPhotos(String tags, Callback callback) {
                loadPhotosCalled.set(true);
                callback.onLoaded(new ArrayList<Photo>());
                return null;
            }
        };
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                loadPhotosAction,
                new ThrowableTranslator() {
                    @Override
                    public String translateThrowable(Throwable throwable) {
                        return "fake error";
                    }
                }
        );

        PhotoSearchViewFake photoSearchViewFake = new PhotoSearchViewFake();
        presenter.setView(photoSearchViewFake);

        presenter.start();

        Assert.assertTrue(loadPhotosCalled.get());
        Assert.assertTrue(photoSearchViewFake.showLoadingCalled.get());
        Assert.assertTrue(photoSearchViewFake.showPhotosCalled.get());
    }


    @Test
    public void testOnFailed() {
        final AtomicBoolean loadPhotosCalled = new AtomicBoolean(false);
        LoadPhotosAction loadPhotosAction = new LoadPhotosAction() {
            @Override
            public Cancelable loadPhotos(String tags, Callback callback) {
                loadPhotosCalled.set(true);
                callback.onFailed(new RuntimeException("fake"));
                return null;
            }
        };
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                loadPhotosAction,
                new ThrowableTranslator() {
                    @Override
                    public String translateThrowable(Throwable throwable) {
                        return "fake error";
                    }
                }
        );

        PhotoSearchViewFake photoSearchViewFake = new PhotoSearchViewFake();
        presenter.setView(photoSearchViewFake);

        presenter.start();

        Assert.assertTrue(loadPhotosCalled.get());
        Assert.assertTrue(photoSearchViewFake.showLoadingCalled.get());
        Assert.assertTrue(photoSearchViewFake.showErrorCalled.get());
    }

    @Test
    public void testCancelAfterDestroy() {
        final AtomicBoolean loadPhotosCalled = new AtomicBoolean(false);
        final AtomicBoolean isCancelableCanceled = new AtomicBoolean(false);

        LoadPhotosAction loadPhotosAction = new LoadPhotosAction() {
            Callback callback;
            @Override
            public Cancelable loadPhotos(String tags, Callback callback) {
                loadPhotosCalled.set(true);
                this.callback = callback;

                return new Cancelable() {
                    @Override
                    public boolean isCanceled() {
                        return isCancelableCanceled.get();
                    }

                    @Override
                    public void cancel() {
                        isCancelableCanceled.set(true);
                    }
                };
            }
        };
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                loadPhotosAction,
                new ThrowableTranslator() {
                    @Override
                    public String translateThrowable(Throwable throwable) {
                        return "fake error";
                    }
                }
        );

        PhotoSearchViewFake photoSearchViewFake = new PhotoSearchViewFake();
        presenter.setView(photoSearchViewFake);

        presenter.start();

        Assert.assertTrue(loadPhotosCalled.get());
        Assert.assertTrue(photoSearchViewFake.showLoadingCalled.get());

        presenter.destroy();
        Assert.assertTrue(isCancelableCanceled.get());
    }

    @Test
    public void testRestoreLoading() {
        final AtomicBoolean loadPhotosCalled = new AtomicBoolean(false);

        LoadPhotosAction loadPhotosAction = new LoadPhotosAction() {
            Callback callback;
            @Override
            public Cancelable loadPhotos(String tags, Callback callback) {
                loadPhotosCalled.set(true);
                return null;
            }
        };
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                loadPhotosAction,
                null
        );
        PhotoSearchViewFake photoSearchViewFake = new PhotoSearchViewFake();
        presenter.setView(photoSearchViewFake);
        presenter.state = new PhotoSearchPresenter.State();
        presenter.state.isLoading = true;

        presenter.restore();

        Assert.assertTrue(loadPhotosCalled.get());
        Assert.assertTrue(photoSearchViewFake.showLoadingCalled.get());
    }

    @Test
    public void testRestoreShowError() {
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                null,
                null
        );
        PhotoSearchViewFake photoSearchViewFake = new PhotoSearchViewFake();
        presenter.setView(photoSearchViewFake);
        presenter.state = new PhotoSearchPresenter.State();
        presenter.state.isLoading = false;
        String fakeMessage = "fake message";
        presenter.state.errorMessage = fakeMessage;

        presenter.restore();

        Assert.assertTrue(photoSearchViewFake.showErrorCalled.get());
        Assert.assertEquals(photoSearchViewFake.errorMessage, fakeMessage);
    }

    @Test
    public void testRestoreShowLoadedPhotos() {
        PhotoSearchPresenter presenter = new PhotoSearchPresenter(
                null,
                null
        );
        PhotoSearchViewFake photoSearchViewFake = new PhotoSearchViewFake();
        presenter.setView(photoSearchViewFake);
        presenter.state = new PhotoSearchPresenter.State();
        presenter.state.isLoading = false;
        List<Photo> loadedPhotosTest = new ArrayList<>();
        presenter.state.loadedPhotos = loadedPhotosTest;

        presenter.restore();

        Assert.assertTrue(photoSearchViewFake.showPhotosCalled.get());
        Assert.assertEquals(photoSearchViewFake.photoList, loadedPhotosTest);
    }


    public class PhotoSearchViewFake implements PhotoSearchView {
        public AtomicBoolean showLoadingCalled = new AtomicBoolean(false);
        public AtomicBoolean showErrorCalled = new AtomicBoolean(false);
        public AtomicBoolean showPhotosCalled  = new AtomicBoolean(false);
        public String errorMessage;
        public List<Photo> photoList;

        @Override
        public void showLoading(boolean show) {
            showLoadingCalled.set(true);
        }

        @Override
        public void showError(String message) {
            errorMessage = message;
            showErrorCalled.set(true);
        }

        @Override
        public void showPhotos(List<Photo> photoList) {
            showPhotosCalled.set(true);
            this.photoList = photoList;
        }

        @Override
        public void showFailedOpenGallery() {

        }
    }
}
