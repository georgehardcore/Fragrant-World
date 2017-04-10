package com.test.fragrant_world.http;


import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.listener.LoadingListener;

import org.apache.http.HttpEntity;

public class Request implements HttpRequestListener {

    private boolean loading;

    private HttpEntity post;

    private String httpRequest;

    private LoadingListener listener;

    private Request(Builder builder) {
        listener = builder.listener;
        post = builder.post;
        httpRequest = builder.httpRequest;
    }

    @Override
    public void execute() {
        new Query(this, post).execute(httpRequest);
    }

    @Override
    public void onRequestStart() {
        listener.showLoading();
        loading = true;
    }

    @Override
    public void onRequestEnd() {
        listener.hideLoading();
        loading = false;
    }

    @Override
    public void onConnectionError() {
        loading = false;
        App.showToast(R.string.error_1);
    }

    @Override
    public void onAnswerReceived(JSON json) {
        if (listener != null) listener.onAnswerReceived(json);
        loading = false;
    }

    public boolean isLoading() {
        return loading;
    }

    @Override
    public void onError(String error, int code) {
        if (listener != null) listener.onError(error, code);
        if (error != null) App.showToast(error);
    }

    /** Instance builder class. */
    public static class Builder {

        private HttpEntity post;

        private String httpRequest;

        private LoadingListener listener;

        public Builder postParams(HttpEntity post) {
            this.post = post;
            return this;
        }

        public Builder httpRequest(String httpRequest) {
            this.httpRequest = httpRequest;
            return this;
        }

        public Builder listener(LoadingListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * Build container class instance.
         * @return container class instance
         */
        public Request build() {
            return new Request(this);
        }
    }
}
