package com.liyu.suzhoubus.ui.bus.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.BusLineStation;
import com.liyu.suzhoubus.ui.bus.LineDetailActivity;

import java.util.List;

/**
 * Created by liyu on 2016/11/1.
 */

public class LineStationAdapter extends BaseQuickAdapter<BusLineStation.ListBean, BaseViewHolder> {

    public LineStationAdapter(int layoutResId, List<BusLineStation.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final BusLineStation.ListBean item) {
        holder.setText(R.id.tv_line_name, item.getLName());
        holder.setText(R.id.tv_line_desc, item.getLDirection());
        holder.setText(R.id.tv_line_direction, item.getDistince_str());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineDetailActivity.start(mContext, item.getGuid(), item.getLName(), item.getLDirection());
            }
        });
    }
}
