package com.test.fragrant_world.listener;


import android.view.View;

/** Drawer click listener. */
public interface DrawerClickListener {

    /**
     * On drawer item clicked.
     * @param view view
     * @param position position
     * @param partitionID partition id
     */
    void onDrawerItemClicked(View view, int position, int partitionID);
}
