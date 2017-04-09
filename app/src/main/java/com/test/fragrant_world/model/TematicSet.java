package com.test.fragrant_world.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.test.fragrant_world.http.json.JSON;


public class TematicSet implements Parcelable, ListedCard {

    private long id;

    private String img;

    private String description;

    public TematicSet(JSON json) {
        this.id = json.getLong("id");
        this.img = json.getString("img");
        this.description = json.getString("description");
    }

    protected TematicSet(Parcel in) {
        id = in.readLong();
        img = in.readString();
        description = in.readString();
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

    public static final Creator<TematicSet> CREATOR = new Creator<TematicSet>() {
        @Override
        public TematicSet createFromParcel(Parcel in) {
            return new TematicSet(in);
        }

        @Override
        public TematicSet[] newArray(int size) {
            return new TematicSet[size];
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
