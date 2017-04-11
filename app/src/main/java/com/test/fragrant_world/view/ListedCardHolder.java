package com.test.fragrant_world.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.presenter.ListedCardPresenter;


public class ListedCardHolder extends MVPViewHolder<ListedCardPresenter> implements ListedCardView {

    private final ImageView imageCard;

    private final TextView description;

    public ListedCardHolder(View itemView) {
        super(itemView);
        imageCard = (ImageView) itemView.findViewById(R.id.image_card);
        description = (TextView) itemView.findViewById(R.id.description);
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void loadImage(String image) {
        ImageLoader.getInstance().displayImage(image, imageCard, App.getRoundedOptions());
    }
}
