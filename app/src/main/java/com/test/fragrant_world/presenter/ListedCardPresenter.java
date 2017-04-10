package com.test.fragrant_world.presenter;

import android.view.View;

import com.test.fragrant_world.model.ListedCard;
import com.test.fragrant_world.view.ListedCardView;


public class ListedCardPresenter extends BasePresenter<ListedCard, ListedCardView> {


    @Override
    protected void updateView() {
        view().setDescription(model.getDescription());
        view().loadImage(model.getImg());
    }

}
