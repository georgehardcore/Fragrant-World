package com.test.fragrant_world.http;

import android.os.AsyncTask;

import com.test.fragrant_world.App;
import com.test.fragrant_world.http.json.JSON;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;


/** Multi part data query. */
@SuppressWarnings("PMD")
public class Query extends AsyncTask<String, Void, String> {

    /** Http response listener. */
    private final HttpRequestListener listener;
    /** Query json post. */
    private HttpEntity post;
    /** Error. */
    private static final String ERROR = "error";
    /** Message. */
    private static final String MESSAGE = "message";
    /** Code. */
    private static final String CODE = "code";
    /** Unexpected error. */
    private static final String UNEXPECTED_ERROR = "unexpected api error";
    /** Is server unavailable. */
    protected boolean isUnavailable;
    /** Response code. */
    protected int responseCode = 0;

    /**
     * Constructor with parameters.
     * @param listener listener
     */
    public Query(HttpRequestListener listener, HttpEntity post) {
        this.listener = listener;
        this.post = post;
    }

    @Override //SUPPRESS CHECKSTYLE Method Length
    public void onPreExecute() { //SUPPRESS CHECKSTYLE Method Length
        super.onPreExecute();
        listener.onRequestStart();
        if (!App.isConnectionAvailable()) {
            listener.onConnectionError();
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(14000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if (post == null) {
                connection.setRequestMethod("GET");
            } else {
                setupPost(connection);
            }
            connection.connect();
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readStream(connection.getInputStream());
            }
            connection.disconnect();
        } catch (SocketTimeoutException e) {
            isUnavailable = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read string stream and generate response from server.
     * @param inputStream input stream
     * @return response from server
     */
    private String readStream(InputStream inputStream) {
        try {
            BufferedReader reader;
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add post post to query.
     * @param conn connection instance
     * @throws IOException
     */
    private void setupPost(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("POST");
        conn.addRequestProperty("Content-length", String.valueOf(post.getContentLength()));
        conn.addRequestProperty(
                post.getContentType().getName(),
                post.getContentType().getValue());
        OutputStream outputStream = conn.getOutputStream();
        post.writeTo(conn.getOutputStream());
        outputStream.close();
    }

    @Override //SUPPRESS CHECKSTYLE Method Length
    public void onPostExecute(String response) { //SUPPRESS CHECKSTYLE Method Length
        if (response == null) {
            performError(UNEXPECTED_ERROR + ":" + responseCode,  isUnavailable ? 999 : 0);
        } else {
            JSON json = new JSON(response);
            listener.onAnswerReceived(json);
        }
        listener.onRequestEnd();
    }

    /**
     * Perform error.
     * @param error error
     * @param code code
     */
    private void performError(String error, int code) {
        listener.onError(error, code);
    }
}
