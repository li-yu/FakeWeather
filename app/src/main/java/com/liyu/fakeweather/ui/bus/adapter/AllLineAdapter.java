package com.liyu.fakeweather.ui.bus.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.LPLine;
import com.liyu.fakeweather.ui.bus.LineDetailActivity;
import com.liyu.fakeweather.utils.TimeUtils;

import java.util.List;

/**
 * Created by liyu on 2017/2/3.
 */

public class AllLineAdapter extends BaseQuickAdapter<LPLine, BaseViewHolder> {

    public AllLineAdapter(int layoutResId, List<LPLine> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final LPLine lpLine) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineDetailActivity.start(mContext, lpLine.getId(), lpLine.getName() + "路", String.format("%s->%s", lpLine.getFromWhere(), lpLine.getToWhere()));
            }
        });
        holder.setText(R.id.tv_line_name, lpLine.getName());
        holder.setText(R.id.tv_line_direction, lpLine.getDirection());
        holder.setText(R.id.tv_line_time, String.format("%s-%s", TimeUtils.string2String(lpLine.getStartTime(), TimeUtils.DEFAULT_SDF, TimeUtils.HOUR_SDF), TimeUtils.string2String(lpLine.getEndTime(), TimeUtils.DEFAULT_SDF, TimeUtils.HOUR_SDF)));
        if (lpLine.getMinTime().startsWith("固定") || lpLine.getMinTime().startsWith("定时"))
            holder.setText(R.id.tv_line_freq, lpLine.getMinTime());
        else
            holder.setText(R.id.tv_line_freq, String.format("%s-%s分钟", lpLine.getMinTime(), lpLine.getMaxTime()));
    }
}
