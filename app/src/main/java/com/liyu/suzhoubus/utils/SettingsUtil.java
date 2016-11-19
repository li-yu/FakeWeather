package com.liyu.suzhoubus.utils;

import com.liyu.suzhoubus.App;
import com.liyu.suzhoubus.R;

/**
 * Created by liyu on 2016/11/18.
 */

public class SettingsUtil {

    public static final String WEATHER_SHARE_TYPE = "weather_share_type";//天气分享形式
    public static final String WEATHER_ALERT = "weather_alert";//灾害预警
    public static final String ABOUT = "about";//关于
    public static final String CLEAR_CACHE = "clear_cache";//清空缓存

    public static void setWeatherShareType(String type) {
        SPUtil.put(App.getContext(), WEATHER_SHARE_TYPE, type);
    }

    public static String getWeatherShareType() {
        return (String) SPUtil.get(App.getContext(), WEATHER_SHARE_TYPE, App.getContext().getResources().getStringArray(R.array.share_type)[0]);
    }

    public static void setWeatherAlert(boolean enable) {
        SPUtil.put(App.getContext(), WEATHER_ALERT, enable);
    }

    public static boolean getWeatherAlert() {
        return (Boolean) SPUtil.get(App.getContext(), WEATHER_ALERT, false);
    }

}
