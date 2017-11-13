package com.liyu.fakeweather.ui.weather;

import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.model.AqiDetailBean;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.model.WeatherCity;
import com.liyu.fakeweather.ui.base.BaseContentFragment;
import com.liyu.fakeweather.ui.weather.adapter.AqiAdapter;
import com.liyu.fakeweather.ui.weather.adapter.HourlyAdapter;
import com.liyu.fakeweather.ui.weather.adapter.SuggestionAdapter;
import com.liyu.fakeweather.ui.weather.dynamic.BaseWeatherType;
import com.liyu.fakeweather.ui.weather.dynamic.DynamicWeatherView2;
import com.liyu.fakeweather.ui.weather.dynamic.ShortWeatherInfo;
import com.liyu.fakeweather.ui.weather.dynamic.TypeUtil;
import com.liyu.fakeweather.utils.ACache;
import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.ThemeUtil;
import com.liyu.fakeweather.utils.WeatherUtil;
import com.liyu.fakeweather.widgets.AqiView;
import com.liyu.fakeweather.widgets.WeatherChartView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2017/8/24.
 */

public class CityWeatherFragment extends BaseContentFragment implements NestedScrollView.OnScrollChangeListener {

    private NestedScrollView weatherNestedScrollView;

    private RecyclerView hourlyRecyclerView;

    private RecyclerView aqiRecyclerView;

    private RecyclerView suggesstionRecyclerView;

    HourlyAdapter hourlyAdapter;

    AqiAdapter aqiAdapter;

    SuggestionAdapter suggestionAdapter;

    private ACache mCache;

    private IFakeWeather currentWeather;

    private Subscription subscription;

    private WeatherChartView weatherChartView;

    private Toolbar parentToolbar;

    private TextView tvNowWeatherString;
    private TextView tvNowTemp;

    private TextView tvTodayTempMax;
    private TextView tvTodayTempMin;

    private AqiView aqiView;

    Rect localRect = new Rect();

    private DynamicWeatherView2 dynamicWeatherView;

    private boolean weatherChartViewLastVisible = false;

    private boolean aqiViewLastVisible = false;

    private String city = "苏州";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city_weather;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mCache = ACache.get(getActivity());
        parentToolbar = ((WeatherFragment) getParentFragment()).getmToolbar();
        dynamicWeatherView = ((WeatherFragment) getParentFragment()).getDynamicWeatherView();

        tvNowWeatherString = findView(R.id.tv_weather_string);
        tvNowTemp = findView(R.id.tv_temp);
        aqiView = findView(R.id.aqiview);
        tvTodayTempMax = findView(R.id.tv_temp_max);
        tvTodayTempMin = findView(R.id.tv_temp_min);

        weatherNestedScrollView = findView(R.id.weatherNestedScrollView);
        weatherNestedScrollView.setOnScrollChangeListener(this);
        hourlyRecyclerView = findView(R.id.recyclerView_hourly);
        hourlyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        hourlyAdapter = new HourlyAdapter(R.layout.item_hourly_weather, null);
        hourlyAdapter.setDuration(1000);
        hourlyAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        hourlyRecyclerView.setAdapter(hourlyAdapter);
        weatherChartView = findView(R.id.weatherChartView);

        aqiRecyclerView = findView(R.id.recyclerViewAqi);
        aqiRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        aqiAdapter = new AqiAdapter(R.layout.item_weather_aqi, null);
        aqiAdapter.setDuration(1000);
        aqiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        aqiRecyclerView.setAdapter(aqiAdapter);

