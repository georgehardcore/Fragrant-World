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
import com.test.fragrant_world.model.News;
import com.test.fragrant_world.presenter.ListedCardPresenter;
import com.test.fragrant_world.view.ListedCardView;
import com.test.fragrant_world.view.MVPViewHolder;


public class NewCardAdapter extends MVPRecyclerViewAdapter<News, ListedCardPresenter, NewCardAdapter.ListedCardHolder> {

    @Override
    public ListedCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new ListedCardHolder(view);
    }

    @NonNull
    @Override
    protected ListedCardPresenter onCreatePresenter(@NonNull News model) {
        ListedCardPresenter cardPresenter = new ListedCardPresenter();
        cardPresenter.setModel(model);
        return cardPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull News model) {
        return model.getId();
    }

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

}
