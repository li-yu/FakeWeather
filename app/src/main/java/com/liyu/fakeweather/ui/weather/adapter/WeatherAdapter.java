package com.liyu.fakeweather.ui.weather.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.utils.SizeUtils;
import com.liyu.fakeweather.utils.TimeUtils;
import com.liyu.fakeweather.widgets.WeatherChartView;
import com.liyu.fakeweather.widgets.WeatherDetailsView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by liyu on 2016/11/19.
 */

public class WeatherAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private boolean showWeatherChart = true;

    public WeatherAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(HeWeather5.TYPE_NOW, R.layout.item_weather_container);
        addItemType(HeWeather5.TYPE_SUGGESTION, R.layout.item_suggestion_weather);
        addItemType(HeWeather5.TYPE_DAILYFORECAST, R.layout.item_weather_container);

    }

    @Override
    protected void convert(final BaseViewHolder holder, MultiItemEntity multiItemEntity) {
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
                    tvWind.setText(String.format("%s级", now.getHourly_forecast().get(i).getWind().getSc()));
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
                HeWeather5 weather5 = (HeWeather5) ((HeWeather5) multiItemEntity).clone();
                LinearLayout container = holder.getView(R.id.contentLayout);
                if (showWeatherChart) {
                    container.setPadding(0, SizeUtils.dp2px(mContext, 16), 0, SizeUtils.dp2px(mContext, 16));
                    container.removeAllViews();
                    container.addView(getChartView(weather5));
                } else {
                    container.removeAllViews();
                    container.addView(getDetailsView(weather5));
                }
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showWeatherChart = !showWeatherChart;
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                });
                break;
        }
    }

    private WeatherChartView getChartView(HeWeather5 weather5) {
        WeatherChartView chartView = new WeatherChartView(mContext);
        chartView.setWeather5(weather5);
        return chartView;
    }

    private WeatherDetailsView getDetailsView(HeWeather5 weather5) {
        WeatherDetailsView detailsView = new WeatherDetailsView(mContext);
        detailsView.setWeather5(weather5);
        return detailsView;
    }

}
