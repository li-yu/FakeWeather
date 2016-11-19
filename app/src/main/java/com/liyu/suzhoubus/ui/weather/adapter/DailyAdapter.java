package com.liyu.suzhoubus.ui.weather.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.HeWeather5;
import com.liyu.suzhoubus.model.WeatherBean;
import com.liyu.suzhoubus.utils.SimpleSubscriber;
import com.liyu.suzhoubus.utils.WeatherUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2016/11/10.
 */

public class DailyAdapter extends BaseQuickAdapter<HeWeather5.DailyForecastBean, BaseViewHolder> {

    public DailyAdapter(int layoutResId, List<HeWeather5.DailyForecastBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HeWeather5.DailyForecastBean item) {
        final ImageView iv = holder.getView(R.id.iv_day_weather);
        WeatherUtil.getWeatherDict(item.getCond().getCode_d()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleSubscriber<WeatherBean>() {
            @Override
            public void onNext(WeatherBean weatherBean) {
                Glide.with(mContext).load(weatherBean.getIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
            }
        });
        if (holder.getAdapterPosition() == 0) {
            holder.setText(R.id.tv_day_week, "今天");
        } else if (holder.getAdapterPosition() == 1) {
            holder.setText(R.id.tv_day_week, "明天");
        } else {
            holder.setText(R.id.tv_day_week, item.getDate());
        }
        holder.setText(R.id.tv_day_temp, String.format(("%s℃ - %s℃"), item.getTmp().getMin(), item.getTmp().getMax()));
        holder.setText(R.id.tv_day_info, String.format("%s，%s%s级", item.getCond().getTxt_d(), item.getWind().getDir(), item.getWind().getSc()));
    }
}
