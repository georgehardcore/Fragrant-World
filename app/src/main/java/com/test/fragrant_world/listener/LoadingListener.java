package com.test.fragrant_world.listener;


import com.test.fragrant_world.http.json.JSON;

public interface LoadingListener {

    void showLoading();

    void hideLoading();

    void onAnswerReceived(JSON json);

    void onError(String error, int code);
}
