package com.test.fragrant_world.presenter;


import android.support.annotation.NonNull;

import com.test.fragrant_world.App;
import com.test.fragrant_world.R;
import com.test.fragrant_world.model.Partition;
import com.test.fragrant_world.view.NavigationDrawerView;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerPresenter extends BasePresenter<List<Partition>, NavigationDrawerView> {


    public static final int LOGO = 11;
    /** Current selected drawer item position. */
    private int selectedPosition = -1;

    @Override
    public void bindView(@NonNull NavigationDrawerView view) {
        super.bindView(view);
        if (model == null) {
            init();
            updateView();
        } else {
            updateView();
        }
    }

    /** Init model. */
    public void init() {
        model = new ArrayList<>();
        model.add(new Partition("", LOGO));
        model.add(new Partition(App.getStr(R.string.catalog),
                R.drawable.ic_action_catalog, R.id.nav_catalog));
        model.add(new Partition(App.getStr(R.string.sets),
                R.drawable.ic_action_sets, R.id.nav_sets));
        model.add(new Partition(App.getStr(R.string.shops),
                R.drawable.ic_action_shops, R.id.nav_shops));
        model.add(new Partition(App.getStr(R.string.actions),
                R.drawable.ic_action_actions, R.id.nav_actions));
        model.add(new Partition(App.getStr(R.string.profile),
                R.drawable.ic_action_profile, R.id.nav_profile));
    }

    /**
     * Get position by partition id.
     * @param id id
     * @return position
     */
    public int getPositionById(int id) {
        for (Partition partition: model) {
            if (partition.getID() == id) {
                return model.indexOf(partition);
            }
        }
        return -1;
    }

    @Override
    protected void updateView() {
        view().setDrawerItems(model);
        view().selectPartitionByID(selectedPosition == -1
                ? R.id.nav_catalog
                : model.get(selectedPosition).getID());
    }

    public int getSelection() {
        return selectedPosition;
    }

    public void selectItem(int position) {
        view().selectPartitionByID(model.get(position).getID());
    }

    public void setSelection(int selection) {
        this.selectedPosition = selection;
    }
}
