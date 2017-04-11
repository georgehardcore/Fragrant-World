package com.test.fragrant_world.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.test.fragrant_world.model.Banner;


public class BannerDao extends AbsDao<Banner> {

    public static final String KEY_IMAGE = "image";

    public static final String KEY_HEADER = "header";

    public static final String KEY_DESCRIPTION = "description";

    @Override
    protected String[] getAllColumns() {
        return new String[] {KEY_HEADER, KEY_IMAGE, KEY_DESCRIPTION};
    }

    @Override
    protected Uri getTableUri() {
        return FWContentProvider.BANNERS_URI;
    }

    @Override
    protected Banner makeInstanceFromCursor(Cursor cursor) {
        return new Banner(cursor);
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Banner instance) {
        return null;
    }
}
