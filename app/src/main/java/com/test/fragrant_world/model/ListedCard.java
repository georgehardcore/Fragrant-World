package com.test.fragrant_world.model;


import android.os.Parcelable;

public interface ListedCard extends Parcelable {

    long getId();

    String getImg();

    String getDescription();

}
