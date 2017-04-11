package com.test.fragrant_world.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.fragrant_world.R;
import com.test.fragrant_world.model.TematicSet;
import com.test.fragrant_world.presenter.ListedCardPresenter;
import com.test.fragrant_world.view.ListedCardHolder;


public class TematicSetAdapter extends MVPRecyclerViewAdapter<TematicSet, ListedCardPresenter,
        ListedCardHolder> {

    @Override
    public ListedCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new ListedCardHolder(view);
    }

    @NonNull
    @Override
    protected ListedCardPresenter onCreatePresenter(@NonNull TematicSet model) {
        ListedCardPresenter cardPresenter = new ListedCardPresenter();
        cardPresenter.setModel(model);
        return cardPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull TematicSet model) {
        return model.getId();
    }

}
