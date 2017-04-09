package com.test.fragrant_world.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;


/** Indicator adapter. */
public class IndicatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /** Indicator selected position. */
    private int selectedPosition;
    /** Unchecked item. */
    private int uncheckedItem = R.drawable.background_indicator;
    /** Checked item. */
    private int checkedItem = R.drawable.backgound_selected_indicator;
    /** Default item size. */
    private int size = 3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_indicator, parent, false);
        return new ViewHolderIndicator(view, parent);
    }

    /**
     * Setter for size.
     * @param size size
     */
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderIndicator holderIndicator = (ViewHolderIndicator) holder;
        holderIndicator.view.setBackgroundResource(position == selectedPosition ?
                checkedItem  :  uncheckedItem);
    }

    /**
     * Setter for unchecked indicator.
     * @param uncheckedItem unchecked indicator
     */
    public void setUncheckedItem(int uncheckedItem) {
        this.uncheckedItem = uncheckedItem;
    }

    /**
     * Setter for checked item.
     * @param checkedItem checked item
     */
    public void setCheckedItem(int checkedItem) {
        this.checkedItem = checkedItem;
    }

    @Override
    public int getItemCount() {
        return size;
    }

    /**
     * Setter for selected position.
     * @param selectedPosition selected position
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    /** View holder for category item. */
    private class ViewHolderIndicator extends RecyclerView.ViewHolder {

        /** Category name. */
        private final View view;

        /**
         * Constructor with parameters.
         * @param itemView item view
         * @param parent parent
         */
        ViewHolderIndicator(View itemView, ViewGroup parent) {
            super(itemView);
            this.view = itemView;
            RelativeLayout.LayoutParams params
                = (RelativeLayout.LayoutParams) parent.getLayoutParams();
            int margin = (int) App.getDimen(R.dimen.small_margin);
            int itemSize = params.height;
            RecyclerView.LayoutParams newParams
                = new RecyclerView.LayoutParams(itemSize, itemSize);
            newParams.setMargins(margin, 0, margin, 0);
            view.setLayoutParams(newParams);
        }
    }
}
