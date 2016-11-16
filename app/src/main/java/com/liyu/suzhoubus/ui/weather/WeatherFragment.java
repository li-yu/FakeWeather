package com.liyu.suzhoubus.ui.weather;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.http.ApiFactory;
import com.liyu.suzhoubus.http.BaseWeatherResponse;
import com.liyu.suzhoubus.model.HeWeather5;
import com.liyu.suzhoubus.ui.MainActivity;
import com.liyu.suzhoubus.ui.base.BaseFragment;
import com.liyu.suzhoubus.ui.weather.adapter.DailyAdapter;
import com.liyu.suzhoubus.ui.weather.adapter.HourlyAdapter;
import com.liyu.suzhoubus.utils.ACache;
import com.liyu.suzhoubus.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/10/31.
 */

public class WeatherFragment extends BaseFragment {

    private static final String CACHE_WEAHTHER_NAME = "weather_cache";

    private Toolbar mToolbar;
    private TextView tvCityName;
    private TextView tvNowWeatherString;
    private TextView tvNowTemp;
    private RecyclerView hourlyRecyclerView;
    private HourlyAdapter hourlyAdapter;
    private TextView tvUpdateTime;
    private TextView tvAqi;

    private RecyclerView dailyRecyclerView;
    private DailyAdapter dailyAdapter;

    private ACache mCache;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initViews() {
        mCache = ACache.get(getActivity());
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("苏州天气");
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_bus);
        tvCityName = findView(R.id.tv_city_name);
        tvNowWeatherString = findView(R.id.tv_weather_string);
        tvNowTemp = findView(R.id.tv_temp);
        tvCityName.setText("苏州");
        tvUpdateTime = findView(R.id.tv_update_time);
        tvAqi = findView(R.id.tv_weather_aqi);

        LinearLayoutManager hourlyLayoutManager = new LinearLayoutManager(getActivity());
        hourlyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hourlyRecyclerView = findView(R.id.rv_hourly_weather);
        hourlyRecyclerView.setLayoutManager(hourlyLayoutManager);
        hourlyAdapter = new HourlyAdapter(R.layout.item_hourly_weather, null);
        hourlyRecyclerView.setAdapter(hourlyAdapter);

        LinearLayoutManager dailyLayoutManager = new LinearLayoutManager(getActivity());
        dailyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dailyRecyclerView = findView(R.id.rv_daily_weather);
        dailyRecyclerView.setLayoutManager(dailyLayoutManager);
        dailyAdapter = new DailyAdapter(R.layout.item_day_weather, null);
        dailyRecyclerView.setAdapter(dailyAdapter);
    }

    @Override
    protected void lazyFetchData() {

        HeWeather5 cacheWeather = (HeWeather5) mCache.getAsObject(CACHE_WEAHTHER_NAME);
        if (cacheWeather != null) {
            showWeather(cacheWeather);
            return;
        }

        ApiFactory
                .getWeatherController()
                .getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseWeatherResponse<HeWeather5>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseWeatherResponse<HeWeather5> listBaseWeatherResponse) {
                        if (listBaseWeatherResponse == null || listBaseWeatherResponse.HeWeather5.size() == 0) {
                            return;
                        }
                        showWeather(listBaseWeatherResponse.HeWeather5.get(0));
                        mCache.put(CACHE_WEAHTHER_NAME, listBaseWeatherResponse.HeWeather5.get(0), 10 * 60);
                    }
                });
    }

    private void showWeather(HeWeather5 weather) {
        if (weather == null || !weather.getStatus().equals("ok")) {
            return;
        }
        tvNowWeatherString.setText(weather.getNow().getCond().getTxt());
        tvAqi.setText(weather.getAqi().getCity().getQlty());
        tvNowTemp.setText(String.format("%s℃", weather.getNow().getTmp()));
        String updateTime = TimeUtils.string2String(weather.getBasic().getUpdate().getLoc(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()), new SimpleDateFormat("HH:mm", Locale.getDefault()));
        tvUpdateTime.setText(String.format("%s 更新", updateTime));
        hourlyAdapter.setNewData(weather.getHourly_forecast());
        dailyAdapter.setNewData(weather.getDaily_forecast());
    }

}
