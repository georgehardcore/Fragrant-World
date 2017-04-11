package com.test.fragrant_world.presenter;


import com.test.fragrant_world.model.Partition;
import com.test.fragrant_world.view.DrawerItem;
import com.test.fragrant_world.view.DrawerView;

public class DrawerPresenter extends BasePresenter<Partition, DrawerView> {

    @Override
    protected void updateView() {
        if (view() instanceof DrawerItem) {
            DrawerItem drawerItem = (DrawerItem) view();
            drawerItem.setName(model.getName());
            drawerItem.setImage(model.getImageID());
            drawerItem.setupSelection();
        }
    }
}
