package com.test.fragrant_world.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.test.fragrant_world.database.BannerDao;
import com.test.fragrant_world.http.json.JSON;

public class Banner implements Parcelable {

    private String image;

    private String description;

    private String header;

    public Banner(JSON json) {
        image = json.getString("img");
        description = json.getString("description");
        header = json.getString("header");
    }

    protected Banner(Parcel in) {
        image = in.readString();
        description = in.readString();
        header = in.readString();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    public Banner(Cursor cursor) {
        image = cursor.getString(cursor.getColumnIndex(BannerDao.KEY_IMAGE));
        description = cursor.getString(cursor.getColumnIndex(BannerDao.KEY_DESCRIPTION));
        header = cursor.getString(cursor.getColumnIndex(BannerDao.KEY_HEADER));
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(header);
    }
}
