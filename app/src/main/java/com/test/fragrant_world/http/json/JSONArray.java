package com.test.fragrant_world.http.json;


import org.json.JSONException;

/** JSON parser for array. */
@SuppressWarnings("PMD")
public class JSONArray {

    /** JSON array instance. */
    private org.json.JSONArray array;
    /** Length. */
    private int length;

    /**
     * Constructor with parameters.
     * @param array json array
     */
    public JSONArray(org.json.JSONArray array) {
        this.array = array;
        this.length = array.length();
    }

    public JSONArray(String jsonString) {
        try {
            this.array = new org.json.JSONArray(jsonString);
            this.length = array.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for integer.
     * @param position current position
     * @return integer value
     */
    public Integer getInt(int position) {
        int value = 0;
        if (array.isNull(position)) return 0;
        try {
            value = array.getInt(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Getter for string.
     * @param position current position
     * @return string value
     */
    public String getString(int position) {
        String value = null;
        if (array.isNull(position)) return null;
        try {
            value = array.getString(position);
            if ("".equals(value) || "null".equals(value)) value = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Getter for boolean.
     * @param position current position
     * @return boolean value
     */
    public Boolean getBoolean(int position) {
        boolean value = false;
        if (array.isNull(position)) return null;
        try {
            value = array.getBoolean(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Getter for float.
     * @param position current position
     * @return float value
     */
    public Float getFloat(int position) {
        float value = 0;
        if (array.isNull(position)) return null;
        try {
            value = (float) array.getDouble(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Getter for json array.
     * @param position current position
     * @return json array
     */
    public JSONArray getArray(int position) {
        JSONArray jsonArray = null;
        if (array.isNull(position)) return null;
        try {
            jsonArray = new JSONArray(array.getJSONArray(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * Getter for json object.
     * @param position current position
     * @return json object
     */
    public JSON getJSONObject(int position) {
        JSON json = null;
        if (array.isNull(position)) return null;
        try {
            json = new JSON(array.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Array size property.
     * @return size of array
     */
    public int size() {
        return length;
    }
}
