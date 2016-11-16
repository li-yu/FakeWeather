package com.liyu.suzhoubus.ui.bus.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.BusLineShort;
import com.liyu.suzhoubus.ui.bus.LineDetailActivity;

import java.util.List;

/**
 * Created by liyu on 2016/11/1.
 */

public class LineAdapter extends BaseQuickAdapter<BusLineShort.LineInfo, BaseViewHolder> {

    public LineAdapter(int layoutResId, List<BusLineShort.LineInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final BusLineShort.LineInfo item) {
        holder.setText(R.id.tv_line_name, item.getLName());
        holder.setText(R.id.tv_line_desc, item.getLDirection());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineDetailActivity.start(mContext, item.getGuid(), item.getLName(), item.getLDirection());
            }
        });
    }
}
