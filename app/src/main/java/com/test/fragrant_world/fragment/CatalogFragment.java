package com.test.fragrant_world.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.test.fragrant_world.listener.SimpleItemClickListener;
import com.test.fragrant_world.model.Banner;
import com.test.fragrant_world.model.ListedCard;
import com.test.fragrant_world.model.Product;
import com.test.fragrant_world.model.Section;
import com.test.fragrant_world.presenter.CatalogPresenter;
import com.test.fragrant_world.view.CatalogView;

import java.util.List;


public class CatalogFragment extends BaseFragment implements CatalogView {

    private View fragmentView;
    /** Indicator adapter. */
    private IndicatorAdapter indicatorAdapter;

    private PresentationAdapter presentationAdapter;

    private ListedCardAdapter tematicSetsAdapter;

    private ListedCardAdapter newsAdapter;

    private ProductAdapter productAdapter;

    private SectionsAdapter sectionsAdapter;

    private CatalogPresenter preseneter;

    @Override
    public void onResume() {
        super.onResume();
        getContext().setCurrentTitle(App.getStr(R.string.catalog));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preseneter = new CatalogPresenter();
        indicatorAdapter = new IndicatorAdapter();
        tematicSetsAdapter = new ListedCardAdapter();
        presentationAdapter = new PresentationAdapter(getChildFragmentManager());
        newsAdapter = new ListedCardAdapter();
        sectionsAdapter = new SectionsAdapter(new SimpleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                preseneter.onSectionClicked(position);
            }
        });
        productAdapter = new ProductAdapter();
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


    public void showNews(List<ListedCard> newsList) {
        newsAdapter.addAll(newsList);
    }


    public void showTematicSets(List<ListedCard> tematicSets) {
        tematicSetsAdapter.addAll(tematicSets);
    }


    public void showBanners(List<Banner> banners) {
        presentationAdapter.setBanners(banners);
    }


    public void showSections(List<Section> sections) {
        sectionsAdapter.setSections(sections);
    }


    public void showPrducts(List<Product> products) {

    }


    public void showLayout() {
        fragmentView.findViewById(R.id.catalog_layout).setVisibility(View.VISIBLE);
    }


    public void showLoading() {
        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        fragmentView.findViewById(R.id.error_view).setVisibility(View.GONE);
    }


    public void onError() {

    }
}
