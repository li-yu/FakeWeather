package com.liyu.fakeweather.ui.weather.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.AqiDetailBean;

import java.util.List;

/**
 * Created by liyu on 2017/8/28.
 */

public class AqiAdapter extends BaseQuickAdapter<AqiDetailBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public AqiAdapter(int layoutResId, List<AqiDetailBean> data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, AqiDetailBean item) {
        helper.setText(R.id.tv_aqi_name, item.getName());
        helper.setText(R.id.tv_aqi_desc, item.getDesc());
        helper.setText(R.id.tv_aqi_value, item.getValue() + "");
        int value = TextUtils.isDigitsOnly(item.getValue()) ? Integer.parseInt(item.getValue()) : 0;
        if (value <= 50) {
            helper.setBackgroundColor(R.id.view_aqi_qlty, 0xFF6BCD07);
        } else if (value <= 100) {
            helper.setBackgroundColor(R.id.view_aqi_qlty, 0xFFFBD029);
        } else if (value <= 150) {
            helper.setBackgroundColor(R.id.view_aqi_qlty, 0xFFFE8800);
        } else if (value <= 200) {
            helper.setBackgroundColor(R.id.view_aqi_qlty, 0xFFFE0000);
        } else if (value <= 300) {
            helper.setBackgroundColor(R.id.view_aqi_qlty, 0xFF970454);
        } else {
            helper.setBackgroundColor(R.id.view_aqi_qlty, 0xFF62001E);
        }
    }
}
