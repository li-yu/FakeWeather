package com.liyu.suzhoubus.ui.weather.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.HeWeather5;
import com.liyu.suzhoubus.model.WeatherBean;
import com.liyu.suzhoubus.utils.SimpleSubscriber;
import com.liyu.suzhoubus.utils.TimeUtils;
import com.liyu.suzhoubus.utils.WeatherUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2016/11/19.
 */

public class WeatherAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public WeatherAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(HeWeather5.TYPE_NOW, R.layout.item_weather_container);
        addItemType(HeWeather5.TYPE_SUGGESTION, R.layout.item_suggestion_weather);
        addItemType(HeWeather5.TYPE_DAILYFORECAST, R.layout.item_weather_container);

    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity multiItemEntity) {
        switch (holder.getItemViewType()) {
            case HeWeather5.TYPE_NOW:
                HeWeather5 now = (HeWeather5) multiItemEntity;
                LinearLayout nowContainer = holder.getView(R.id.contentLayout);
                nowContainer.removeAllViews();
                for (int i = 0; i < now.getHourly_forecast().size(); i++) {
                    View view = View.inflate(mContext, R.layout.item_now_weather, null);
                    TextView tvTime = (TextView) view.findViewById(R.id.tv_now_time);
                    TextView tvTemp = (TextView) view.findViewById(R.id.tv_now_temp);
                    TextView tvPop = (TextView) view.findViewById(R.id.tv_now_pop);
                    TextView tvWind = (TextView) view.findViewById(R.id.tv_now_wind);
                    tvTime.setText(TimeUtils.date2String(TimeUtils.string2Date(now.getHourly_forecast().get(i).getDate(), sdf1), sdf2));
                    tvTemp.setText(now.getHourly_forecast().get(i).getTmp() + "℃");
                    tvPop.setText(now.getHourly_forecast().get(i).getPop() + "%");
                    tvWind.setText(String.format("%s，%s", now.getHourly_forecast().get(i).getWind().getDir(), now.getHourly_forecast().get(i).getWind().getSc()));
                    nowContainer.addView(view);
                }
                break;
            case HeWeather5.TYPE_SUGGESTION:
                HeWeather5.SuggestionBean suggestion = (HeWeather5.SuggestionBean) multiItemEntity;
                holder.setText(R.id.tv_suggestion_air, String.format("舒适指数 -- %s", suggestion.getComf().getBrf()));
                holder.setText(R.id.tv_suggestion_air_info, suggestion.getComf().getTxt());
                holder.setText(R.id.tv_suggestion_out, String.format("运动指数 -- %s", suggestion.getSport().getBrf()));
                holder.setText(R.id.tv_suggestion_out_info, suggestion.getSport().getTxt());
                holder.setText(R.id.tv_suggestion_car, String.format("洗车指数 -- %s", suggestion.getCw().getBrf()));
                holder.setText(R.id.tv_suggestion_car_info, suggestion.getCw().getTxt());
                break;
            case HeWeather5.TYPE_DAILYFORECAST:
                HeWeather5 weather5 = (HeWeather5) multiItemEntity;
                LinearLayout container = holder.getView(R.id.contentLayout);
                container.removeAllViews();
                HeWeather5.DailyForecastBean yesterday = WeatherUtil.getYesterday();
                if (yesterday != null) {
                    weather5.getDaily_forecast().add(0, yesterday);
                }
                for (int i = 0; i < weather5.getDaily_forecast().size(); i++) {
                    View view = View.inflate(mContext, R.layout.item_daily_weather, null);
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
                            tvWeek.setText(TimeUtils.getWeek(weather5.getDaily_forecast().get(i).getDate(), TimeUtils.DATE_SDF));
                        }
                    } else {
                        if (i == 0) {
                            tvWeek.setText("今天");
                        } else if (i == 1) {
                            tvWeek.setText("明天");
                        } else {
                            tvWeek.setText(TimeUtils.getWeek(weather5.getDaily_forecast().get(i).getDate(), TimeUtils.DATE_SDF));
                        }
                    }

                    tvTemp.setText(String.format(("%s℃ - %s℃"), weather5.getDaily_forecast().get(i).getTmp().getMin(), weather5.getDaily_forecast().get(i).getTmp().getMax()));
                    tvInfo.setText(String.format("%s，降雨几率：%s%%，%s %s 级", weather5.getDaily_forecast().get(i).getCond().getTxt_d(), weather5.getDaily_forecast().get(i).getPop(), weather5.getDaily_forecast().get(i).getWind().getDir(), weather5.getDaily_forecast().get(i).getWind().getSc()));

                    WeatherUtil.getWeatherDict(weather5.getDaily_forecast().get(i).getCond().getCode_d()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleSubscriber<WeatherBean>() {
                        @Override
                        public void onNext(WeatherBean weatherBean) {
                            Glide.with(mContext).load(weatherBean.getIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivWeek);
                        }
                    });
                    container.addView(view);
                }

                break;
        }
    }

}
