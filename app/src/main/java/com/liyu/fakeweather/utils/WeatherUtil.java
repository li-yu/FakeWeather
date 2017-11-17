package com.liyu.fakeweather.utils;


import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.liyu.fakeweather.App;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseAppResponse;
import com.liyu.fakeweather.http.RetrofitManager;
import com.liyu.fakeweather.model.FakeWeather;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.model.WeatherBean;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/11/11.
 */

public class WeatherUtil {

    private static WeatherUtil instance;

    private Map<String, WeatherBean> weatherBeanMap;

    private WeatherUtil() {
        weatherBeanMap = new HashMap<>();
        List<WeatherBean> list = RetrofitManager.gson().fromJson(readFromAssets(),
                new TypeToken<List<WeatherBean>>() {
                }.getType());
        for (WeatherBean bean : list) {
            weatherBeanMap.put(bean.getCode(), bean);
        }
    }

    public static WeatherUtil getInstance() {
        if (instance == null) {
            synchronized (WeatherUtil.class) {
                instance = new WeatherUtil();
            }
        }
        return instance;
    }

    public Observable<WeatherBean> getWeatherDict(final String code) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<WeatherBean>() {
            @Override
            public void call(Subscriber<? super WeatherBean> subscriber) {
                subscriber.onNext(WeatherUtil.getInstance().weatherBeanMap.get(code));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<String> getWeatherKey() {
        Observable<String> localKey = Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String key = SettingsUtil.getWeatherKey();
                if (TextUtils.isEmpty(key)) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(key);
                }
            }
        });

        Observable<String> netKey = ApiFactory.getAppController().getWeatherKey().flatMap(new Func1<BaseAppResponse<String>, Observable<String>>() {
            @Override
            public Observable<String> call(BaseAppResponse<String> response) {
                if (TextUtils.isEmpty(response.results)) {
                    SettingsUtil.setWeatherKey(response.results);
                }
                return Observable.just(response.results);
            }
        });

        return Observable
                .concat(localKey, netKey)
                .first()
                .subscribeOn(Schedulers.io());
    }

    private String readFromAssets() {
        try {
            InputStream is = App.getContext().getAssets().open("weather_map.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveDailyHistory(IFakeWeather weather) {
        Observable.just(weather).filter(new Func1<IFakeWeather, Boolean>() {
            @Override
            public Boolean call(IFakeWeather weather5) {
                return weather5 != null;
            }
        }).map(new Func1<IFakeWeather, Boolean>() {
            @Override
            public Boolean call(IFakeWeather weather) {
                ACache mCache = ACache.get(App.getContext());
                for (FakeWeather.FakeForecastDaily bean : weather.getFakeForecastDaily()) {
                    mCache.put(bean.getDate(), bean, 7 * 24 * 60 * 60);//每天的情况缓存7天，供后面查询
                }
                return true;
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public FakeWeather.FakeForecastDaily getYesterday() {
        return (FakeWeather.FakeForecastDaily) ACache.get(App.getContext())
                .getAsObject(TimeUtils.getPreviousDay(TimeUtils.getCurTimeString(TimeUtils.DATE_SDF), 1));
    }

    public String getShareMessage(IFakeWeather weather) {
        StringBuffer message = new StringBuffer();
        message.append(weather.getFakeBasic().getCityName());
        message.append("天气：");
        message.append("\r\n");
        message.append(weather.getFakeNow().getUpdateTime());
        message.append(" 发布：");
        message.append("\r\n");
        message.append(weather.getFakeNow().getNowText());
        message.append("，");
        message.append(weather.getFakeNow().getNowTemp()).append("℃");
        message.append("。");
        message.append("\r\n");
        message.append("PM2.5：").append(weather.getFakeAqi().getPm25());
        message.append("，");
        message.append(weather.getFakeAqi().getQlty());
        message.append("。");
        message.append("\r\n");
        message.append("今天：");
        message.append(weather.getFakeForecastDaily().get(0).getMinTemp()).append("℃-");
        message.append(weather.getFakeForecastDaily().get(0).getMaxTemp()).append("℃");
        message.append("，");
        message.append(weather.getFakeForecastDaily().get(0).getTxt());
        message.append("\r\n");
        message.append("明天：");
        message.append(weather.getFakeForecastDaily().get(1).getMinTemp()).append("℃-");
        message.append(weather.getFakeForecastDaily().get(1).getMaxTemp()).append("℃");
        message.append("，");
        message.append(weather.getFakeForecastDaily().get(1).getTxt());

        return message.toString();
    }
}
