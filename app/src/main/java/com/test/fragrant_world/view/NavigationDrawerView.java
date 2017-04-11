package com.test.fragrant_world.view;


import com.test.fragrant_world.model.Partition;

import java.util.List;

public interface NavigationDrawerView {

    void setDrawerItems(List<Partition> model);

    void selectPartitionByID(int id);
}
