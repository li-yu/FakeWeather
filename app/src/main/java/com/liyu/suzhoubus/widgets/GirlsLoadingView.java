package com.liyu.suzhoubus.widgets;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by liyu on 2016/12/9.
 */

public class GirlsLoadingView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected int getLoadingViewId() {
        return 0;
    }

    @Override
    protected int getLoadFailViewId() {
        return 0;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
