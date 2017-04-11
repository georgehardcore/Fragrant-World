package com.test.fragrant_world.presenter;

import com.test.fragrant_world.model.Product;
import com.test.fragrant_world.view.ProductView;


public class ProductPresenter extends BasePresenter<Product, ProductView> {

    @Override
    protected void updateView() {
        view().setName(model.getNameOrigin());
        view().setValue(model.getValue());
        view().setPrice(model.getPriceText());
        view().loadImage(model.getImg());
    }
}
