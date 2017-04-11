package com.test.fragrant_world.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.test.fragrant_world.database.NewsDao;
import com.test.fragrant_world.database.TematicSetDao;
import com.test.fragrant_world.http.json.JSON;



public class News implements Parcelable, ListedCard {

    private long id;

    private String img;

    private String description;

    public News(JSON json) {
        this.id = json.getLong("id");
        this.img = json.getString("img");
        this.description = json.getString("description");
    }

    protected News(Parcel in) {
        id = in.readLong();
        img = in.readString();
        description = in.readString();
    }

    public News(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(NewsDao.KEY_ID));
        img = cursor.getString(cursor.getColumnIndex(NewsDao.KEY_IMAGE));
        description = cursor.getString(cursor.getColumnIndex(NewsDao.KEY_DESCRIPTION));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(img);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getImg() {
        return img;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
