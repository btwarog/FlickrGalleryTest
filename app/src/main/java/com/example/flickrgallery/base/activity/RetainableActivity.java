package com.example.flickrgallery.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.flickrgallery.base.BaseView;
import com.example.flickrgallery.base.presenter.RetainablePresenter;
import com.example.flickrgallery.base.presenter.RetainablePresenterFactory;

public abstract class RetainableActivity<T extends RetainablePresenter<V>, V extends BaseView> extends AppCompatActivity {

    RetainablePresenterFactory<T, V> presenterFactory;
    T presenter;

    protected void init(Bundle savedInstanceState, V view) {
        presenterFactory = providePresenterFactory();
        presenter = presenterFactory.getretainablePresenter(savedInstanceState, view);
    }

    public void onSaveInstanceState(Bundle outState) {
        presenterFactory.saveretainablePresenter(outState, presenter);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.releaseView();
        if (isFinishing()) {
            presenterFactory.destroyretainablePresenter(presenter);
        }
    }

    protected T getPresenter() {
        return presenter;
    }

    protected abstract RetainablePresenterFactory<T, V> providePresenterFactory();
}
