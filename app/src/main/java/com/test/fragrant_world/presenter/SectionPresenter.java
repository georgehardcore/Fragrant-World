package com.test.fragrant_world.presenter;


import com.test.fragrant_world.model.Section;
import com.test.fragrant_world.view.SectionView;

public class SectionPresenter extends BasePresenter<Section, SectionView> {

    @Override
    protected void updateView() {
        view().setName(model.getName());
        view().setupSelection();
    }
}
