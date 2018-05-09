package com.liyu.fakeweather.http.api;

import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.model.HeWeather;
import com.liyu.fakeweather.model.HeWeatherAir;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 天气查询接口
 * Created by liyu on 2016/10/31.
 */

public interface WeatherController {

    @GET("https://free-api.heweather.com/s6/weather")
    Observable<BaseWeatherResponse<HeWeather>> getWeather(@Query("key") String key, @Query("location") String city);

    @GET("https://free-api.heweather.com/s6/air/now")
    Observable<BaseWeatherResponse<HeWeatherAir>> getAir(@Query("key") String key, @Query("location") String city);

    @GET("https://free-api.heweather.com/s6/air/now")
    Call<BaseWeatherResponse<HeWeatherAir>> getAirSync(@Query("key") String key, @Query("location") String city);

}
