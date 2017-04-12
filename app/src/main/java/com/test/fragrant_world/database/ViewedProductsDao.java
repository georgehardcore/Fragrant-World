package com.test.fragrant_world.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.test.fragrant_world.model.Section;


public class ViewedProductsDao extends AbsDao<Section> {

    public static final String KEY_NAME = "name";

    public static final String KEY_PRODUCTS = "products";

    private static ViewedProductsDao instance;

    private ViewedProductsDao() { }

    public static ViewedProductsDao getInstance() {
        if (instance != null) return instance;
        instance = new ViewedProductsDao();
        return instance;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[] {KEY_NAME, KEY_PRODUCTS};
    }

    @Override
    protected Uri getTableUri() {
        return FWContentProvider.VIEWED_PRODUCTS_URI;
    }

    @Override
    protected Section makeInstanceFromCursor(Cursor cursor) {
        return new Section(cursor);
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Section instance) {
        ContentValues section = new ContentValues();
        section.put(KEY_NAME, instance.getName());
        section.put(KEY_PRODUCTS, instance.getProductsString());
        return section;
    }
}
