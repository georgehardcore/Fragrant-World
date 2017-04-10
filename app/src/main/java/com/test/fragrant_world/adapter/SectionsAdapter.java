package com.test.fragrant_world.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.listener.SimpleItemClickListener;
import com.test.fragrant_world.model.Section;

import java.util.ArrayList;


public class SectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Section> sections = new ArrayList<>();

    private SimpleItemClickListener listener;

    private int selectedPosition;

    public SectionsAdapter(SimpleItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_section, parent, false);
        return new SectionHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionHolder sectionHolder = (SectionHolder) holder;
        Section section = sections.get(position);
        sectionHolder.itemView.setBackground(selectedPosition == position
                ? App.getImage(R.drawable.selected_card)
                : App.getImage(R.drawable.background_card));
        sectionHolder.name.setText(section.getName());
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    class SectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;

        public SectionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
            selectedPosition = getAdapterPosition();
            notifyDataSetChanged();
        }
    }
}
