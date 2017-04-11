package com.test.fragrant_world.adapter;

import android.support.v7.widget.RecyclerView;

import com.test.fragrant_world.presenter.BasePresenter;
import com.test.fragrant_world.view.MVPViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * MVP Recycler View adapter presenters overlay.
 * @param <M> Model generic type
 * @param <P> Presenter generic type
 * @param <VH> View holder generic type
 */
public abstract class MVPRecyclerAdapter<M, P extends BasePresenter, VH extends MVPViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Map<Object, P> presenters;

    public MVPRecyclerAdapter() {
        presenters = new HashMap<>();
    }

    protected P getPresenter(M model) {
        return presenters.get(getModelId(model));
    }

    protected abstract P onCreatePresenter(M model);

    protected abstract Object getModelId(M model);

    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
        holder.unbindPresenter();
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        holder.unbindPresenter();
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindPresenter(getPresenter(getItem(position)));
    }

    protected abstract M getItem(int position);
}