package com.test.fragrant_world.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.http.json.JSONArray;

import java.util.ArrayList;

public class Section implements Parcelable {

    private String name;

    private ArrayList<Product> products;

    public Section(JSON json) {
        this.name = json.getString("section");
        if (name == null) name = "Unknown";
        JSONArray array = json.getArray("products");
        products = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            products.add(new Product(array.getJSONObject(i)));
        }
    }


    protected Section(Parcel in) {
        name = in.readString();
        products = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(products);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }
}
