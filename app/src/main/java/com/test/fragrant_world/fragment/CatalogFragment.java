package com.test.fragrant_world.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.test.fragrant_world.R;
import com.test.fragrant_world.activity.WorldActivity;
import com.test.fragrant_world.adapter.IndicatorAdapter;
import com.test.fragrant_world.adapter.NewCardAdapter;
import com.test.fragrant_world.adapter.PresentationAdapter;
import com.test.fragrant_world.adapter.ProductAdapter;
import com.test.fragrant_world.adapter.SectionsAdapter;
import com.test.fragrant_world.adapter.TematicSetAdapter;
import com.test.fragrant_world.http.Request;
import com.test.fragrant_world.listener.SimpleItemClickListener;
import com.test.fragrant_world.model.Banner;
import com.test.fragrant_world.model.News;
import com.test.fragrant_world.model.Product;
import com.test.fragrant_world.model.Section;
import com.test.fragrant_world.model.TematicSet;
import com.test.fragrant_world.presenter.CatalogPresenter;
import com.test.fragrant_world.view.CatalogView;

import java.util.List;


public class CatalogFragment extends BaseFragment implements CatalogView, View.OnClickListener,
        Toolbar.OnMenuItemClickListener {

    private View fragmentView;
    /** Indicator adapter. */
    private IndicatorAdapter indicatorAdapter;

    private PresentationAdapter presentationAdapter;

    private TematicSetAdapter tematicSetsAdapter;

    private NewCardAdapter newsAdapter;

    private ProductAdapter productAdapter;

    private SectionsAdapter sectionsAdapter;

    private CatalogPresenter presenter;


    @Override
    public void onResume() {
        super.onResume();
        ((WorldActivity) getContext()).enableDrawer();
        presenter.bindView(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new CatalogPresenter();
        indicatorAdapter = new IndicatorAdapter();
        tematicSetsAdapter = new TematicSetAdapter();
        presentationAdapter = new PresentationAdapter(getChildFragmentManager());
        newsAdapter = new NewCardAdapter();
        sectionsAdapter = new SectionsAdapter(new SimpleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.onSectionClicked(position);
            }
        });
        productAdapter = new ProductAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_catalog, container, false);
        fragmentView.findViewById(R.id.retry_button).setOnClickListener(this);
        ViewPager pager = (ViewPager) fragmentView.findViewById(R.id.pager);
        pager.setAdapter(presentationAdapter);
        pager.addOnPageChangeListener(createPageChangeListener());
        RecyclerView indicator = (RecyclerView) fragmentView.findViewById(R.id.indicator);
        setupRecyclerView(indicator, indicatorAdapter);
        RecyclerView tematicSets = (RecyclerView) fragmentView.findViewById(R.id.tematic_set_list);
        setupRecyclerView(tematicSets, tematicSetsAdapter);
        RecyclerView newsList = (RecyclerView) fragmentView.findViewById(R.id.news_list);
        setupRecyclerView(newsList, newsAdapter);
        RecyclerView products = (RecyclerView) fragmentView.findViewById(R.id.products_list);
        setupRecyclerView(products, productAdapter);
        RecyclerView sections = (RecyclerView) fragmentView.findViewById(R.id.sections_list);
        setupRecyclerView(sections, sectionsAdapter);
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        getContext().getToolbar().setOnMenuItemClickListener(this);
        menuInflater.inflate(R.menu.catalog_menu, menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.shop_card) {
            getContext().setFragment(new BucketFragment(), null, true);
        }
        return true;
    }

    private void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private ViewPager.OnPageChangeListener createPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                indicatorAdapter.setSelectedPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        };
    }

@   Override
    public void setNews(List<News> newsList) {
        newsAdapter.setAll(newsList);
    }

    @Override
    public void setTematicSets(List<TematicSet> tematicSets) {
        tematicSetsAdapter.setAll(tematicSets);
    }

    @Override
    public void setBanners(List<Banner> banners) {
        presentationAdapter.setBanners(banners);
    }

    @Override
    public void setSections(List<Section> sections) {
        sectionsAdapter.setAll(sections);
    }

    @Override
    public void setProducts(List<Product> products) {
        productAdapter.setAll(products);
    }

    @Override
    public void hideLoading() {
        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void showLayout() {
        fragmentView.findViewById(R.id.catalog_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        fragmentView.findViewById(R.id.error_view).setVisibility(View.GONE);
    }

    @Override
    public void onError(String error, int code) {

        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.error_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retry_button) presenter.onRetry();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindView();
    }
}
