package com.test.fragrant_world.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Partition implements Parcelable {

    private String name;

    private int imageID;

    private int settingID;

    private String badgeValue;
    private boolean selected;

    public Partition(String title, int id) {
        this.settingID = id;
        this.name = title;
    }

    public Partition(String title, int imageID, int settingID) {
        this.imageID = imageID;
        this.name = title;
        this.settingID = settingID;
    }

    protected Partition(Parcel in) {
        name = in.readString();
        imageID = in.readInt();
        settingID = in.readInt();
        badgeValue = in.readString();
        selected = in.readByte() != 0;
    }

    public String getBadgeValue() {
        return badgeValue;
    }

    public static final Creator<Partition> CREATOR = new Creator<Partition>() {
        @Override
        public Partition createFromParcel(Parcel in) {
            return new Partition(in);
        }

        @Override
        public Partition[] newArray(int size) {
            return new Partition[size];
        }
    };

    public Partition(String title, int imageID, int settingID, String badgeValue) {
        this(title, imageID, settingID);
        this.badgeValue = badgeValue;
    }

    public int getID() {
        return settingID;
    }

    public int getImageID() {
        return imageID;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(imageID);
        dest.writeInt(settingID);
        dest.writeString(badgeValue);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    public void setSelected(boolean state){
        this.selected = state;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setBadge(String badge) {
        this.badgeValue = badge;
    }
}
