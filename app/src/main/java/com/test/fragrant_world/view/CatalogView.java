package com.test.fragrant_world.view;


import com.nostra13.universalimageloader.utils.L;
import com.test.fragrant_world.model.Banner;
import com.test.fragrant_world.model.ListedCard;
import com.test.fragrant_world.model.Product;
import com.test.fragrant_world.model.Section;

import java.util.List;

public interface CatalogView extends BaseView {

    void showNews(List<ListedCard> newsList);

    void showTematicSets(List<ListedCard> tematicSets);

    void showBanners(List<Banner> banners);

    void showSections(List<Section> sections);

    void showPrducts(List<Product> products);
}
