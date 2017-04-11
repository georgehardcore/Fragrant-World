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
import com.test.fragrant_world.R;
import com.test.fragrant_world.activity.WorldActivity;
import com.test.fragrant_world.adapter.DrawerAdapter;
import com.test.fragrant_world.listener.DrawerClickListener;
import com.test.fragrant_world.listener.PartitionSelectedListener;
import com.test.fragrant_world.model.Partition;
import com.test.fragrant_world.presenter.NavigationDrawerPresenter;
import com.test.fragrant_world.view.NavigationDrawerView;

import java.util.List;


/** Navigation drawer fragment. */
@SuppressWarnings("PMD")
public class NavigationDrawerFragment extends Fragment implements DrawerClickListener, NavigationDrawerView {


    /** Helper component that ties the action bar to the navigation drawer. */
    private ActionBarDrawerToggle navDrawerToggle;
    /** Navigation drawer layout. */
    private DrawerLayout drawerLayout;
    /** Drawer list view. */
    private RecyclerView drawerList;
    /** Current fragment. */
    private View fragmentView;
    /** Drawer adapter. */
    private DrawerAdapter drawerAdapter;
    /** Drawer enable flag. */
    private boolean enabled;

    private PartitionSelectedListener listener;

    private NavigationDrawerPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NavigationDrawerPresenter();
        drawerAdapter = new DrawerAdapter(this);
    }

    @Override
    public void setDrawerItems(List<Partition> model) {
        drawerAdapter.setAll(model);
    }

    @Override
    public void onDrawerItemClicked(View view, int position, int partitionID) {
        selectItem(position);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        drawerList.setAdapter(drawerAdapter);
    }

    /** Open drawer. */
    public void open() {
        drawerLayout.openDrawer(fragmentView);
    }

    /** Close drawer. */
    public void close() {
        drawerLayout.closeDrawer(fragmentView);
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
        drawerAdapter.notifyDataSetChanged();
    }

    /**
     *  Return drawer open state.
     * @return drawer open state
     * */
    public boolean isDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentView);
    }

    /** Set up navigation drawer. */
    public void setUp(PartitionSelectedListener listener) {
        drawerLayout = (DrawerLayout) getContext().findViewById(R.id.drawer_layout);
        fragmentView = getActivity().findViewById(R.id.navigation_drawer);
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
     **/
    public void selectItem(int position) {
        presenter.selectItem(position);
    }

    @Override
    public void selectPartitionByID(int id) {
        int position = presenter.getPositionById(id);
        int selection = presenter.getSelection();
        if (selection == position && drawerLayout != null && isDrawerOpen()) {
            drawerLayout.closeDrawer(fragmentView);
            return;
        }
        if (selection == position) return;
        drawerAdapter.setSelection(position);
        presenter.setSelection(position);
        if (listener != null) listener.onPartitionSelected(id);
        closeDrawerWithDelay();
    }

    /** Close drawer with delay. */
    private void closeDrawerWithDelay() {
        if (drawerLayout != null && fragmentView != null) {
            fragmentView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(fragmentView);
                }
            }, 200);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindView();
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
