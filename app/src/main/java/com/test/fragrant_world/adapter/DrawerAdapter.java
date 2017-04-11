package com.test.fragrant_world.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.fragment.NavigationDrawerFragment;
import com.test.fragrant_world.listener.DrawerClickListener;
import com.test.fragrant_world.model.Partition;
import com.test.fragrant_world.presenter.DrawerPresenter;
import com.test.fragrant_world.view.DrawerItem;
import com.test.fragrant_world.view.LogoItem;
import com.test.fragrant_world.view.MvpViewHolder;

/** Drawer item layout adapter. */
@SuppressWarnings("PMD")
public class DrawerAdapter extends MvpRecyclerListAdapter<Partition, DrawerPresenter, MvpViewHolder<DrawerPresenter>> {


    /** Selected position. */
    private int selectedPosition;
    /** Drawer click listener. */
    private final DrawerClickListener listener;

    /**
     * Constructor without parameters.
     * @param listener click callback
     * */
    public DrawerAdapter(DrawerClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MvpViewHolder<DrawerPresenter> onCreateViewHolder(ViewGroup viewGroup, int layoutID) {
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

    @Override
    public int getItemViewType(int position) {
        Partition partition = getItem(position);
        if (partition.getID() == NavigationDrawerFragment.LOGO) {
            return R.layout.list_item_drawer_user;
        } else {
            return R.layout.list_item_drawer_simple;
        }
    }

    /**
     * Set selection of clicked item.
     * @param position selected position
     */
    public void setSelection(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    protected DrawerPresenter onCreatePresenter(@NonNull Partition model) {
        DrawerPresenter presenter = new DrawerPresenter();
        presenter.setModel(model);
        return presenter;
    }

    @Override
    protected Object getModelId(@NonNull Partition model) {
        return model.getID();
    }

    /** View holder for simple item. */
    class ViewHolderItem extends MvpViewHolder<DrawerPresenter> implements View.OnClickListener, DrawerItem {

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
            Partition partition = getItem(getAdapterPosition());
            listener.onDrawerItemClicked(view, getAdapterPosition(), partition.getID());
        }

        @Override
        public void setName(String name) {
            text.setText(name);
        }

        @Override
        public void setImage(int imageID) {
            icon.setBackground(App.getImage(imageID));
        }

        @Override
        public void setupSelection() {
            boolean selected = getAdapterPosition() == selectedPosition;
            itemView.setBackgroundColor(App.getClr(selected
                    ? R.color.drawer_selected_color
                    : R.color.white));
        }
    }

    /** View holder for user item. */
    class ViewHolderUser extends  MvpViewHolder<DrawerPresenter> implements LogoItem {

        /**
         * Constructor with parameters.
         * @param itemView item view
         */
        public ViewHolderUser(View itemView) {
            super(itemView);
        }

    }
}
