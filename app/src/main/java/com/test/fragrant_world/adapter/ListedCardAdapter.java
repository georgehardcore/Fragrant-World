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
import com.test.fragrant_world.model.ListedCard;

import java.util.ArrayList;


public class ListedCardAdapter<M extends ListedCard> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<M> modelList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new ListedCardHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListedCardHolder setHolder = (ListedCardHolder) holder;
        ListedCard cardModel = modelList.get(position);
        ImageLoader.getInstance().displayImage(cardModel.getImg(),
                setHolder.imageCard, App.getRoundedOptions());
        setHolder.description.setText(cardModel.getDescription());
    }

    public void setListedCards(ArrayList<M> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ListedCardHolder extends RecyclerView.ViewHolder {

        private final ImageView imageCard;

        private final TextView description;

        public ListedCardHolder(View itemView) {
            super(itemView);
            imageCard = (ImageView) itemView.findViewById(R.id.image_card);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
