package com.liyu.fakeweather.ui.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.FakeWeather;
import com.liyu.fakeweather.model.WeatherBean;
import com.liyu.fakeweather.utils.SimpleSubscriber;
import com.liyu.fakeweather.utils.SizeUtils;
import com.liyu.fakeweather.utils.TimeUtils;
import com.liyu.fakeweather.utils.WeatherUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2017/8/25.
 */

public class HourlyAdapter extends BaseQuickAdapter<FakeWeather.FakeForecastHourly, BaseViewHolder> {

    public HourlyAdapter(int layoutResId, List<FakeWeather.FakeForecastHourly> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, FakeWeather.FakeForecastHourly item) {
        int width = SizeUtils.getScreenWidth(mContext) / 5;
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.itemView.getLayoutParams();
        params.width = width;
        helper.itemView.setLayoutParams(params);
        helper.setText(R.id.tv_hourly_temp, item.getTemp() + "°");
        helper.setText(R.id.tv_hourly_time, item.getTime());
        final ImageView imageView = helper.getView(R.id.iv_hourly_weather);
        WeatherUtil.getInstance().getWeatherDict(item.getCode()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleSubscriber<WeatherBean>() {
            @Override
            public void onNext(WeatherBean weatherBean) {
                Glide.with(mContext).load(weatherBean.getIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }
        });
    }
}
