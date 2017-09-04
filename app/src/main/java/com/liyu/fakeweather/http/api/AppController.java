package com.liyu.fakeweather.http.api;

import com.liyu.fakeweather.http.BaseAppResponse;
import com.liyu.fakeweather.model.UpdateInfo;
import com.liyu.fakeweather.model.WeatherCityBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liyu on 2016/12/1.
 */

public interface AppController {

    @GET("http://api.caoliyu.cn/appupdate.json")
    Observable<BaseAppResponse<UpdateInfo>> checkUpdate();

    @GET("http://api.caoliyu.cn/weatherkey.json")
    Observable<BaseAppResponse<String>> getWeatherKey();

    @GET("http://api.caoliyu.cn/centralWeatherCity.json")
    Observable<List<WeatherCityBean>> getWeatherCityList();
}
