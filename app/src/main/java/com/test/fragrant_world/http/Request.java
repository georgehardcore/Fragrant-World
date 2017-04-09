package com.test.fragrant_world.http;


import android.view.View;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.http.json.JSON;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class Request implements HttpRequestListener, View.OnClickListener {

    private View view;

    private HttpEntity post;

    private String httpRequest;

    private HttpResponseListener listener;

    private Request(Builder builder) {
        view = builder.view;
        listener = builder.listener;
        view.findViewById(R.id.retry_button).setOnClickListener(this);
        post = builder.post;
        httpRequest = builder.httpRequest;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.retry_button) execute();
    }

    @Override
    public void execute() {
        new Query(this, post).execute(httpRequest);
    }

    @Override
    public void onRequestStart() {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestEnd() {
        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void onConnectionError() {
        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        view.findViewById(R.id.error_view).setVisibility(View.VISIBLE);
        App.showToast(R.string.error_1);
    }

    @Override
    public void onAnswerReceived(JSON json) {
        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        view.findViewById(R.id.error_view).setVisibility(View.GONE);
        if (listener != null) listener.onAnswerReceived(json);
    }

    @Override
    public void onError(String error, int code) {
        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        view.findViewById(R.id.error_view).setVisibility(View.VISIBLE);
        if (listener != null) listener.onError(error, code);
        if (error != null) App.showToast(error);
    }



    /** Instance builder class. */
    public static class Builder {

        private View view;

        private HttpEntity post;

        private String httpRequest;

        private HttpResponseListener listener;

        public Builder view(View progressView) {
            this.view = progressView;
            return this;
        }

        public Builder postParams(HttpEntity post) {
            this.post = post;
            return this;
        }

        public Builder httpRequest(String httpRequest) {
            this.httpRequest = httpRequest;
            return this;
        }

        public Builder listener(HttpResponseListener listener) {
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
