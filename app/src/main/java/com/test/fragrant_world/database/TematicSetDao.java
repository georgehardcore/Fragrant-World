package com.test.fragrant_world.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.test.fragrant_world.model.TematicSet;

public class TematicSetDao extends AbsDao<TematicSet> {

    public static final String KEY_ID = "id";

    public static final String KEY_DESCRIPTION = "description";

    public static final String KEY_IMAGE = "image";

    @Override
    protected String[] getAllColumns() {
        return new String[] {KEY_ID, KEY_IMAGE, KEY_DESCRIPTION};
    }

    @Override
    protected Uri getTableUri() {
        return FWContentProvider.TEMATIC_SETS;
    }

    @Override
    protected TematicSet makeInstanceFromCursor(Cursor cursor) {
        return new TematicSet(cursor);
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(TematicSet instance) {
        ContentValues section = new ContentValues();
        section.put(KEY_ID, instance.getId());
        section.put(KEY_IMAGE, instance.getImg());
        section.put(KEY_DESCRIPTION, instance.getDescription());
        return section;
    }
}
