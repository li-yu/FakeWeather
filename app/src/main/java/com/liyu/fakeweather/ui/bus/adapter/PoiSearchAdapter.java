package com.liyu.fakeweather.ui.bus.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.AmapPoi;

import java.util.List;

/**
 * Created by liyu on 2016/11/1.
 */

public class PoiSearchAdapter extends BaseQuickAdapter<AmapPoi.PoisBean, BaseViewHolder> {

    public PoiSearchAdapter(int layoutResId, List<AmapPoi.PoisBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final AmapPoi.PoisBean item) {
        holder.setText(R.id.tv_line_name, item.getName());
        holder.setText(R.id.tv_line_desc, item.getAddress());
    }
}
