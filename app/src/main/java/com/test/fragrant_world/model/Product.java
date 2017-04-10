package com.test.fragrant_world.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.util.Constants;

public class Product implements Parcelable {

    private long articul;

    private String nameOrigin;

    private String nameRus;

    private String img;

    private int price;

    private String currency;

    private String value;

    public Product(JSON json) {
        this.articul = json.getLong("articul");
        this.nameOrigin = json.getString("nameOriginal");
        this.img = json.getString("img");
        this.nameRus = json.getString("nameRus");
        this.price = json.getInt("price");
        this.currency = json.getString("currency");
        this.value = json.getJSONObject("props").getString("value");
    }

    protected Product(Parcel in) {
        articul = in.readLong();
        nameOrigin = in.readString();
        nameRus = in.readString();
        img = in.readString();
        price = in.readInt();
        currency = in.readString();
        value = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public long getArticul() {
        return articul;
    }

    public String getNameOrigin() {
        return nameOrigin;
    }

    public String getNameRus() {
        return nameRus;
    }

    public String getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }

    public String getPriceText() {
        return price + Constants.SPACE + currency;
    }

    public String getCurrency() {
        return currency;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(articul);
        dest.writeString(nameOrigin);
        dest.writeString(nameRus);
        dest.writeString(img);
        dest.writeInt(price);
        dest.writeString(currency);
        dest.writeString(value);
    }
}
