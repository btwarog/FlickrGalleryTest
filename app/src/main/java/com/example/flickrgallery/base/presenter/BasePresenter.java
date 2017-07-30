package com.example.flickrgallery.base.presenter;

import com.example.flickrgallery.base.BaseView;

public abstract class BasePresenter<V extends BaseView> {
    V view;

    public void setView(V view) {
        this.view = view;
    }

    public abstract void start();

    public abstract void destroy();

    protected V getView() {
        return view;
    }
}