package com.example.flickrgallery.base.presenter;

import com.example.flickrgallery.base.BaseView;

public abstract class RetainablePresenter<V extends BaseView> extends BasePresenter<V> {
    long uniqueId;

    protected void setId(long id) {
        this.uniqueId = id;
    }

    protected long getId() {
        return uniqueId;
    }

    protected abstract void restore();
}
