package com.liyu.suzhoubus.utils;

import com.google.gson.reflect.TypeToken;
import com.liyu.suzhoubus.App;
import com.liyu.suzhoubus.http.RetrofitManager;
import com.liyu.suzhoubus.model.HeWeather5;
import com.liyu.suzhoubus.model.WeatherBean;

import java.io.IOException;
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

    private static WeatherUtil getInstance() {
        if (instance == null) {
            synchronized (WeatherUtil.class) {
                instance = new WeatherUtil();
            }
        }
        return instance;
    }

    public static Observable<WeatherBean> getWeatherDict(final String code) {
        return Observable.create(new Observable.OnSubscribe<WeatherBean>() {
            @Override
            public void call(Subscriber<? super WeatherBean> subscriber) {
                subscriber.onNext(WeatherUtil.getInstance().weatherBeanMap.get(code));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    private String readFromAssets() {
        try {
            InputStream is = App.getContext().getAssets().open("weather.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");

        } catch (IOException e) {
            return null;
        }
    }

    public static void saveDailyHistory(HeWeather5 weather) {
        Observable.just(weather).filter(new Func1<HeWeather5, Boolean>() {
            @Override
            public Boolean call(HeWeather5 weather5) {
                return weather5 != null;
            }
        }).map(new Func1<HeWeather5, Boolean>() {
            @Override
            public Boolean call(HeWeather5 weather5) {
                ACache mCache = ACache.get(App.getContext());
                for (HeWeather5.DailyForecastBean bean : weather5.getDaily_forecast()) {
                    mCache.put(bean.getDate(), bean, 7 * 24 * 60 * 60);//每天的情况缓存7天，供后面查询
                }
                return true;
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static HeWeather5.DailyForecastBean getYesterday() {
        return (HeWeather5.DailyForecastBean) ACache.get(App.getContext())
                .getAsObject(TimeUtils.getPreviousDay(TimeUtils.getCurTimeString(TimeUtils.DATE_SDF), 1));
    }
}
