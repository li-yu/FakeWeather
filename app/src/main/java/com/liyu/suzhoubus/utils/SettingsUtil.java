package com.liyu.suzhoubus.utils;

import com.liyu.suzhoubus.App;
import com.liyu.suzhoubus.R;

/**
 * Created by liyu on 2016/11/18.
 */

public class SettingsUtil {

    public static final String WEATHER_SHARE_TYPE = "weather_share_type";//天气分享形式
    public static final String THEME = "theme_color";//主题
    public static final String CLEAR_CACHE = "clean_cache";//清空缓存
    public static final String BUS_REFRESH_FREQ = "bus_refresh_freq";//公交自动刷新频率

    public static void setWeatherShareType(String type) {
        SPUtil.put(App.getContext(), WEATHER_SHARE_TYPE, type);
    }

    public static String getWeatherShareType() {
        return (String) SPUtil.get(App.getContext(), WEATHER_SHARE_TYPE, App.getContext().getResources().getStringArray(R.array.share_type)[0]);
    }

    public static void setTheme(int themeIndex) {
        SPUtil.put(App.getContext(), THEME, themeIndex);
    }

    public static int getTheme() {
        return (int) SPUtil.get(App.getContext(), THEME, 0);
    }

    public static void setBusRefreshFreq(int freq) {
        SPUtil.put(App.getContext(), BUS_REFRESH_FREQ, freq);
    }

    public static int getBusRefreshFreq() {
        return (int) SPUtil.get(App.getContext(), BUS_REFRESH_FREQ, 10);
    }

}
