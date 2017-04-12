package com.test.fragrant_world.presenter;

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
        if (checkState()) {
            updateView();
        }
    }

    public void bindView(V view) {
        this.view = new WeakReference<>(view);
        if (checkState()) {
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

    protected boolean checkState() {
        return view() != null && model != null;
    }
}