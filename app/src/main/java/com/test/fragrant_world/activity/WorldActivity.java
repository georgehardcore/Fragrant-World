package com.test.fragrant_world.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.test.fragrant_world.R;
import com.test.fragrant_world.adapter.DrawerAdapter;
import com.test.fragrant_world.fragment.ActionsFragment;
import com.test.fragrant_world.fragment.CatalogFragment;
import com.test.fragrant_world.fragment.ProfileFragment;
import com.test.fragrant_world.fragment.SetsFragment;
import com.test.fragrant_world.fragment.ShopsFragment;

public class WorldActivity extends DrawerActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        drawerFragment.selectPartitionByID(R.id.nav_catalog);
    }

    @Override
    public void onPartitionSelected(int partitionID) {
        if (partitionID == R.id.nav_catalog) {
            setFragment(new CatalogFragment(), null, false);
        }
        if (partitionID == R.id.nav_sets) {
            setFragment(new SetsFragment(), null, false);
        }
        if (partitionID == R.id.nav_shops) {
            setFragment(new ShopsFragment(), null, false);
        }
        if (partitionID == R.id.nav_actions) {
            setFragment(new ActionsFragment(), null, false);
        }
        if (partitionID == R.id.nav_profile) {
            setFragment(new ProfileFragment(), null, false);
        }
        bottomNavigationView.setSelectedItemId(partitionID);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerFragment.selectPartitionByID(item.getItemId());
        return true;
    }
}
