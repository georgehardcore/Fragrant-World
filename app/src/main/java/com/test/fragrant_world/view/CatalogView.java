package com.test.fragrant_world.view;


import com.test.fragrant_world.model.Banner;
import com.test.fragrant_world.model.News;
import com.test.fragrant_world.model.Product;
import com.test.fragrant_world.model.Section;
import com.test.fragrant_world.model.TematicSet;

import java.util.List;

public interface CatalogView extends LoadingView {

    void setNews(List<News> newsList);

    void setTematicSets(List<TematicSet> tematicSets);

    void setBanners(List<Banner> banners);

    void setSections(List<Section> sections);

    void setProducts(List<Product> products);
}
