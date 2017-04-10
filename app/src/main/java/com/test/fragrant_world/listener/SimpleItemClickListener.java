package com.test.fragrant_world.listener;

import android.view.View;

/** View holder click interface. */
public interface SimpleItemClickListener {

    /**
     * On item click listener.
     * @param view     current clicked view
     * @param position current clicked position
     */
    void onItemClick(View view, int position);
}
