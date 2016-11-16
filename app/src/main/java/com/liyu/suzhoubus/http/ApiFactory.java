package com.liyu.suzhoubus.http;

import com.liyu.suzhoubus.http.api.BusController;
import com.liyu.suzhoubus.http.api.GankController;
import com.liyu.suzhoubus.http.api.WeatherController;

/**
 * Created by liyu on 2016/8/25.
 */
public class ApiFactory {
    protected static final Object monitor = new Object();

    private static BusController busController;
    private static GankController gankController;
    private static WeatherController weatherController;

    public static BusController getBusController() {
        if (busController == null) {
            synchronized (monitor) {
                busController = RetrofitManager.getInstance().create(BusController.class);
            }
        }
        return busController;
    }

    public static GankController getGankController() {
        if (gankController == null) {
            synchronized (monitor) {
                gankController = RetrofitManager.getInstance().create(GankController.class);
            }
        }
        return gankController;
    }

    public static WeatherController getWeatherController() {
        if (weatherController == null) {
            synchronized (monitor) {
                weatherController = RetrofitManager.getInstance().create(WeatherController.class);
            }
        }
        return weatherController;
    }


    public static void reset() {
        busController = null;
        gankController = null;
        weatherController = null;
    }


}
