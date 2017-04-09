package com.test.fragrant_world.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.test.fragrant_world.activity.NavigationActivity;


/** Fragment parents. */
@SuppressWarnings("PMD")
public class BaseFragment extends Fragment {

    /**
     * Handle on back pressed.
     * @param manager manager
     * @return true if manger or child manager pop back stack
     */
    public static boolean handleBackPressed(FragmentManager manager) {
        if (manager.getFragments() == null) return false;
        for (Fragment frag : manager.getFragments()) {
            if (frag == null) continue;
            if (frag.isVisible() && frag instanceof BaseFragment) {
                if (((BaseFragment) frag).onBackPressed()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * On back pressed.
     * @return on back pressed
     * */
    protected boolean onBackPressed() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        if (handleBackPressed(childFragmentManager)) {
            return true;
        } else if (getUserVisibleHint() && childFragmentManager.getBackStackEntryCount() > 0) {
            childFragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    /**
     * Getter for context.
     * @return parent activity
     */
    public NavigationActivity getContext() {
        return (NavigationActivity) getActivity();
    }
}
