package com.test.fragrant_world.http.json;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/** JSON parser. */
@SuppressWarnings("PMD")
public class JSON {

    /** Parsing JSON object. */
    private JSONObject object;
    /** Json object keys. */
    private ArrayList<String> keys;

    /**
     * Constructor with parameters.
     * @param object json object for parsing
     */
    public JSON(JSONObject object) {
        this.object = object;
        keys = new ArrayList<>();
        Iterator<String> keysIterator = this.object.keys();
        while (keysIterator.hasNext()) {
            this.keys.add(keysIterator.next());
        }
    }

    /**
     * Constructor with parameters.
     * @param json json string
     */
    public JSON(String json) {
        try {
            this.object = new JSONObject(json);
            keys = new ArrayList<>();
            Iterator<String> keysIterator = this.object.keys();
            while (keysIterator.hasNext()) {
                this.keys.add(keysIterator.next());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for json object keys size.
     * @return size
     */
    public int size() {
        return object.length();
    }

    /**
     * Getter for integer.
     * @param key object key.
     * @return integer value
     */
    public Integer getInt(String key) {
        int value = 0;
        if (object.isNull(key)) return 0;
        try {
            value = object.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Getter for long.
     * @param key object key
     * @return long key
     */
    public long getLong(String key) {
        long value = 0;
        if (object.isNull(key)) return 0;
        try {
            value = object.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * Getter for string.
     * @param key object key.
     * @return string value
     */
    public String getString(String key) {
        String value = null;
        if (object.isNull(key)) return null;
        try {
            value = object.getString(key);
            if (value.equals("") || value.equals("null")) value = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Check if json object contains key.
     * @param key key
     * @return true if contains
     */
    public boolean contains(String key) {
        return !object.isNull(key);
    }

    /**
     * Getter for boolean.
     * @param key object key.
     * @return boolean value
     */
    public Boolean getBoolean(String key) {
        boolean value;
        if (object.isNull(key)) return false;
        try {
            value = object.getBoolean(key);
        } catch (JSONException e) {
            value = getInt(key) == 1;
        }
        return value;
    }

    /**
     * Getter for float.
     * @param key object key.
     * @return float value
     */
    public float getFloat(String key) {
        float value = 0f;
        if (object.isNull(key)) return 0f;
        try {
            value = (float) object.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Getter for json array.
     * @param key object key.
     * @return json array
     */
    public JSONArray getArray(String key) {
        JSONArray jsonArray = null;
        if (object.isNull(key)) return null;
        try {
            jsonArray = new JSONArray(object.getJSONArray(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public String toString() {
        return object.toString();
    }

    /**
     * Getter for json object.
     * @param key object key.
     * @return json object
     */
    public JSON getJSONObject(String key) {
        JSON json = null;
        if (object.isNull(key)) return null;
        try {
            json = new JSON(object.getJSONObject(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Getter for json object keys.
     * @return json object keys
     */
    public ArrayList<String> keys() {
        return keys;
    }

}
