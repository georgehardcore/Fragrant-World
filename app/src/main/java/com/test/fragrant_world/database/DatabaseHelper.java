package com.test.fragrant_world.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;

import java.util.ArrayList;

/** Class creates data base if it don't exist. */
public class DatabaseHelper extends SQLiteOpenHelper {

    /** Name of database file. */
    public static final String DATABASE_NAME = "FragrantWorld.sqlite";
    /** Database version. */
    public static final int DATABASE_VERSION = 1;

    /**
     * Creates database helper class.
     * @param context Application context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        for (int i = 0; i < migrationsList.size(); i++) {
            migrationsList.get(i).apply(sdb);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            migrationsList.get(i).revert(sdb);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            migrationsList.get(i).apply(sdb);
        }
    }

    /** Migration list. */
    private final ArrayList<Patch> migrationsList = new ArrayList<Patch>() { {
            add(createV1Patch());
        }
    };

    /**
     * Create v1 patch.
     * @return v1 patch
     */
    private Patch createV1Patch() {
        return new Patch() {
            @Override
            public void apply(SQLiteDatabase sdb) {
                sdb.execSQL(App.getStr(R.string.create_news_table));
                sdb.execSQL(App.getStr(R.string.create_tematic_sets_table));
                sdb.execSQL(App.getStr(R.string.create_viewed_products_table));
                sdb.execSQL(App.getStr(R.string.create_banners_table));
            }

            @Override
            public void revert(SQLiteDatabase sdb) {
                //do nothing
            }
        };
    }


    /** Database patch abstract class. */
    interface Patch {

        /**
         * Apply patch.
         * @param sdb database
         */
        void apply(SQLiteDatabase sdb);

        /**
         * Revert patch.
         * @param sdb database
         */
        void revert(SQLiteDatabase sdb);
    }
}
