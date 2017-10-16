package com.liyu.fakeweather.utils;

import com.liyu.fakeweather.App;
import com.liyu.fakeweather.R;

/**
 * Created by liyu on 2016/11/18.
 */

public class SettingsUtil {

    public static final String WEATHER_SRC = "weather_src";//天气源
    public static final String WEATHER_SHARE_TYPE = "weather_share_type";//天气分享形式
    public static final String WEATHER_KEY = "weather_key";//天气 key
    public static final String WEATHER_DATE_TYPE = "weather_date_type";//天气日期显示样式，日期 or 星期
    public static final String THEME = "theme_color";//主题
    public static final String CLEAR_CACHE = "clean_cache";//清空缓存
    public static final String BUS_REFRESH_FREQ = "bus_refresh_freq";//公交自动刷新频率
    public static final String TTS_VOICE_TYPE = "tts_voice_type";//讯飞语音人声

    public static final int WEATHER_DATE_TYPE_WEEK = 0;

    public static final int WEATHER_DATE_TYPE_DATE = 1;

    public static final int WEATHER_SRC_HEFENG = 0;

    public static final int WEATHER_SRC_XIAOMI = 1;

    public static void setTtsVoiceType(String type) {
        SPUtil.put(App.getContext(), TTS_VOICE_TYPE, type);
    }

    public static String getTtsVoiceType() {
        return (String) SPUtil.get(App.getContext(), TTS_VOICE_TYPE, App.getContext().getResources().getStringArray(R.array.tts_voice_value)[0]);
    }

    public static void setWeatherShareType(String type) {
        SPUtil.put(App.getContext(), WEATHER_SHARE_TYPE, type);
    }

    public static String getWeatherShareType() {
        return (String) SPUtil.get(App.getContext(), WEATHER_SHARE_TYPE, App.getContext().getResources().getStringArray(R.array.share_type)[0]);
    }

    public static void setWeatherSrc(int src) {
        SPUtil.put(App.getContext(), WEATHER_SRC, src);
    }

    public static int getWeatherSrc() {
        return (int) SPUtil.get(App.getContext(), WEATHER_SRC, WEATHER_SRC_HEFENG);
    }

    public static void setWeatherDateType(int type) {
        SPUtil.put(App.getContext(), WEATHER_DATE_TYPE, type);
    }

    public static int getWeatherDateType() {
        return (int) SPUtil.get(App.getContext(), WEATHER_DATE_TYPE, 0);
    }

    public static void setWeatherKey(String key) {
        SPUtil.put(App.getContext(), WEATHER_KEY, key);
    }

    public static String getWeatherKey() {
        return (String) SPUtil.get(App.getContext(), WEATHER_KEY, "");
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
