package com.test.fragrant_world.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

/** Class provides access to application data. */
@SuppressWarnings("PMD")
public class FWContentProvider extends ContentProvider {

    /** Database helper object. */
    private SQLiteOpenHelper msDatabase;
    /** Application Content provider URI. */
    private static final Uri PROVIDER_URI
        = Uri.parse("content://com.test.fragrant_world.contentprovider/");
    /** Sets table Uri. */
    public static final Uri BANNERS_URI = Uri.parse(PROVIDER_URI + "banners");
    /** Workouts table Uri. */
    public static final Uri VIEWED_PRODUCTS_URI = Uri.parse(PROVIDER_URI + "viewedproducts");
    /** Workout programs table Uri.*/
    public static final Uri TEMATIC_SETS = Uri.parse(PROVIDER_URI + "tematicsets");

    public static final Uri NEWS_URI = Uri.parse(PROVIDER_URI + "news");
    /** Workouts table name. */
    public static final String BANNERS_TABLE = "Banners";
    /** Set table name. */
    public static final String VIEWED_PRODUCTS_TABLE = "ViewedProducts";
    /** Workout programs table name. */
    public static final String TEMATIC_SETS_TABLE = "TematicSets";

    public static final String NEWS_TABLE = "News";
    /** Name of parameter that restrict number of
     * measurements in result table of query() method. */
    public static final String QUERY_PARAMETER_LIMIT = "limit";

    @Override
    public boolean onCreate() {
        msDatabase = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public synchronized Cursor query(@NonNull Uri uri, String[] projection, String selection,
                                     String[] selectionArgs, String sortOrder) {
        String tableName = getType(uri);
        if (tableName == null) {
            return null;
        }
        try {
            return msDatabase.getReadableDatabase().query(
                    tableName, projection, selection, selectionArgs,
                    null, null, sortOrder, uri.getQueryParameter(QUERY_PARAMETER_LIMIT));
        } catch (SQLiteException exception) {
            return null;
        }
    }

    @Override
    public synchronized int update(@NonNull Uri uri, ContentValues values, String selection,
                                   String[] selectionArgs) {
        String tableName = getType(uri);
        if (tableName == null) {
            return 0;
        }
        int affectedRowsNumber = msDatabase.getWritableDatabase().update(
                tableName, values, selection, selectionArgs);
        if (affectedRowsNumber > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return affectedRowsNumber;
    }



    @Override
    public synchronized Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        String tableName = getType(uri);
        if (tableName == null) {
            return null;
        }
        long recordID = msDatabase.getWritableDatabase().insert(
                tableName, null, contentValues);
        if (recordID == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(tableName + "#" + recordID);

    }

    @Override
    public synchronized int delete(@NonNull Uri uri, String selection, String[] selArgs) {
        String tableName = getType(uri);
        if (tableName == null) {
            return 0;
        }
        int affectedRowsNumber = msDatabase.getWritableDatabase().delete(
                tableName, selection, selArgs);
        if (affectedRowsNumber > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return affectedRowsNumber;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        if (uri.toString().startsWith(BANNERS_URI.toString())) {
            return BANNERS_TABLE;
        } else if (uri.toString().startsWith(VIEWED_PRODUCTS_URI.toString())) {
            return VIEWED_PRODUCTS_TABLE;
        } else if (uri.toString().startsWith(TEMATIC_SETS.toString())) {
            return TEMATIC_SETS_TABLE;
        } else if (uri.toString().startsWith(NEWS_URI.toString())) {
            return NEWS_TABLE;
        } else {
            return null;
        }
    }
}
