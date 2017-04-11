package com.test.fragrant_world.view;


interface LoadingView {

    void hideLoading();

    void showLayout();

    void showLoading();

    void onError(String error, int code);
}
