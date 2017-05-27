package com.liyu.fakeweather.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.model.WeatherBean;
import com.liyu.fakeweather.utils.SimpleSubscriber;
import com.liyu.fakeweather.utils.TimeUtils;
import com.liyu.fakeweather.utils.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2016/12/8.
 */

public class WeatherDetailsView extends LinearLayout {

    private List<HeWeather5.DailyForecastBean> dailyForecastList = new ArrayList<>();

    LayoutParams rowParams;

    public WeatherDetailsView(Context context) {
        this(context, null);
    }

    public WeatherDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
        rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    private void letItGo() {
        removeAllViews();
        HeWeather5.DailyForecastBean yesterday = WeatherUtil.getInstance().getYesterday();
        if (yesterday != null) {
            dailyForecastList.add(0, yesterday);
        }
        for (int i = 0; i < dailyForecastList.size(); i++) {
            View view = View.inflate(getContext(), R.layout.item_daily_weather, null);
            TextView tvTemp = (TextView) view.findViewById(R.id.tv_day_temp);
            TextView tvWeek = (TextView) view.findViewById(R.id.tv_day_week);
            TextView tvInfo = (TextView) view.findViewById(R.id.tv_day_info);
            final ImageView ivWeek = (ImageView) view.findViewById(R.id.iv_day_weather);
            if (yesterday != null) {
                if (i == 0) {
                    tvWeek.setText("昨天");
                } else if (i == 1) {
                    tvWeek.setText("今天");
                } else if (i == 2) {
                    tvWeek.setText("明天");
                } else {
                    tvWeek.setText(TimeUtils.getWeek(dailyForecastList.get(i).getDate(), TimeUtils.DATE_SDF));
                }
            } else {
                if (i == 0) {
                    tvWeek.setText("今天");
                } else if (i == 1) {
                    tvWeek.setText("明天");
                } else {
                    tvWeek.setText(TimeUtils.getWeek(dailyForecastList.get(i).getDate(), TimeUtils.DATE_SDF));
                }
            }

            tvTemp.setText(String.format(("%s℃ - %s℃"), dailyForecastList.get(i).getTmp().getMin(), dailyForecastList.get(i).getTmp().getMax()));
            tvInfo.setText(String.format("%s，降雨几率：%s%%，%s %s 级", dailyForecastList.get(i).getCond().getTxt_d(), dailyForecastList.get(i).getPop(), dailyForecastList.get(i).getWind().getDir(), dailyForecastList.get(i).getWind().getSc()));

            WeatherUtil.getInstance().getWeatherDict(dailyForecastList.get(i).getCond().getCode_d()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleSubscriber<WeatherBean>() {
                @Override
                public void onNext(WeatherBean weatherBean) {
                    Glide.with(getContext()).load(weatherBean.getIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivWeek);
                }
            });
            addView(view, rowParams);
        }
    }

    public void setWeather5(HeWeather5 weather5) {
        dailyForecastList.clear();
        dailyForecastList.addAll(weather5.getDaily_forecast());
        letItGo();
    }
}
