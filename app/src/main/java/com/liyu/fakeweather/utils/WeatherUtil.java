package com.liyu.fakeweather.utils;


import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.liyu.fakeweather.App;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseAppResponse;
import com.liyu.fakeweather.http.RetrofitManager;
import com.liyu.fakeweather.model.HeWeather5;
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
            InputStream is = App.getContext().getAssets().open("weather1.json");
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

    public void saveDailyHistory(HeWeather5 weather) {
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

    public HeWeather5.DailyForecastBean getYesterday() {
        return (HeWeather5.DailyForecastBean) ACache.get(App.getContext())
                .getAsObject(TimeUtils.getPreviousDay(TimeUtils.getCurTimeString(TimeUtils.DATE_SDF), 1));
    }

    public String getShareMessage(HeWeather5 weather) {
        StringBuffer message = new StringBuffer();
        message.append(weather.getBasic().getCity());
        message.append("天气：");
        message.append("\r\n");
        message.append(weather.getBasic().getUpdate().getLoc());
        message.append(" 发布：");
        message.append("\r\n");
        message.append(weather.getNow().getCond().getTxt());
        message.append("，");
        message.append(weather.getNow().getTmp()).append("℃");
        message.append("。");
        message.append("\r\n");
        message.append("PM2.5：").append(weather.getAqi().getCity().getPm25());
        message.append("，");
        message.append(weather.getAqi().getCity().getQlty());
        message.append("。");
        message.append("\r\n");
        message.append("今天：");
        message.append(weather.getDaily_forecast().get(0).getTmp().getMin()).append("℃-");
        message.append(weather.getDaily_forecast().get(0).getTmp().getMax()).append("℃");
        message.append("，");
        message.append(weather.getDaily_forecast().get(0).getCond().getTxt_d());
        message.append("，");
        message.append(weather.getDaily_forecast().get(0).getWind().getDir());
        message.append(weather.getDaily_forecast().get(0).getWind().getSc());
        message.append("级。");
        message.append("\r\n");
        message.append("明天：");
        message.append(weather.getDaily_forecast().get(1).getTmp().getMin()).append("℃-");
        message.append(weather.getDaily_forecast().get(1).getTmp().getMax()).append("℃");
        message.append("，");
        message.append(weather.getDaily_forecast().get(1).getCond().getTxt_d());
        message.append("，");
        message.append(weather.getDaily_forecast().get(1).getWind().getDir());
        message.append(weather.getDaily_forecast().get(1).getWind().getSc());
        message.append("级。");

        return message.toString();
    }
}
