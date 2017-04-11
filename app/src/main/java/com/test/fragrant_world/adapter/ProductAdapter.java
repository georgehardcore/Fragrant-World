package com.test.fragrant_world.adapter;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.model.Product;
import com.test.fragrant_world.presenter.ProductPresenter;
import com.test.fragrant_world.view.MVPViewHolder;
import com.test.fragrant_world.view.ProductView;


public class ProductAdapter extends MVPRecyclerViewAdapter<Product, ProductPresenter,
        ProductAdapter.ProductHolder> {

    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    protected ProductPresenter onCreatePresenter(@NonNull Product model) {
        ProductPresenter productPresenter = new ProductPresenter();
        productPresenter.setModel(model);
        return productPresenter;
    }

    @Override
    protected Object getModelId(@NonNull Product model) {
        return model.getArticul();
    }


    public class ProductHolder extends MVPViewHolder<ProductPresenter> implements ProductView {

        private final TextView name;

        private final ImageView image;

        private final TextView price;

        private final TextView value;

        public ProductHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
            value = (TextView) itemView.findViewById(R.id.value);
        }

        @Override
        public void setName(String nameOrigin) {
            name.setText(nameOrigin);
        }

        @Override
        public void setValue(String value) {
            this.value.setText(value);
        }

        @Override
        public void setPrice(String priceText) {
            price.setText(priceText);
        }

        @Override
        public void loadImage(String img) {
            ImageLoader.getInstance().displayImage(img, image, App.getOptions());
        }
    }
}
