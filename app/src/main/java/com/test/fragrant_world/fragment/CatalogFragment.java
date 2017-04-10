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
import com.test.fragrant_world.adapter.ListedCardAdapter;
import com.test.fragrant_world.adapter.PresentationAdapter;
import com.test.fragrant_world.adapter.ProductAdapter;
import com.test.fragrant_world.adapter.SectionsAdapter;
import com.test.fragrant_world.http.HttpResponseListener;
import com.test.fragrant_world.http.Queries;
import com.test.fragrant_world.http.Request;
import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.listener.SimpleItemClickListener;
import com.test.fragrant_world.model.Catalog;

import org.apache.http.entity.mime.MultipartEntityBuilder;


public class CatalogFragment extends BaseFragment {

    private View fragmentView;
    /** Indicator adapter. */
    private IndicatorAdapter indicatorAdapter;

    private PresentationAdapter presentationAdapter;

    private ListedCardAdapter tematicSetsAdapter;

    private ListedCardAdapter newsAdapter;

    private ProductAdapter productAdapter;

    private SectionsAdapter sectionsAdapter;

    private Request request;

    private Catalog catalogModel;

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
        sectionsAdapter = new SectionsAdapter(createSectionClickListener());
        productAdapter = new ProductAdapter();
    }

    private SimpleItemClickListener createSectionClickListener() {
        return new SimpleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                productAdapter.setProducts(catalogModel.getViewedProducts().get(position).getProducts());
            }
        };
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
        RecyclerView products = (RecyclerView) fragmentView.findViewById(R.id.products_list);
        products.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        products.setAdapter(productAdapter);
        RecyclerView sections = (RecyclerView) fragmentView.findViewById(R.id.sections_list);
        sections.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        sections.setAdapter(sectionsAdapter);
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
                catalogModel = new Catalog(catalog);
                indicatorAdapter.setSize(catalogModel.getBanners().size());
                presentationAdapter.setBanners(catalogModel.getBanners());
                tematicSetsAdapter.setListedCards(catalogModel.getTematicSets());
                newsAdapter.setListedCards(catalogModel.getNews());
                productAdapter.setProducts(catalogModel.getViewedProducts().get(0).getProducts());
                sectionsAdapter.setSections(catalogModel.getViewedProducts());
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
