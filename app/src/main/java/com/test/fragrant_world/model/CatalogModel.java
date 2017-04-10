package com.test.fragrant_world.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.http.json.JSONArray;

import java.util.ArrayList;

public class CatalogModel implements Parcelable {

    private ArrayList<Banner> banners;

    private ArrayList<News> news;

    private ArrayList<TematicSet> tematicSets;

    private ArrayList<Section> viewedProducts;

    public CatalogModel(JSON json) {
        JSONArray bannersArr = json.getArray("banners");
        banners = new ArrayList<>();
        for (int i = 0; i < bannersArr.size(); i++) {
            banners.add(new Banner(bannersArr.getJSONObject(i)));
        }

        tematicSets = new ArrayList<>();
        JSONArray tematicSetsArr = json.getArray("tematicSets");
        for (int i = 0; i < tematicSetsArr.size(); i++) {
            tematicSets.add(new TematicSet(tematicSetsArr.getJSONObject(i)));
        }

        JSONArray newsArr = json.getArray("news");
        news = new ArrayList<>();
        for (int i = 0; i < newsArr.size(); i++) {
            news.add(new News(newsArr.getJSONObject(i)));
        }
        viewedProducts = new ArrayList<>();
        JSONArray sectionsArr = json.getArray("viewedProducts");
        for (int i = 0; i < sectionsArr.size(); i++) {
            viewedProducts.add(new Section(sectionsArr.getJSONObject(i)));
        }
    }

    protected CatalogModel(Parcel in) {
        banners = in.createTypedArrayList(Banner.CREATOR);
        tematicSets = in.createTypedArrayList(TematicSet.CREATOR);
        news = in.createTypedArrayList(News.CREATOR);
        viewedProducts = in.createTypedArrayList(Section.CREATOR);
    }

    public static final Creator<CatalogModel> CREATOR = new Creator<CatalogModel>() {
        @Override
        public CatalogModel createFromParcel(Parcel in) {
            return new CatalogModel(in);
        }

        @Override
        public CatalogModel[] newArray(int size) {
            return new CatalogModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(banners);
        dest.writeTypedList(viewedProducts);
    }

    public ArrayList<TematicSet> getTematicSets() {
        return tematicSets;
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public ArrayList<Section> getViewedProducts() {
        return viewedProducts;
    }

    public ArrayList<Banner> getBanners() {
        return banners;
    }
}
