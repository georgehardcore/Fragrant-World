package com.test.fragrant_world.presenter;


import android.support.annotation.NonNull;

import com.test.fragrant_world.http.Queries;
import com.test.fragrant_world.http.Request;
import com.test.fragrant_world.http.json.JSON;
import com.test.fragrant_world.listener.LoadingListener;
import com.test.fragrant_world.model.CatalogModel;
import com.test.fragrant_world.view.CatalogView;

import org.apache.http.entity.mime.MultipartEntityBuilder;

public class CatalogPresenter extends BasePresenter<CatalogModel, CatalogView> implements LoadingListener {

    private Request request;

    @Override
    protected void updateView() {
        view().setNews(model.getNews());
        view().setTematicSets(model.getTematicSets());
        view().setBanners(model.getBanners());
        view().setSections(model.getViewedProducts());
        if (!model.getViewedProducts().isEmpty()) {
            view().setProducts(model.getViewedProducts().get(0).getProducts());
        }
        view().showLayout();
    }

    @Override
    public void bindView(@NonNull CatalogView view) {
        super.bindView(view);
        request = new Request.Builder().postParams(MultipartEntityBuilder.create().build())
                .httpRequest(Queries.catalog()).listener(this).build();
        CatalogModel catalogModel = CatalogModel.fromDatabase();
        if (!catalogModel.isEmpty()) {
            setModel(catalogModel);
            updateView();
        }
        if (!request.isLoading()) {
            view().showLoading();
            request.execute();
        } else {
            updateView();
        }
    }

    @Override
    public void showLoading() {
        if (view() != null) {
            view().showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (view() != null) {
            view().hideLoading();
        }
    }

    @Override
    public void onAnswerReceived(JSON json) {
        setModel(CatalogModel.fromJson(json.getJSONObject("catalog")));
        if (view() != null) {
            view().showLayout();
        }
    }

    @Override
    public void onError(String error, int code) {
        if (view() == null) return;
        view().hideLoading();
        if (code == Request.NO_CONNECTION && model != null) {
            return;
        }
        view().onError(error, code);
    }

    public void onSectionClicked(int position) {
        view().setProducts(model.getViewedProducts().get(position).getProducts());
    }

    public void onRetry() {
        request.execute();
    }
}
