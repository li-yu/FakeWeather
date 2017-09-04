package com.liyu.fakeweather.widgets;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.model.WeatherBean;
import com.liyu.fakeweather.utils.SimpleSubscriber;
import com.liyu.fakeweather.utils.SizeUtils;
import com.liyu.fakeweather.utils.TimeUtils;
import com.liyu.fakeweather.utils.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2016/12/8.
 */

public class WeatherChartView extends LinearLayout {

    private boolean canRefresh = true;

    private List<HeWeather5.DailyForecastBean> dailyForecastList = new ArrayList<>();

    LinearLayout.LayoutParams cellParams;
    LinearLayout.LayoutParams rowParams;
    LinearLayout.LayoutParams chartParams;

    LayoutTransition transition = new LayoutTransition();

    public WeatherChartView(Context context) {
        this(context, null);
    }

    public WeatherChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
        transition.enableTransitionType(LayoutTransition.APPEARING);
        this.setLayoutTransition(transition);
        rowParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        cellParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        chartParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    private void letItGo() {
        removeAllViews();
        final LinearLayout dateTitleView = new LinearLayout(getContext());
        dateTitleView.setLayoutParams(rowParams);
        dateTitleView.setOrientation(HORIZONTAL);
        dateTitleView.setLayoutTransition(transition);
        dateTitleView.removeAllViews();

        final LinearLayout iconView = new LinearLayout(getContext());
        iconView.setLayoutParams(rowParams);
        iconView.setOrientation(HORIZONTAL);
        iconView.setLayoutTransition(transition);
        iconView.removeAllViews();

        final LinearLayout weatherStrView = new LinearLayout(getContext());
        weatherStrView.setLayoutParams(rowParams);
        weatherStrView.setOrientation(HORIZONTAL);
        weatherStrView.setLayoutTransition(transition);
        weatherStrView.removeAllViews();

        List<Integer> minTemp = new ArrayList<>();
        List<Integer> maxTemp = new ArrayList<>();
        HeWeather5.DailyForecastBean yesterday = WeatherUtil.getInstance().getYesterday();
        if (yesterday != null) {
            dailyForecastList.add(0, yesterday);
        }
        for (int i = 0; i < dailyForecastList.size(); i++) {
            final TextView tvDate = new TextView(getContext());
            tvDate.setGravity(Gravity.CENTER);
            tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            tvDate.setTextColor(getResources().getColor(R.color.colorTextDark));
            tvDate.setVisibility(INVISIBLE);
            final TextView tvWeather = new TextView((getContext()));
            tvWeather.setGravity(Gravity.CENTER);
            tvWeather.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            tvWeather.setTextColor(getResources().getColor(R.color.colorTextDark));
            tvWeather.setVisibility(INVISIBLE);
            final ImageView ivIcon = new ImageView(getContext());
            ivIcon.setAdjustViewBounds(true);
            ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int padding = SizeUtils.dp2px(getContext(), 10);
            int width = SizeUtils.getScreenWidth(getContext()) / dailyForecastList.size();
            LayoutParams ivParam = new LayoutParams(width, width);
            ivParam.weight = 1;
            ivIcon.setLayoutParams(ivParam);
            ivIcon.setPadding(padding, padding, padding, padding);
            ivIcon.setVisibility(INVISIBLE);
            if (yesterday != null) {
                if (i == 0) {
                    tvDate.setText("昨天");
                } else if (i == 1) {
                    tvDate.setText("今天");
                } else if (i == 2) {
                    tvDate.setText("明天");
                } else {
                    tvDate.setText(TimeUtils.getWeek(dailyForecastList.get(i).getDate(), TimeUtils.DATE_SDF));
                }
            } else {
                if (i == 0) {
                    tvDate.setText("今天");
                } else if (i == 1) {
                    tvDate.setText("明天");
                } else {
                    tvDate.setText(TimeUtils.getWeek(dailyForecastList.get(i).getDate(), TimeUtils.DATE_SDF));
                }
            }
            tvWeather.setText(dailyForecastList.get(i).getCond().getTxt_d());
            WeatherUtil.getInstance().getWeatherDict(dailyForecastList.get(i).getCond().getCode_d()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleSubscriber<WeatherBean>() {
                @Override
                public void onNext(WeatherBean weatherBean) {
                    Glide.with(getContext()).load(weatherBean.getIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivIcon);
                }
            });
            minTemp.add(Integer.valueOf(dailyForecastList.get(i).getTmp().getMin()));
            maxTemp.add(Integer.valueOf(dailyForecastList.get(i).getTmp().getMax()));
            weatherStrView.addView(tvWeather, cellParams);
            dateTitleView.addView(tvDate, cellParams);
            iconView.addView(ivIcon);
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvDate.setVisibility(VISIBLE);
                    tvWeather.setVisibility(VISIBLE);
                    ivIcon.setVisibility(VISIBLE);
                }
            }, 200 * i);
        }
        addView(dateTitleView);
        addView(iconView);
        addView(weatherStrView);
        final ChartView chartView = new ChartView(getContext());
        chartView.setData(minTemp, maxTemp);
        chartView.setPadding(0, SizeUtils.dp2px(getContext(), 16), 0, SizeUtils.dp2px(getContext(), 16));
        addView(chartView, chartParams);
    }

    public void setWeather5(HeWeather5 weather5) {
        if (weather5 == null || !canRefresh) {
            return;
        }
        dailyForecastList.clear();
        dailyForecastList.addAll(weather5.getDaily_forecast());
        letItGo();
        canRefresh = false;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                canRefresh = true;
            }
        }, weather5.getDaily_forecast().size() * 200);
    }
}
