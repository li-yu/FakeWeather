package com.liyu.fakeweather.ui.bus.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.BusLineNearby;
import com.liyu.fakeweather.ui.bus.LineDetailActivity;

import java.util.List;

/**
 * Created by liyu on 2016/11/1.
 */

public class LineNearbyAdapter extends BaseQuickAdapter<BusLineNearby.LineBean, BaseViewHolder> {

    public LineNearbyAdapter(int layoutResId, List<BusLineNearby.LineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final BusLineNearby.LineBean item) {
        holder.setText(R.id.tv_line_name, item.getLName());
        holder.setText(R.id.tv_line_desc, item.getVicinity_station_info());
        holder.setText(R.id.tv_line_direction, item.getDistince_str());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineDetailActivity.start(mContext, item.getGuid(), item.getLName(), item.getLDirection());
            }
        });
    }
}
