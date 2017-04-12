package com.test.fragrant_world.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.test.fragrant_world.model.News;

public class NewsDao extends AbsDao<News> {

    public static final String KEY_ID = "id";

    public static final String KEY_DESCRIPTION = "description";

    public static final String KEY_IMAGE = "image";

    private static NewsDao instance;

    private NewsDao() { }

    public static NewsDao getInstance() {
        if (instance != null) return instance;
        instance = new NewsDao();
        return instance;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[] {KEY_ID, KEY_IMAGE, KEY_DESCRIPTION};
    }

    @Override
    protected Uri getTableUri() {
        return FWContentProvider.NEWS_URI;
    }

    @Override
    protected News makeInstanceFromCursor(Cursor cursor) {
        return new News(cursor);
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(News instance) {
        ContentValues section = new ContentValues();
        section.put(KEY_ID, instance.getId());
        section.put(KEY_IMAGE, instance.getImg());
        section.put(KEY_DESCRIPTION, instance.getDescription());
        return section;
    }
}
