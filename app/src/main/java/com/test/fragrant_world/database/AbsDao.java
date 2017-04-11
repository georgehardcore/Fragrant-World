package com.test.fragrant_world.database;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;

import com.test.fragrant_world.App;

import java.util.ArrayList;

/**
 * Abstract data access object.
 * @param <T> model template
 */
public abstract class AbsDao<T> {

    /** Equal string. */
    protected static final String EQUALS = "=?";
    /** Key for 'id' column in the workouts table. */
    protected static final String KEY_ID = "id";

    /**
     * Get value for specified value from cursor.
     * @param cursor Cursor to get value from.
     * @param columnName Name of column.
     * @return Value of column from the cursor.
     */
    protected String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    /**
     * Get value for specified value from cursor for int.
     * @param cursor cursor
     * @param columnName column name
     * @return int column value
     */
    protected int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    /**
     * Getter for all columns array.
     * @return all columns array
     */
    protected abstract String[] getAllColumns();

    /**
     * Getter for table uri.
     * @return table uri
     */
    protected abstract Uri getTableUri();

    /**
     * Get all records in table.
     * @return all records in table array
     */
    public ArrayList<T> getAllData() {
        Cursor cursor = getContentResolver().query(getTableUri(), getAllColumns(),
                null, null, null);
        return extractItemsFromCursor(cursor);
    }

    /**
     * Return content resolver to get access to content providers.
     * @return ContentResolver class instance
     */
    protected ContentResolver getContentResolver() {
        return App.getAppContext().getContentResolver();
    }

    /**
     * Make class instance form cursor.
     * @param cursor cursor
     * @return class instance
     */
    protected abstract T makeInstanceFromCursor(Cursor cursor);

    /**
     * Make content values from class instance.
     * @param instance instance
     * @return content values
     */
    protected abstract ContentValues makeContentValuesFromInstance(T instance);

    /**
     * Extract class instances from cursor.
     * @param cursor Cursor
     * @return class instances array
     */
    protected ArrayList<T> extractItemsFromCursor(Cursor cursor) {
        ArrayList<T> items = new ArrayList<>();
        if (cursor == null) return items;
        if (cursor.moveToFirst()) {
            do {
                try {
                    items.add(makeInstanceFromCursor(cursor));
                } catch (CursorIndexOutOfBoundsException e) {
                    return items;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    /**
     * Getter for class instance by id.
     * @param id id
     * @return class instance
     */
    public T getItemByID(long id) {
        Cursor cursor = getContentResolver().query(getTableUri(), getAllColumns(),
                KEY_ID + EQUALS, new String[]{String.valueOf(id)}, null);
        ArrayList<T> models = extractItemsFromCursor(cursor);
        return models.isEmpty() ? null : models.get(0);
    }

    /**
     * Insert item to table.
     * @param item item
     */
    public void insertItem(T item) {
        getContentResolver().insert(getTableUri(), makeContentValuesFromInstance(item));
    }

    /**
     * Delete item by id.
     * @param id id
     * @return true if deleted
     */
    public boolean deleteItemById(int id) {
        int affectedRowsNumber = getContentResolver().delete(getTableUri(), KEY_ID + EQUALS,
                new String[] {String.valueOf(id)});
        return affectedRowsNumber == 1;
    }
}
