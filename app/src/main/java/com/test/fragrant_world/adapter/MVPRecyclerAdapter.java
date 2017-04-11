package com.test.fragrant_world.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.test.fragrant_world.presenter.BasePresenter;
import com.test.fragrant_world.view.MvpViewHolder;

import java.util.HashMap;
import java.util.Map;

public abstract class MVPRecyclerAdapter<M, P extends BasePresenter, VH extends MvpViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Map<Object, P> presenters;

    public MVPRecyclerAdapter() {
        presenters = new HashMap<>();
    }

    protected P getPresenter(@NonNull M model) {
        return presenters.get(getModelId(model));
    }

    protected abstract P onCreatePresenter(@NonNull M model);

    protected abstract Object getModelId(@NonNull M model);


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