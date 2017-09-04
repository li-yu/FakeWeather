package com.liyu.fakeweather.http.api;

import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.model.HeWeather5;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 天气查询接口
 * Created by liyu on 2016/10/31.
 */

public interface WeatherController {

    @GET("https://free-api.heweather.com/v5/weather")
    Observable<BaseWeatherResponse<HeWeather5>> getWeather(@Query("key") String key, @Query("city") String city);

}
