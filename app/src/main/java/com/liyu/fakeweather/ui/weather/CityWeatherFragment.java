package com.liyu.fakeweather.ui.weather;

import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.model.AqiDetailBean;
import com.liyu.fakeweather.model.FakeWeather;
import com.liyu.fakeweather.model.HeWeather;
import com.liyu.fakeweather.model.HeWeatherAir;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.model.WeatherCity;
import com.liyu.fakeweather.ui.base.BaseContentFragment;
import com.liyu.fakeweather.ui.weather.adapter.AqiAdapter;
import com.liyu.fakeweather.ui.weather.adapter.HourlyAdapter;
import com.liyu.fakeweather.ui.weather.adapter.SuggestionAdapter;
import com.liyu.fakeweather.ui.weather.dynamic.BaseWeatherType;
import com.liyu.fakeweather.ui.weather.dynamic.DynamicWeatherView;
import com.liyu.fakeweather.ui.weather.dynamic.ShortWeatherInfo;
import com.liyu.fakeweather.ui.weather.dynamic.TypeUtil;
import com.liyu.fakeweather.utils.ACache;
import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.ThemeUtil;
import com.liyu.fakeweather.utils.WeatherUtil;
import com.liyu.fakeweather.widgets.AqiView;
import com.liyu.fakeweather.widgets.WeatherChartView;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;
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

    private HourlyAdapter hourlyAdapter;

    private AqiAdapter aqiAdapter;

    private SuggestionAdapter suggestionAdapter;

    private ACache mCache;

    private IFakeWeather currentWeather;

    private Subscription subscription;

    private WeatherChartView weatherChartView;

    private Toolbar parentToolbar;

    private View fakeStatusBar;

    private LinearLayout layoutNow;
    private RelativeLayout layoutDetails;

    private TextView tvNowWeatherString;
    private TextView tvNowTemp;

    private TextView tvNowHum;
    private TextView tvNowPres;
    private TextView tvNowWindSc;
    private TextView tvNowWindDir;

    private TextView tvTodayTempMax;
    private TextView tvTodayTempMin;

    private AqiView aqiView;

    private Rect localRect = new Rect();

    private DynamicWeatherView dynamicWeatherView;

    private boolean weatherChartViewLastVisible = false;

    private boolean aqiViewLastVisible = false;

    private String cityId = "苏州";

    private String cityName = "苏州";

    private BaseWeatherType type;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city_weather;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mCache = ACache.get(getActivity());
        parentToolbar = ((WeatherFragment) getParentFragment()).getmToolbar();
        fakeStatusBar = ((WeatherFragment) getParentFragment()).getfakeStatusBar();
        dynamicWeatherView = ((WeatherFragment) getParentFragment()).getDynamicWeatherView();

        layoutNow = findView(R.id.layout_now);
        layoutDetails = findView(R.id.layout_details);
        tvNowWeatherString = findView(R.id.tv_weather_string);
        tvNowTemp = findView(R.id.tv_temp);
        aqiView = findView(R.id.aqiview);
        tvTodayTempMax = findView(R.id.tv_temp_max);
        tvTodayTempMin = findView(R.id.tv_temp_min);

        tvNowHum = findView(R.id.tv_now_hum);
        tvNowPres = findView(R.id.tv_now_pres);
        tvNowWindSc = findView(R.id.tv_now_wind_sc);
        tvNowWindDir = findView(R.id.tv_now_wind_dir);

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
                String cacheKey = cityId;
                if (cityId.contains(",")) {
                    cacheKey = cityName; //当 key 是经纬度坐标时，按城市名取缓存，否则按城市 ID 取
                }
                IFakeWeather cacheWeather = (IFakeWeather) mCache.getAsObject(cacheKey);
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
            return WeatherUtil.getInstance().getWeatherKey().flatMap(new Func1<String, Observable<BaseWeatherResponse<HeWeather>>>() {
                @Override
                public Observable<BaseWeatherResponse<HeWeather>> call(String key) {
                    return ApiFactory
                            .getWeatherController()
                            .getWeather(key, cityId)
                            .subscribeOn(Schedulers.io());
                }
            }).map(new Func1<BaseWeatherResponse<HeWeather>, HeWeather>() {
                @Override
                public HeWeather call(BaseWeatherResponse<HeWeather> heWeatherBaseWeatherResponse) {
                    HeWeather heWeather = heWeatherBaseWeatherResponse.HeWeather6.get(0);
                    try {
                        Response<BaseWeatherResponse<HeWeatherAir>> response = ApiFactory.getWeatherController().getAirSync(SettingsUtil.getWeatherKey(), heWeather.getBasic().getParent_city()).execute();
                        if (response.body() != null && response.body().HeWeather6.size() > 0) {
                            HeWeatherAir air = response.body().HeWeather6.get(0);
                            FakeWeather.FakeAqi fakeAqi = new FakeWeather.FakeAqi();
                            fakeAqi.setApi(air.getAir_now_city().getAqi());
                            fakeAqi.setCo(air.getAir_now_city().getCo());
                            fakeAqi.setNo2(air.getAir_now_city().getNo2());
                            fakeAqi.setO3(air.getAir_now_city().getO3());
                            fakeAqi.setPm10(air.getAir_now_city().getPm10());
                            fakeAqi.setPm25(air.getAir_now_city().getPm25());
                            fakeAqi.setQlty(air.getAir_now_city().getQlty());
                            fakeAqi.setSo2(air.getAir_now_city().getSo2());
                            heWeather.setFakeAqi(fakeAqi);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("获取空气质量失败！");
                    }
                    return heWeather;
                }
            }).map(new Func1<HeWeather, IFakeWeather>() {
                @Override
                public IFakeWeather call(HeWeather response) {
                    HeWeather heWeather = response;
                    if (cityId.contains(",")) {
                        mCache.put(cityName, heWeather, 30 * 60);
                    } else {
                        mCache.put(cityId, heWeather, 30 * 60);
                    }
                    ContentValues values = new ContentValues();
                    values.put("weatherCode", heWeather.getFakeNow().getNowCode());
                    values.put("weatherText", heWeather.getFakeNow().getNowText());
                    values.put("weatherTemp", heWeather.getFakeNow().getNowTemp());
                    DataSupport.updateAll(WeatherCity.class, values, "cityName = ?", cityName);
                    return heWeather;
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
        cityId = getArguments().getString("cityId");
        cityName = getArguments().getString("cityName");
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
        layoutNow.setVisibility(View.VISIBLE);
        layoutDetails.setVisibility(View.VISIBLE);
        tvNowHum.setText(weather.getFakeNow().getNowHum() + "%");
        tvNowPres.setText(weather.getFakeNow().getNowPres());
        tvNowWindSc.setText(hasDigit(weather.getFakeNow().getNowWindSc()) ? weather.getFakeNow().getNowWindSc() + "级"
                : weather.getFakeNow().getNowWindSc());
        tvNowWindDir.setText(weather.getFakeNow().getNowWindDir());
        layoutNow.setAlpha(0);
        layoutDetails.setAlpha(0);
        layoutNow.animate().alpha(1).setDuration(1000);
        layoutDetails.setTranslationY(-100.0f);
        layoutDetails.animate().translationY(0).setDuration(1000);
        layoutDetails.animate().alpha(1).setDuration(1000);
        hourlyAdapter.setNewData(weather.getFakeForecastHourly());
        weatherChartView.setWeather(weather);
        tvNowWeatherString.setText(weather.getFakeNow().getNowText());
        tvNowTemp.setText(String.format("%s°", weather.getFakeNow().getNowTemp()));
        tvTodayTempMax.setText(weather.getFakeForecastDaily().get(0).getMaxTemp() + "℃");
        tvTodayTempMin.setText(weather.getFakeForecastDaily().get(0).getMinTemp() + "℃");
        parentToolbar.setTitle(cityName);
        parentToolbar.setTitleTextColor(Color.TRANSPARENT);
        aqiView.setApi(weather);
        setAqiDetail(weather);
        setSuggesstion(weather);

    }

    // 判断一个字符串是否含有数字
    private boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
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
        if (type == null || System.currentTimeMillis() - type.getLastUpdatedTime() > 30 * 60 * 1000) {
            type = TypeUtil.getType(getActivity(), info);
            type.setLastUpdatedTime(System.currentTimeMillis());
        }
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

        float fraction = (float) (scrollY) / (float) (parentToolbar.getHeight());

        if (fraction >= 1) {
            fraction = 1;
        }

        int newColor = ThemeUtil.changeAlpha(dynamicWeatherView.getColor(), fraction);
        int titleColor = ThemeUtil.changeAlpha(0xFFFFFFFF, fraction);
        parentToolbar.setBackgroundColor(newColor);
        parentToolbar.setTitleTextColor(titleColor);
        fakeStatusBar.setBackgroundColor(newColor);

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
            parentToolbar.setTitle(cityName);
        }
        if (!isVisibleToUser && weatherNestedScrollView != null) {
            weatherNestedScrollView.scrollTo(0, 0);
        }
    }
}
