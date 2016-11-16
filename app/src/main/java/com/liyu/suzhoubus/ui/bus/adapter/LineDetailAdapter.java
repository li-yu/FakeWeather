package com.liyu.suzhoubus.ui.bus.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.BusLineDetail;
import com.liyu.suzhoubus.model.StandInfoBean;
import com.liyu.suzhoubus.ui.bus.StationDetailActivity;
import com.liyu.suzhoubus.widgets.StationIndicator;

import java.util.List;

/**
 * Created by liyu on 2016/11/1.
 */

public class LineDetailAdapter extends BaseQuickAdapter<StandInfoBean, BaseViewHolder> {

    public LineDetailAdapter(int layoutResId, List<StandInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final StandInfoBean item) {
        holder.setText(R.id.tv_station_name, item.getSName());
        holder.setText(R.id.tv_station_status, item.getS_num_str());
        StationIndicator indicator = holder.getView(R.id.bus_indicator);
        if (item.getIs_vicinity() == 0 && !TextUtils.isEmpty(item.getS_num_str())) {
            indicator.setChecked(true);
        } else {
            indicator.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StationDetailActivity.start(mContext, item.getSName(), item.getSCode());
            }
        });
    }
}
