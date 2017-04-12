package com.test.fragrant_world.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.test.fragrant_world.database.BannerDao;
import com.test.fragrant_world.database.NewsDao;
import com.test.fragrant_world.database.TematicSetDao;
import com.test.fragrant_world.database.ViewedProductsDao;
import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.http.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CatalogModel implements Parcelable {

    private ArrayList<Banner> banners;

    private ArrayList<News> news;

    private ArrayList<TematicSet> tematicSets;

    private ArrayList<Section> viewedProducts;

    public CatalogModel() {
        tematicSets = TematicSetDao.getInstance().getAllData();
        news = NewsDao.getInstance().getAllData();
        viewedProducts = ViewedProductsDao.getInstance().getAllData();
        banners = BannerDao.getInstance().getAllData();
    }

    public boolean isEmpty() {
        return tematicSets.isEmpty();
    }

    public CatalogModel(JSON json) {
        JSONArray bannersArr = json.getArray("banners");
        banners = new ArrayList<>();
        for (int i = 0; i < bannersArr.size(); i++) {
            banners.add(new Banner(bannersArr.getJSONObject(i)));
        }
        BannerDao.getInstance().replace(banners);

        JSONArray tematicSetsArr = json.getArray("tematicSets");
        tematicSets = new ArrayList<>();
        for (int i = 0; i < tematicSetsArr.size(); i++) {
            tematicSets.add(new TematicSet(tematicSetsArr.getJSONObject(i)));
        }
        TematicSetDao.getInstance().replace(tematicSets);


        JSONArray newsArr = json.getArray("news");
        news = new ArrayList<>();
        for (int i = 0; i < newsArr.size(); i++) {
            news.add(new News(newsArr.getJSONObject(i)));
        }
        NewsDao.getInstance().replace(news);

        JSONArray sectionsArr = json.getArray("viewedProducts");
        viewedProducts = new ArrayList<>();
        for (int i = 0; i < sectionsArr.size(); i++) {
            Section section = (new Section(sectionsArr.getJSONObject(i)));
            viewedProducts.add(section);
            ViewedProductsDao.getInstance().insertItem(section);
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
        dest.writeTypedList(news);
        dest.writeTypedList(tematicSets);
    }

    public List<TematicSet> getTematicSets() {
        return tematicSets;
    }

    public List<News> getNews() {
        return news;
    }

    public List<Section> getViewedProducts() {
        return viewedProducts;
    }

    public List<Banner> getBanners() {
        return banners;
    }
}
