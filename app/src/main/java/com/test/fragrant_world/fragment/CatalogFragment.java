package com.test.fragrant_world.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.adapter.IndicatorAdapter;
import com.test.fragrant_world.adapter.PresentationAdapter;
import com.test.fragrant_world.adapter.ListedCardAdapter;
import com.test.fragrant_world.http.HttpResponseListener;
import com.test.fragrant_world.http.Queries;
import com.test.fragrant_world.http.Request;
import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.http.json.JSONArray;
import com.test.fragrant_world.model.Banner;
import com.test.fragrant_world.model.ListedCard;
import com.test.fragrant_world.model.News;
import com.test.fragrant_world.model.TematicSet;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.ArrayList;


public class CatalogFragment extends BaseFragment {

    private View fragmentView;
    /** Indicator adapter. */
    private IndicatorAdapter indicatorAdapter;

    private PresentationAdapter presentationAdapter;

    private ListedCardAdapter tematicSetsAdapter;

    private ListedCardAdapter newsAdapter;

    private Request request;

    private ArrayList<Banner> banners;

    private ArrayList<ListedCard> tematicSets;

    private ArrayList<ListedCard> news;

    @Override
    public void onResume() {
        super.onResume();
        getContext().setCurrentTitle(App.getStr(R.string.catalog));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indicatorAdapter = new IndicatorAdapter();
        tematicSetsAdapter = new ListedCardAdapter();
        presentationAdapter = new PresentationAdapter(getChildFragmentManager());
        newsAdapter = new ListedCardAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_catalog, container, false);
        ViewPager pager = (ViewPager) fragmentView.findViewById(R.id.pager);
        pager.setAdapter(presentationAdapter);
        pager.addOnPageChangeListener(createPageChangeListener());
        RecyclerView indicator = (RecyclerView) fragmentView.findViewById(R.id.indicator);
        indicator.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        indicator.setAdapter(indicatorAdapter);
        RecyclerView tematicSetsView = (RecyclerView) fragmentView.findViewById(R.id.tematic_set_list);
        tematicSetsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tematicSetsView.setAdapter(tematicSetsAdapter);
        RecyclerView newsList = (RecyclerView) fragmentView.findViewById(R.id.news_list);
        newsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newsList.setAdapter(newsAdapter);
        request = new Request.Builder().httpRequest(Queries.catalog()).view(fragmentView)
                .listener(createCatalogListener()).postParams(MultipartEntityBuilder.create()
                        .build()).build();
        return fragmentView;
    }

    private ViewPager.OnPageChangeListener createPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorAdapter.setSelectedPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    private HttpResponseListener createCatalogListener() {
        return new HttpResponseListener() {
            @Override
            public void onAnswerReceived(JSON jsonObject) {
                JSON catalog = jsonObject.getJSONObject("catalog");
                JSONArray bannersArr = catalog.getArray("banners");
                banners = new ArrayList<>();
                for (int i = 0; i < bannersArr.size(); i++) {
                    banners.add(new Banner(bannersArr.getJSONObject(i)));
                }
                indicatorAdapter.setSize(banners.size());
                presentationAdapter.setBanners(banners);
                tematicSets = new ArrayList<>();
                JSONArray tematicSetsArr = catalog.getArray("tematicSets");
                for (int i = 0; i < tematicSetsArr.size(); i++) {
                    tematicSets.add(new TematicSet(tematicSetsArr.getJSONObject(i)));
                }
                tematicSetsAdapter.setListedCards(tematicSets);
                JSONArray newsArr = catalog.getArray("news");
                news = new ArrayList<>();
                for (int i = 0; i < newsArr.size(); i++) {
                    news.add(new News(newsArr.getJSONObject(i)));
                }
                newsAdapter.setListedCards(news);
                fragmentView.findViewById(R.id.catalog_layout).setVisibility(View.VISIBLE);
                Log.e("Answer:" , "CATALOG");
            }

            @Override
            public void onError(String error, int code) {
                //do nothing
            }
        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        request.execute();
    }
}
