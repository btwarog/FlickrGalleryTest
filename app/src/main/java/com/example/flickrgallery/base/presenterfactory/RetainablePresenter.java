package com.example.flickrgallery.base.presenterfactory;

import com.example.flickrgallery.base.BaseView;

public abstract class RetainablePresenter<V extends BaseView> {
    long uniqueId;
    V view;

    protected void setId(long id) {
        this.uniqueId = id;
    }

    protected long getId() {
        return uniqueId;
    }

    public void setView(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

    protected abstract void start();

    protected abstract void restore();

    protected abstract void destroy();
}
