package com.liyu.fakeweather.ui.weather;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by liyu on 2017/8/31.
 */

public class WeatherPageTransformer implements ViewPager.PageTransformer {
    /**
     * Apply a property transformation to the given page.
     *
     * @param page     Apply the transformation to this page
     * @param position Position of page relative to the current front-and-center
     *                 position of the pager. 0 is front and center. 1 is one full
     */
    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0) {
            page.setAlpha(1 + position);
        } else if (position <= 1) {
            page.setAlpha(1 - position);
        } else {
            page.setAlpha(0);
        }
        page.setTranslationX(position);
    }
}
