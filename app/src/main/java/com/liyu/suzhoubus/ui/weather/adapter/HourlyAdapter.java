package com.liyu.suzhoubus.ui.weather.adapter;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.HeWeather5;
import com.liyu.suzhoubus.utils.SizeUtils;
import com.liyu.suzhoubus.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by liyu on 2016/11/10.
 */

public class HourlyAdapter extends BaseQuickAdapter<HeWeather5.HourlyForecastBean, BaseViewHolder> {

    private int columnWidth = 0;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public HourlyAdapter(int layoutResId, List<HeWeather5.HourlyForecastBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HeWeather5.HourlyForecastBean item) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (columnWidth == 0) {
            columnWidth = getData().size() >= 5 ? SizeUtils.getScreenWidth(mContext, 8) / 5 : SizeUtils.getScreenWidth(mContext, 8) / getData().size();
        }
        layoutParams.width = columnWidth;
        holder.itemView.setLayoutParams(layoutParams);
        String hour = item.getDate();
        hour = TimeUtils.date2String(TimeUtils.string2Date(hour, sdf1), sdf2);
        holder.setText(R.id.tv_hourly_time, hour);
        holder.setText(R.id.tv_hourly_weather, item.getCond().getTxt());
        holder.setText(R.id.tv_hourly_temp, item.getTmp() + "℃");
    }
}
