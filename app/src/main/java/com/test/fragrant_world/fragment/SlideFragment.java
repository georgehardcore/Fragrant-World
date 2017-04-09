package com.test.fragrant_world.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.model.Banner;

import util.Constants;


/** SlideFragment fragment. */
@SuppressWarnings("PMD")
public class SlideFragment extends Fragment {

    /** View fragmentView. */
    private View fragmentView;

    /**
     * SlideFragment newinstance method.
     * @param banner banner instance
     * @return fragment newInstance Fragment
     * */
    public static SlideFragment newInstance(Banner banner) {
        SlideFragment fragment = new SlideFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Args.BANNER, banner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(
                R.layout.fragment_slide, container, false);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageView = (ImageView) fragmentView.findViewById(R.id.image);
        Banner banner = getArguments().getParcelable(Constants.Args.BANNER);
        ImageLoader.getInstance().displayImage(banner.getImage(), imageView, App.getOptions());
        ((TextView) fragmentView.findViewById(R.id.header)).setText(banner.getHeader());
        ((TextView) fragmentView.findViewById(R.id.description)).setText(banner.getDescription());
    }
}