        suggesstionRecyclerView = findView(R.id.recyclerViewSuggestion);
        suggesstionRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        suggestionAdapter = new SuggestionAdapter(R.layout.item_suggestion, null);
        suggestionAdapter.setDuration(1000);
        suggestionAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        suggesstionRecyclerView.setAdapter(suggestionAdapter);

    }

    private Observable<IFakeWeather> getLocalCache() {
        return Observable.unsafeCreate(new Observable.OnSubscribe<IFakeWeather>() {

            @Override
            public void call(Subscriber<? super IFakeWeather> subscriber) {
                IFakeWeather cacheWeather = (IFakeWeather) mCache.getAsObject(city);
                if (cacheWeather == null) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(cacheWeather);
                }
            }
        });
    }

    private Observable<IFakeWeather> getFromNetwork() {
        int weatherSrc = SettingsUtil.getWeatherSrc();
        if (weatherSrc == SettingsUtil.WEATHER_SRC_HEFENG) {
            return WeatherUtil.getInstance().getWeatherKey().flatMap(new Func1<String, Observable<BaseWeatherResponse<HeWeather5>>>() {
                @Override
                public Observable<BaseWeatherResponse<HeWeather5>> call(String key) {
                    return ApiFactory
                            .getWeatherController()
                            .getWeather(key, city)
                            .subscribeOn(Schedulers.io());
                }
            }).map(new Func1<BaseWeatherResponse<HeWeather5>, IFakeWeather>() {
                @Override
                public IFakeWeather call(BaseWeatherResponse<HeWeather5> response) {
                    HeWeather5 heWeather5 = response.HeWeather5.get(0);
                    mCache.put(city, heWeather5, 30 * 60);
                    WeatherUtil.getInstance().saveDailyHistory(heWeather5);
                    ContentValues values = new ContentValues();
                    values.put("weatherCode", heWeather5.getFakeNow().getNowCode());
                    values.put("weatherText", heWeather5.getFakeNow().getNowText());
                    values.put("weatherTemp", heWeather5.getFakeNow().getNowTemp());
                    DataSupport.updateAll(WeatherCity.class, values, "cityName = ?", heWeather5.getFakeBasic().getCityName());
                    return heWeather5;
                }
            });
        } else if (weatherSrc == SettingsUtil.WEATHER_SRC_XIAOMI) {

            // TODO: 2017/10/12 完成小米天气源的适配
            return null;
        } else
            return null;

    }

    @Override
    protected void lazyFetchData() {
        city = getArguments().getString("city");
        showRefreshing(true);
        subscription = Observable
                .concat(getLocalCache(), getFromNetwork())
                .first()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IFakeWeather>() {
                    @Override
                    public void onCompleted() {
                        showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showRefreshing(false);
                        Snackbar.make(getView(), "获取天气失败!", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lazyFetchData();
                            }
                        }).setActionTextColor(getActivity().getResources().getColor(R.color.actionColor)).show();
                    }

                    @Override
                    public void onNext(IFakeWeather weather) {
                        showWeather(weather);
                    }
                });

    }

    private void showWeather(IFakeWeather weather) {
        currentWeather = weather;
        setDynamicWeatherView(weather);
        hourlyAdapter.setNewData(weather.getFakeForecastHourly());
        weatherChartView.setWeather(weather);
        tvNowWeatherString.setText(weather.getFakeNow().getNowText());
        tvNowTemp.setText(String.format("%s°", weather.getFakeNow().getNowTemp()));
        tvTodayTempMax.setText(weather.getFakeForecastDaily().get(0).getMaxTemp() + "℃");
        tvTodayTempMin.setText(weather.getFakeForecastDaily().get(0).getMinTemp() + "℃");
        aqiView.setApi(weather);
        setAqiDetail(weather);
        setSuggesstion(weather);

    }

    private void setDynamicWeatherView(IFakeWeather weather) {
        ((WeatherFragment) getParentFragment()).getDynamicWeatherView().setOriginWeather(weather);
        ShortWeatherInfo info = new ShortWeatherInfo();
        info.setCode(weather.getFakeNow().getNowCode());
        info.setWindSpeed(weather.getFakeNow().getNowWindSpeed());
        info.setSunrise(weather.getFakeForecastDaily().get(0).getSunRise());
        info.setSunset(weather.getFakeForecastDaily().get(0).getSunSet());
        info.setMoonrise(weather.getFakeForecastDaily().get(0).getMoonRise());
        info.setMoonset(weather.getFakeForecastDaily().get(0).getMoonSet());
        BaseWeatherType type = TypeUtil.getType(getActivity(), info);
        parentToolbar.setTitle(weather.getFakeBasic().getCityName());
        parentToolbar.setTitleTextColor(Color.TRANSPARENT);
        dynamicWeatherView.setType(type);

    }

    private void setSuggesstion(IFakeWeather weather) {
        suggestionAdapter.setNewData(weather.getFakeSuggestion());
    }

    private void setAqiDetail(IFakeWeather weather) {
        List<AqiDetailBean> list = new ArrayList<>();
        AqiDetailBean pm25 = new AqiDetailBean("PM2.5", "细颗粒物", weather.getFakeAqi().getPm25());
        list.add(pm25);
        AqiDetailBean pm10 = new AqiDetailBean("PM10", "可吸入颗粒物", weather.getFakeAqi().getPm10());
        list.add(pm10);
        AqiDetailBean so2 = new AqiDetailBean("SO2", "二氧化硫", weather.getFakeAqi().getSo2());
        list.add(so2);
        AqiDetailBean no2 = new AqiDetailBean("NO2", "二氧化氮", weather.getFakeAqi().getNo2());
        list.add(no2);
        AqiDetailBean co = new AqiDetailBean("CO", "一氧化碳", weather.getFakeAqi().getCo());
        list.add(co);
        AqiDetailBean o3 = new AqiDetailBean("O3", "臭氧", weather.getFakeAqi().getO3());
        list.add(o3);
        aqiAdapter.setNewData(list);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        float heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;
        float fraction = (scrollY - heightPixels * 0.8f + parentToolbar.getHeight()) / parentToolbar.getHeight();

        if (fraction <= 0) {
            fraction = 0;
        }

        if (fraction >= 1) {
            fraction = 1;
        }

        int newColor = ThemeUtil.changeAlpha(dynamicWeatherView.getColor(), fraction);
        int titleColor = ThemeUtil.changeAlpha(0xFFFFFFFF, fraction);
        parentToolbar.setBackgroundColor(newColor);
        parentToolbar.setTitleTextColor(titleColor);

        ViewCompat.setElevation(parentToolbar, fraction);

        if (weatherChartView.getLocalVisibleRect(localRect)) {
            if (!weatherChartViewLastVisible) {
                weatherChartView.setWeather(currentWeather);
            }
            weatherChartViewLastVisible = true;
        } else {
            weatherChartViewLastVisible = false;
        }

        if (aqiView.getLocalVisibleRect(localRect)) {
            if (!aqiViewLastVisible) {
                aqiView.setApi(currentWeather);
            }
            aqiViewLastVisible = true;
        } else {
            aqiViewLastVisible = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && currentWeather != null) {
            setDynamicWeatherView(currentWeather);
        }
        if (!isVisibleToUser && weatherNestedScrollView != null) {
            weatherNestedScrollView.scrollTo(0, 0);
        }
    }
}
