package com.test.fragrant_world.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.test.fragrant_world.R;
import com.test.fragrant_world.fragment.NavigationDrawerFragment;
import com.test.fragrant_world.listener.PartitionSelectedListener;

/** Drawer abstract activity. */
public abstract class DrawerActivity extends NavigationActivity implements PartitionSelectedListener {

    /** Fragment managing navigation drawer. */
    protected NavigationDrawerFragment drawerFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        drawerFragment.setUp(this);
        drawerFragment.enable();
        initToolbar();
    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        setupActionBar();
        toolbar.setNavigationOnClickListener(createDrawerClick());
    }

    /**
     * Create drawer click.
     * @return drawer click
     */
    private View.OnClickListener createDrawerClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerFragment.isEnabled()) {
                    if (drawerFragment.isDrawerOpen()) {
                        drawerFragment.close();
                    } else {
                        drawerFragment.open();
                    }
                } else {
                    DrawerActivity.this.onBackPressed();
                }
            }
        };
    }

    /**
     * Open drawer.
     */
    public void openDrawer() {
        drawerFragment.open();
    }


    /** Update navigation drawer state. */
    public void updateDrawer() {
        drawerFragment.updateAdapter();
    }

    /**
     * Disable navigation drawer.
     */
    public void disableDrawer() {
        drawerFragment.disable();
    }

    /**
     * Enable navigation drawer.
     */
    public void enableDrawer() {
        drawerFragment.enable();
    }

    /** Restore action bar on closing drawer. */
    public void restoreActionBar() {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(currentTitle);
    }

    /**
     * Getter for navigation drawer fragment.
     *
     * @return navigation drawer fragment
     */
    public NavigationDrawerFragment getDrawerFragment() {
        return drawerFragment;
    }
}
