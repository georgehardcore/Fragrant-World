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


public class ListedCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ListedCard> tematicSets = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new TematicSetHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TematicSetHolder setHolder = (TematicSetHolder) holder;
        ListedCard tematicSet = tematicSets.get(position);
        ImageLoader.getInstance().displayImage(tematicSet.getImg(),
                setHolder.imageCard, App.getRoundedOptions());
        setHolder.description.setText(tematicSet.getDescription());
    }

    public void setListedCards(ArrayList<ListedCard> tematicSetArrayList) {
        this.tematicSets = tematicSetArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tematicSets.size();
    }

    public class TematicSetHolder extends RecyclerView.ViewHolder {

        private final ImageView imageCard;

        private final TextView description;

        public TematicSetHolder(View itemView) {
            super(itemView);
            imageCard = (ImageView) itemView.findViewById(R.id.image_card);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
