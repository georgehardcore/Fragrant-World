package com.test.fragrant_world.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Product> products = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductHolder productHolder = (ProductHolder) holder;
        Product product = products.get(position);
        productHolder.name.setText(product.getNameOrigin());
        productHolder.value.setText(product.getValue());
        productHolder.price.setText(product.getPriceText());
        ImageLoader.getInstance().displayImage(product.getImg(), productHolder.image,
                App.getOptions());
    }

    public void setProducts(ArrayList<Product> productArray) {
        this.products = productArray;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ProductHolder extends RecyclerView.ViewHolder {

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
    }
}
