package com.example.flickrgallery.base.presenter;

import android.os.Bundle;

import com.example.flickrgallery.base.BaseView;
import com.example.flickrgallery.util.ThreadUtils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class RetainablePresenterFactory<T extends RetainablePresenter<V>, V extends BaseView> {

    private final String KEY_BUNDLE;
    private final String KEY_OBJECT_ID;

    private HashMap<Long, T> availableObjects = new HashMap<>();

    protected RetainablePresenterFactory(String factoryName) {
        KEY_BUNDLE = "KEY_" + factoryName;
        KEY_OBJECT_ID = "KEY_" + factoryName + "_ID";
    }

    public final T getretainablePresenter(Bundle savedInstanceState, V view) {
        if (savedInstanceState == null) {
            T retainablePresenter = create();
            long uniqueId = getNextUniqueId();
            availableObjects.put(uniqueId, retainablePresenter);
            retainablePresenter.setId(uniqueId);
            retainablePresenter.setView(view);
            retainablePresenter.start();
            return retainablePresenter;
        } else {
            Bundle objectBundle = savedInstanceState.getBundle(KEY_BUNDLE);
            if (objectBundle != null) {
                objectBundle.setClassLoader(getClass().getClassLoader());
                long objectId = objectBundle.getLong(KEY_OBJECT_ID);
                if (availableObjects.containsKey(objectId)) {
                    T retainablePresenter = availableObjects.get(objectId);
                    retainablePresenter.setView(view);
                    retainablePresenter.restore();
                    return retainablePresenter;
                }

                T retainablePresenter = loadretainablePresenterFromBundle(objectBundle);
                retainablePresenter.setId(objectId);
                availableObjects.put(objectId, retainablePresenter);
                retainablePresenter.setView(view);
                retainablePresenter.restore();
                return retainablePresenter;
            } else {
                return null;
            }
        }
    }

    private static AtomicLong lastUniqueId = new AtomicLong(0);
    public static long getNextUniqueId() {
        synchronized (lastUniqueId) {
            long generated = System.currentTimeMillis();
            if (generated == lastUniqueId.get()) {
                ThreadUtils.sleep(1);
                return getNextUniqueId();
            } else {
                lastUniqueId.set(generated);
                return generated;
            }
        }
    }

    protected abstract T create();

    public final void destroyretainablePresenter(T presenter) {
        availableObjects.remove(presenter.getId());
    }

    public final void saveretainablePresenter(Bundle savedInstanceState, T retainablePresenter) {
        Bundle objectBundle = writeretainablePresenterToBundle(retainablePresenter);

        long objectId = retainablePresenter.getId();
        objectBundle.putLong(KEY_OBJECT_ID, objectId);

        savedInstanceState.putBundle(KEY_BUNDLE, objectBundle);
    }

    protected abstract T loadretainablePresenterFromBundle(Bundle bundle);

    protected abstract Bundle writeretainablePresenterToBundle(T bundle);
}