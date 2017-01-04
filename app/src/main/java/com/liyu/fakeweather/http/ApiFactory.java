package com.liyu.fakeweather.http;

import com.liyu.fakeweather.http.api.AppController;
import com.liyu.fakeweather.http.api.BusController;
import com.liyu.fakeweather.http.api.GirlsController;
import com.liyu.fakeweather.http.api.WeatherController;

/**
 * Created by liyu on 2016/8/25.
 */
public class ApiFactory {
    protected static final Object monitor = new Object();

    private static BusController busController;
    private static GirlsController girlsController;
    private static WeatherController weatherController;
    private static AppController appController;

    public static BusController getBusController() {
        if (busController == null) {
            synchronized (monitor) {
                busController = RetrofitManager.getInstance().create(BusController.class);
            }
        }
        return busController;
    }

    public static GirlsController getGirlsController() {
        if (girlsController == null) {
            synchronized (monitor) {
                girlsController = RetrofitManager.getInstance().create(GirlsController.class);
            }
        }
        return girlsController;
    }

    public static WeatherController getWeatherController() {
        if (weatherController == null) {
            synchronized (monitor) {
                weatherController = RetrofitManager.getInstance().create(WeatherController.class);
            }
        }
        return weatherController;
    }

    public static AppController getAppController() {
        if (appController == null) {
            synchronized (monitor) {
                appController = RetrofitManager.getInstance().create(AppController.class);
            }
        }
        return appController;
    }

    public static void reset() {
        busController = null;
        girlsController = null;
        weatherController = null;
    }


}
