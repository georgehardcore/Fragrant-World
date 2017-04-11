package com.test.fragrant_world.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.test.fragrant_world.R;
import com.test.fragrant_world.fragment.BaseFragment;


/** Activity fragment interface. */
@SuppressWarnings("PMD")
public abstract class NavigationActivity extends AppCompatActivity {

    /** Used to store the last screen title. */
    protected CharSequence currentTitle;
    /** Activity toolbar. */
    protected Toolbar toolbar;
    /** Action bar. */
    protected ActionBar actionBar;

    /**
     * Setter for current title.
     * @param title title text
     */
    public void setCurrentTitle(String title) {
        actionBar.setTitle(title);
        currentTitle = title;
    }

    @Override
    public void onBackPressed() {
        if (!BaseFragment.handleBackPressed(getSupportFragmentManager())) {
            super.onBackPressed();
        }
    }

    /** Clear activity back stack. */
    protected void clearBackStack() {
        if (getSupportFragmentManager() != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                String name = getSupportFragmentManager().getBackStackEntryAt(0).getName();
                getSupportFragmentManager().popBackStack(name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    /**
     * Setter fragment by fragment instance.
     * @param fragment fragment
     * @param args args
     * @param isAddToBackStack is need to add to backstack
     */
    public void setFragment(Fragment fragment, Bundle args, boolean isAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isAddToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.setCustomAnimations(R.anim.tr_child_up, R.anim.transition_exit_left,
                    R.anim.tr_parent_back, R.anim.tr_child_back);
        }
        if (args != null) fragment.setArguments(args);
        if (isFinishing()) return;
        transaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    /** Init toolbar. */
    protected abstract void initToolbar();

    /** Setup action bar. */
    protected void setupActionBar() {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_up);
    }

    /**
     * Get fragment by tag (ClassFragment.class.getSimpleName).
     * @param tag tag
     * @return fragment instance
     */
    public Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    /**
     * Getter for toolbar.
     * @return toolbar
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

}
