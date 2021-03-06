package com.test.fragrant_world.adapter;

import android.support.v7.widget.RecyclerView;

import com.test.fragrant_world.presenter.BasePresenter;
import com.test.fragrant_world.view.MVPViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MVP Abstract recycler view adapter using for MVP pattern for {@link RecyclerView.ViewHolder}
 * @param <M> Model generic type
 * @param <P> Presenter generic type
 * @param <VH> View holder generic type
 */
public abstract class MVPRecyclerViewAdapter<M, P extends BasePresenter, VH extends MVPViewHolder<P>> extends MVPRecyclerAdapter<M, P, VH> {

    private final List<M> models;

    public MVPRecyclerViewAdapter() {
        models = new ArrayList<>();
    }

    public void setAll(Collection<M> data) {
        models.clear();
        presenters.clear();
        for (M item : data) {
            addInternal(item);
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<M> data) {
        for (M item : data) {
            addInternal(item);
        }

        int addedSize = data.size();
        int oldSize = models.size() - addedSize;
        notifyItemRangeInserted(oldSize, addedSize);
    }

    public void addItem(M item) {
        addInternal(item);
        notifyItemInserted(models.size());
    }

    public void updateItem(M item) {
        Object modelId = getModelId(item);
        int position = getItemPosition(item);
        if (position >= 0) {
            models.remove(position);
            models.add(position, item);
        }

        // Swap the presenter
        P existingPresenter = presenters.get(modelId);
        if (existingPresenter != null) {
            existingPresenter.setModel(item);
        }

        if (position >= 0) {
            notifyItemChanged(position);
        }
    }

    public void removeItem(M item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            models.remove(item);
        }
        presenters.remove(getModelId(item));
        if (position >= 0) {
            notifyItemRemoved(position);
        }
    }

    private int getItemPosition(M item) {
        Object modelId = getModelId(item);
        int position = -1;
        for (int i = 0; i < models.size(); i++) {
            M model = models.get(i);
            if (getModelId(model).equals(modelId)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private void addInternal(M item) {
        models.add(item);
        presenters.put(getModelId(item), onCreatePresenter(item));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    protected M getItem(int position) {
        return models.get(position);
    }
}