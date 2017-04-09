package com.test.fragrant_world.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.test.fragrant_world.R;
import com.test.fragrant_world.fragment.BaseFragment;

import java.util.List;


/** Activity fragment interface. */
@SuppressWarnings("PMD")
public abstract class NavigationActivity extends AppCompatActivity {

    /** Used to store the last screen title. */
    protected CharSequence currentTitle;
    /** Activity toolbar. */
    protected Toolbar toolbar;
    /** Action bar. */
    protected ActionBar actionBar;
    /** Current fragment number. */
    protected int currentFragmentCode = 0;
    /** Status bar color. */
    protected int statusBarColor;
    /** Toolbar color. */
    protected int toolbarColor;

    /**
     * Set title by title code.
     * @param titleCode code matching with title
     */
    public void setTitleByCode(int titleCode) {
        currentFragmentCode = titleCode;
    }

    /**
     * Setter for current title.
     * @param title title text
     */
    public void setCurrentTitle(String title) {
        actionBar.setTitle(title);
        currentTitle = title;
    }

    /**
     * Recursive searching for fragment in all fragment manager.
     * @param tag tag
     * @return fragment
     */
    public Fragment findFragment(String tag) {
        List<Fragment> activeFragments = getSupportFragmentManager().getFragments();
        Fragment searchedFragment;
        for (Fragment fragment: activeFragments) {
            if (fragment == null) continue;
            if (fragment.getClass().getSimpleName().equals(tag)) {
                return fragment;
            }
            searchedFragment = findInFragmentManager(fragment.getChildFragmentManager(), tag);
            if (searchedFragment != null) return searchedFragment;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if (!BaseFragment.handleBackPressed(getSupportFragmentManager())) {
            super.onBackPressed();
        }
    }

    /**
     * Set status bar color.
     * @param color color
     */
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT < 21) return;
        statusBarColor = color;
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }

    /**
     * Set toolbar color.
     * @param color color
     */
    public void setToolbarColor(int color) {
        toolbarColor = color;
        toolbar.setBackgroundColor(color);
    }

    /**
     * Find in child fragment manager.
     * @param childFragmentManager child fragment manager
     * @param tag tag
     * @return fragment
     */
    private Fragment findInFragmentManager(FragmentManager childFragmentManager, String tag) {
        List<Fragment> activeFragments = childFragmentManager.getFragments();
        if (activeFragments == null) return null;
        for (Fragment fragment: activeFragments) {
            if (fragment == null) continue;
            if (fragment.getClass().getSimpleName().equals(tag)) {
                return fragment;
            } else {
                findInFragmentManager(fragment.getChildFragmentManager(), tag);
            }
        }
        return null;
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
    public void setFragmentWithoutTransition(Fragment fragment, Bundle args,
                                             boolean isAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isAddToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        if (args != null) fragment.setArguments(args);
        if (isFinishing()) return;
        transaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
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

    /**
     * Getter for fragment code.
     * @return fragment code.
     */
    public int getCurrentFragmentCode() {
        return currentFragmentCode;
    }

    /** Init toolbar. */
    protected abstract void initToolbar();

    /**
     * On section attached.
     * @param titleCode current fragment position
     * */
    public void setupTitle(int titleCode) {
        currentFragmentCode = titleCode;
    }

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
     * Getter for status bar color.
     * @return status bar color
     */
    public int getStatusBarColor() {
        return statusBarColor;
    }

    /**
     * Getter for toolbar color.
     * @return toolbar color
     */
    public int getToolbarColor() {
        return toolbarColor;
    }

    /**
     * Getter for toolbar.
     * @return toolbar
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Setter for fragment code.
     * @param fragmentCode fragment code
     */
    public void setTitleCode(int fragmentCode) {
        this.currentFragmentCode = fragmentCode;
    }

}
