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
        //view().showNews(model.getNews());
        //view().showTematicSets(model.getTematicSets());
    }

    @Override
    public void bindView(@NonNull CatalogView view) {
        super.bindView(view);
        request = new Request.Builder().postParams(MultipartEntityBuilder.create().build())
                .httpRequest(Queries.catalog()).listener(this).build();
        if (model == null && !request.isLoading()) {
            view().showLoading();
            request.execute();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onAnswerReceived(JSON jsonObject) {
        model = new CatalogModel(jsonObject);
    }

    @Override
    public void onError(String error, int code) {

    }

    public void onSectionClicked(int position) {
        view().showPrducts(model.getViewedProducts().get(position).getProducts());
    }
}
