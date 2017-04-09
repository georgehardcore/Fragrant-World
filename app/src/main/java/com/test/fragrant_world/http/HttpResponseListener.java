package com.test.fragrant_world.http;

import com.test.fragrant_world.http.json.JSON;

public interface HttpResponseListener {

    /**
     *  Authorization event.
     *  @param jsonObject http response
     *  */
    void onAnswerReceived(JSON jsonObject);

    /**
     * On error callback.
     * @param error error message
     * @param code error code
     */
    void onError(String error, int code);
}
