package com.test.fragrant_world.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.fragrant_world.R;

public class ActionsFragment extends BaseFragment {


    private View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(
                R.layout.fragment_empty, container, false);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView label = (TextView) fragmentView.findViewById(R.id.label);
        label.setText(R.string.actions);
    }
}
