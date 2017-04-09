package com.test.fragrant_world.http;


import com.test.fragrant_world.http.json.JSON;

import org.json.JSONException;
import org.json.JSONObject;

/** Http response listener. */
public interface HttpRequestListener {

    void execute();

    void onRequestStart();

    void onRequestEnd();

    void onConnectionError();

    /**
     *  Authorization event.
     *  @param json http response
     *  */
    void onAnswerReceived(JSON json);

    /**
     * On error callback.
     * @param error error message
     * @param code error code
     */
    void onError(String error, int code);

}
