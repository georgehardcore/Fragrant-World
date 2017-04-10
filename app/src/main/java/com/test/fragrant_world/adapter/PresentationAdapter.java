package com.test.fragrant_world.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.fragrant_world.fragment.SlideFragment;
import com.test.fragrant_world.model.Banner;

import java.util.ArrayList;
import java.util.List;


/** PresentationAdapter adapter. */
@SuppressWarnings("PMD")
public class PresentationAdapter extends FragmentPagerAdapter {

    /** Images ids. */
    private List<Banner> banners = new ArrayList<>();

    /** PresentationAdapter constructor.
     * @param fragmentManager FragmentManager
     * */
    public PresentationAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public Fragment getItem(int position) {
        return SlideFragment.newInstance(banners.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
