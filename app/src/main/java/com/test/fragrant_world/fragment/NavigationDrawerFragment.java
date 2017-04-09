package com.test.fragrant_world.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.fragrant_world.App;
import com.test.fragrant_world.BuildConfig;
import com.test.fragrant_world.R;
import com.test.fragrant_world.activity.WorldActivity;
import com.test.fragrant_world.adapter.DrawerAdapter;
import com.test.fragrant_world.listener.DrawerClickListener;
import com.test.fragrant_world.listener.PartitionSelectedListener;


/** Navigation drawer fragment. */
@SuppressWarnings("PMD")
public class NavigationDrawerFragment extends Fragment implements DrawerClickListener {

    /** Remember the position of the selected item. */
    private static final String SELECTED_DRAWER_POSITION = "selected_drawer_position";
    /** Helper component that ties the action bar to the navigation drawer. */
    private ActionBarDrawerToggle navDrawerToggle;
    /** Navigation drawer layout. */
    private DrawerLayout drawerLayout;
    /** Drawer list view. */
    private RecyclerView drawerList;
    /** Current fragment. */
    private View fragmentContainerView;
    /** Current selected drawer item position. */
    private int selectedPosition = -1;
    /** Drawer adapter. */
    private DrawerAdapter drawerAdapter;
    /** Drawer enable flag. */
    private boolean enabled;

    private PartitionSelectedListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerAdapter = new DrawerAdapter(this);
        drawerAdapter.update();
        if (savedInstanceState != null) {
            selectedPosition = savedInstanceState.getInt(SELECTED_DRAWER_POSITION);
        }
    }

    @Override
    public void onDrawerItemClicked(View view, int position, int partitionID) {
        selectItem(position, partitionID);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        drawerList.setAdapter(drawerAdapter);
    }

    /**
     * Getter for version.
     * @return version name
     */
    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * Getter for current id.
     * @return current id
     */
    public int getCurrentID() {
        return drawerAdapter.getCurrentID();
    }

    /** Open drawer. */
    public void open() {
        drawerLayout.openDrawer(fragmentContainerView);
    }

    /** Close drawer. */
    public void close() {
        drawerLayout.closeDrawer(fragmentContainerView);
    }

    /** Disable navigation drawer toggle and gesture calling side menu. */
    public void disable() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navDrawerToggle.setDrawerIndicatorEnabled(false);
        navDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_action_up);
        enabled = false;
    }

    /** Enable navigation drawer toggle and gesture calling side menu. */
    public void enable() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        navDrawerToggle.setDrawerIndicatorEnabled(true);
        enabled = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_navigation_drawer,
                container, false);
        drawerList = (RecyclerView) fragmentView.findViewById(R.id.navigation_list);
        drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return fragmentView;
    }

    /** Updates adapter. */
    public void updateAdapter() {
        drawerAdapter.update();
    }

    /**
     *  Return drawer open state.
     * @return drawer open state
     * */
    public boolean isDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
    }

    /** Set up navigation drawer. */
    public void setUp(PartitionSelectedListener listener) {
        drawerLayout = (DrawerLayout) getContext().findViewById(R.id.drawer_layout);
        fragmentContainerView = getActivity().findViewById(R.id.navigation_drawer);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        setUpDrawerToggle();
        this.listener =  listener;
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                navDrawerToggle.syncState();
            }
        });
        drawerLayout.addDrawerListener(navDrawerToggle);
    }

    /** Set up navigation drawer toggle. */
    private void setUpDrawerToggle() {
        navDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, getContext().getToolbar(), 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                App.hideKeyboard(getActivity());
            }
        };
        navDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    /**
     * Select current fragment item.
     * @param position current selected item position
     * @param id current selected position id
     **/
    public void selectItem(int position, int id) {
        if (selectedPosition == position && drawerLayout != null && isDrawerOpen()) {
            drawerLayout.closeDrawer(fragmentContainerView);
            return;
        }
        if (selectedPosition == position) return;
        drawerAdapter.setSelection(position);
        selectedPosition = position;
        if (listener != null) listener.onPartitionSelected(id);
        closeDrawerWithDelay();
    }
    /**
     * Select partition by id.
     * @param id id
     */
    public void selectPartitionByID(int id) {
        int position = drawerAdapter.getPositionById(id);
        selectItem(position, id);
    }

    /** Close drawer with delay. */
    private void closeDrawerWithDelay() {
        if (drawerLayout != null && fragmentContainerView != null) {
            fragmentContainerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(fragmentContainerView);
                }
            }, 200);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_DRAWER_POSITION, selectedPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Is drawer enabled.
     * @return true if enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Get parent activity.
     * @return parent activity
     */
    public WorldActivity getContext() {
        return (WorldActivity) getActivity();
    }

}
