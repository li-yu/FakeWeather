package com.liyu.fakeweather.ui.weather;

import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.location.RxLocation;
import com.liyu.fakeweather.model.AqiDetailBean;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.ui.base.BaseContentFragment;
import com.liyu.fakeweather.ui.weather.adapter.AqiAdapter;
import com.liyu.fakeweather.ui.weather.adapter.HourlyAdapter;
import com.liyu.fakeweather.ui.weather.adapter.SuggestionAdapter;
import com.liyu.fakeweather.ui.weather.dynamic.DynamicWeatherView2;
import com.liyu.fakeweather.ui.weather.dynamic.ShortWeatherInfo;
import com.liyu.fakeweather.ui.weather.dynamic.SunnyType;
import com.liyu.fakeweather.utils.ACache;
import com.liyu.fakeweather.utils.ThemeUtil;
import com.liyu.fakeweather.utils.WeatherUtil;
import com.liyu.fakeweather.widgets.AqiView;
import com.liyu.fakeweather.widgets.WeatherChartView;

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

    private static final String CACHE_WEAHTHER_NAME = "weather_cache";

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

    private TextView tvCityName;

    private AqiView aqiView;

    Rect localRect = new Rect();

    private DynamicWeatherView2 dynamicWeatherView;

    private boolean weatherChartViewLastVisible = false;

    private boolean aqiViewLastVisible = false;

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
        tvCityName = findView(R.id.tv_city_name);

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
                IFakeWeather cacheWeather = (IFakeWeather) mCache.getAsObject(CACHE_WEAHTHER_NAME);
                if (cacheWeather == null) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(cacheWeather);
                }
            }
        });
    }

    private Observable<IFakeWeather> getFromNetwork() {
        return RxLocation.get().locate(getActivity())
                .flatMap(new Func1<BDLocation, Observable<BaseWeatherResponse<HeWeather5>>>() {
                    @Override
                    public Observable<BaseWeatherResponse<HeWeather5>> call(BDLocation bdLocation) {
                        final String city = TextUtils.isEmpty(bdLocation.getCity()) ? "苏州" : bdLocation.getCity().replace("市", "");
                        return WeatherUtil.getInstance().getWeatherKey().flatMap(new Func1<String, Observable<BaseWeatherResponse<HeWeather5>>>() {
                            @Override
                            public Observable<BaseWeatherResponse<HeWeather5>> call(String key) {
                                return ApiFactory
                                        .getWeatherController()
                                        .getWeather(key, city)
                                        .subscribeOn(Schedulers.io());
                            }
                        });
                    }
                })
                .map(new Func1<BaseWeatherResponse<HeWeather5>, IFakeWeather>() {
                    @Override
                    public IFakeWeather call(BaseWeatherResponse<HeWeather5> response) {
                        HeWeather5 heWeather5 = response.HeWeather5.get(0);
                        mCache.put(CACHE_WEAHTHER_NAME, heWeather5, 30 * 60);
                        WeatherUtil.getInstance().saveDailyHistory(heWeather5);
                        return heWeather5;
                    }
                });
    }

    @Override
    protected void lazyFetchData() {
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
                        Snackbar.make(getView(), "获取天气失败!", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
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
        ShortWeatherInfo info = new ShortWeatherInfo();
        info.setCode(weather.getFakeNow().getNowCode());
        info.setWindSpeed(weather.getFakeNow().getNowWindSpeed());
        info.setSunrise(weather.getFakeForecastDaily().get(0).getSunRise());
        info.setSunset(weather.getFakeForecastDaily().get(0).getSunSet());
        info.setMoonrise(weather.getFakeForecastDaily().get(0).getMoonRise());
        info.setMoonset(weather.getFakeForecastDaily().get(0).getMoonSet());
        dynamicWeatherView.setType(new SunnyType(getActivity(), info));
        parentToolbar.setTitle(weather.getFakeBasic().getCityName());
        parentToolbar.setTitleTextColor(0x00FFFFFF);
        tvCityName.setText(weather.getFakeBasic().getCityName());
        hourlyAdapter.setNewData(weather.getFakeForecastHourly());
        weatherChartView.setWeather5(weather);
        tvNowWeatherString.setText(weather.getFakeNow().getNowText());
        tvNowTemp.setText(String.format("%s°", weather.getFakeNow().getNowTemp()));
        tvTodayTempMax.setText(weather.getFakeForecastDaily().get(0).getMaxTemp() + "℃");
        tvTodayTempMin.setText(weather.getFakeForecastDaily().get(0).getMinTemp() + "℃");
        aqiView.setApi(weather);
        setAqiDetail(weather);
        setSuggesstion(weather);

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

        int newColor = ThemeUtil.changeAlpha(0xFF51C0F8, fraction);
        int titleColor = ThemeUtil.changeAlpha(0xFFFFFFFF, fraction);
        parentToolbar.setBackgroundColor(newColor);
        parentToolbar.setTitleTextColor(titleColor);

        ViewCompat.setElevation(parentToolbar, fraction);

        if (weatherChartView.getLocalVisibleRect(localRect)) {
            if (!weatherChartViewLastVisible) {
                weatherChartView.setWeather5(currentWeather);
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

}
