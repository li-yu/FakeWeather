package com.liyu.fakeweather.event;

import com.liyu.fakeweather.model.FavoritesBusBean;

/**
 * Created by liyu on 2016/11/29.
 */

public class BusFavoritesEvent {

    private boolean isFavorite;

    private FavoritesBusBean data;

    public BusFavoritesEvent(FavoritesBusBean bean) {
        this.data = bean;
    }

    public FavoritesBusBean getFavorite() {
        return data;
    }

    public void setFavorite(FavoritesBusBean favorite) {
        this.data = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
