package com.test.fragrant_world.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.listener.SimpleItemClickListener;
import com.test.fragrant_world.model.Section;
import com.test.fragrant_world.presenter.SectionPresenter;
import com.test.fragrant_world.view.MvpViewHolder;
import com.test.fragrant_world.view.SectionView;


public class SectionsAdapter extends MvpRecyclerListAdapter<Section, SectionPresenter,
        SectionsAdapter.SectionHolder>  {

    private SimpleItemClickListener listener;

    private int selectedPosition;

    public SectionsAdapter(SimpleItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public SectionsAdapter.SectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_section, parent, false);
        return new SectionHolder(view);
    }

    @Override
    protected SectionPresenter onCreatePresenter(@NonNull Section model) {
        SectionPresenter presenter = new SectionPresenter();
        presenter.setModel(model);
        return presenter;
    }

    @Override
    protected Object getModelId(@NonNull Section model) {
        return model.getName();
    }


    class SectionHolder extends MvpViewHolder<SectionPresenter> implements View.OnClickListener, SectionView {

        private final TextView name;

        public SectionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) listener.onItemClick(view, getAdapterPosition());
            selectedPosition = getAdapterPosition();
            notifyDataSetChanged();
        }

        @Override
        public void setName(String name) {
            this.name.setText(name);
        }

        @Override
        public void setupSelection() {
            itemView.setBackground(selectedPosition == getAdapterPosition()
                    ? App.getImage(R.drawable.selected_card)
                    : App.getImage(R.drawable.background_card));
        }

    }
}
