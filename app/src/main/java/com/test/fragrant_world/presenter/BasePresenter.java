package com.test.fragrant_world.presenter;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Base presenter class.
 * @param <M> model generic type
 * @param <V> view generic type
 */
public abstract class BasePresenter<M, V> {

    protected M model;

    private WeakReference<V> view;

    public void setModel(M model) {
        this.model = model;
        if (check()) {
            updateView();
        }
    }

    public void bindView(@NonNull V view) {
        this.view = new WeakReference<>(view);
        if (check()) {
            updateView();
        }
    }

    public void unbindView() {
        this.view = null;
    }

    protected V view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    protected abstract void updateView();

    protected boolean check() {
        return view() != null && model != null;
    }
}