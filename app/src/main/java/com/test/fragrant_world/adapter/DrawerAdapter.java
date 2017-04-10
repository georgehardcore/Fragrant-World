package com.test.fragrant_world.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.listener.DrawerClickListener;
import com.test.fragrant_world.model.Partition;

import java.util.ArrayList;

/** Drawer item layout adapter. */
@SuppressWarnings("PMD")
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    /** User item id. */
    public static final int LOGO = 11;
    /** Partitions array list. */
    private ArrayList<Partition> partitions = new ArrayList<>();
    /** Selected position. */
    private Integer selectedPosition;
    /** Drawer click listener. */
    private final DrawerClickListener listener;

    /**
     * Constructor without parameters.
     * @param listener click callback
     * */
    public DrawerAdapter(DrawerClickListener listener) {
        this.listener = listener;
        selectedPosition = 0;
    }

    /** Update model. */
    public void update() { //SUPPRESS CHECKSTYLE Method Length
        partitions = new ArrayList<>();
        partitions.add(new Partition("", LOGO));
        partitions.add(new Partition(App.getStr(R.string.catalog),
                R.drawable.ic_action_catalog, R.id.nav_catalog));
        partitions.add(new Partition(App.getStr(R.string.sets),
                R.drawable.ic_action_sets, R.id.nav_sets));
        partitions.add(new Partition(App.getStr(R.string.shops),
                R.drawable.ic_action_shops, R.id.nav_shops));
        partitions.add(new Partition(App.getStr(R.string.actions),
                R.drawable.ic_action_actions, R.id.nav_actions));
        partitions.add(new Partition(App.getStr(R.string.profile),
                R.drawable.ic_action_profile, R.id.nav_profile));
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int layoutID) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(layoutID, viewGroup, false);
        if (layoutID == R.layout.list_item_drawer_simple) {
            return new ViewHolderItem(view);
        }
        if (layoutID == R.layout.list_item_drawer_user) {
            return new ViewHolderUser(view);
        }
        return null;
    }

    @Override//SUPPRESS CHECKSTYLE Method Length
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) { //SUPPRESS CHECKSTYLE Method Length
        Partition partition = partitions.get(i);
        if (viewHolder instanceof ViewHolderItem) {
            ViewHolderItem item = (ViewHolderItem) viewHolder;
            setupSimpleItemHolder(item, i, partition);
        }
    }

    /**
     * Setup simple item holder.
     * @param item holder item
     * @param i holder position
     * @param partition partition instance
     */
    private void setupSimpleItemHolder(ViewHolderItem item, int i, Partition partition) { //SUPPRESS CHECKSTYLE Method Length
        boolean selected = i == selectedPosition;
        item.itemView.setBackgroundColor(App.getClr(selected
                ? R.color.drawer_selected_color
                : R.color.white));
        item.icon.setBackground(App.getImage(partition.getImageID()));
        item.text.setText(partition.getName());
    }

    @Override
    public int getItemViewType(int position) {
        Partition partition = partitions.get(position);
        if (partition.getID() == LOGO) {
            return R.layout.list_item_drawer_user;
        } else {
            return R.layout.list_item_drawer_simple;
        }
    }

    @Override
    public int getItemCount() {
        return partitions.size();
    }

    /**
     * Get position by partition id.
     * @param id id
     * @return position
     */
    public int getPositionById(int id) {
        for (Partition partition: partitions) {
            if (partition.getID() == id) {
                return partitions.indexOf(partition);
            }
        }
        return -1;
    }

    /**
     * Set selection of clicked item.
     * @param position selected position
     */
    public void setSelection(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    /**
     * Getter for current id.
     * @return current id
     */
    public int getCurrentID() {
        return partitions.get(selectedPosition).getID();
    }

    /** View holder for simple item. */
    class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** Image button. */
        private final TextView text;
        /** Item beautyIcon. */
        private final ImageView icon;
        /** Item view. */
        private final View itemView;

        /**
         * Constructor with parameters.
         * @param itemView item view
         */
        public ViewHolderItem(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.itemView = itemView;
            icon = (ImageView) itemView.findViewById(R.id.drawer_icon);
            text = (TextView) itemView.findViewById(R.id.main_text);
        }

        @Override
        public void onClick(View view) {
            Partition partition = partitions.get(getAdapterPosition());
            listener.onDrawerItemClicked(view, getAdapterPosition(), partition.getID());
        }
    }

    /** View holder for user item. */
    class ViewHolderUser extends RecyclerView.ViewHolder implements View.OnClickListener {


        /**
         * Constructor with parameters.
         * @param itemView item view
         */
        public ViewHolderUser(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Partition partition = partitions.get(getAdapterPosition());
            listener.onDrawerItemClicked(view, getAdapterPosition(), partition.getID());
        }
    }
}
