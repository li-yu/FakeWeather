package com.liyu.fakeweather.ui.weather.dynamic;

import android.content.Context;
import android.text.TextUtils;

import com.liyu.fakeweather.utils.SettingsUtil;

/**
 * Created by liyu on 2017/10/12.
 */

public class TypeUtil {

    public static BaseWeatherType getType(Context context, ShortWeatherInfo info) {
        if (SettingsUtil.getWeatherSrc() == SettingsUtil.WEATHER_SRC_HEFENG) {
            return getHeWeatherType(context, info);
        } else {
            return null;
        }
    }

    private static BaseWeatherType getHeWeatherType(Context context, ShortWeatherInfo info) {
        if (info != null && TextUtils.isDigitsOnly(info.getCode())) {
            int code = Integer.parseInt(info.getCode());
            if (code == 100) {//晴
                return new SunnyType(context, info);
            } else if (code >= 101 && code <= 103) {//多云
                return new SunnyType(context, info);
            } else if (code == 104) {//阴
                return new OvercastType(context, info);
            } else if (code >= 200 && code <= 213) {//各种风
                return new SunnyType(context, info);
            } else if (code >= 300 && code <= 303) {//各种阵雨
                return new RainType(context, RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2);
            } else if (code == 304) {//阵雨加冰雹
                return new SunnyType(context, info);
            } else if (code >= 305 && code <= 312) {//各种雨
                if (code == 305 || code == 309) {//小雨
                    return new RainType(context, RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1);
                } else if (code == 306) {//中雨
                    return new RainType(context, RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2);
                } else//大到暴雨
                    return new RainType(context, RainType.RAIN_LEVEL_3, RainType.WIND_LEVEL_3);
            } else if (code == 313) {//冻雨
                return new SunnyType(context, info);
            } else if (code >= 400 && code <= 407) {//各种雪
                return new SnowType(context, SnowType.SNOW_LEVEL_3);
            } else if (code >= 500 && code <= 501) {//雾
                return new FogType(context);
            } else if (code == 502) {//霾

            } else if (code >= 503 && code <= 508) {//各种沙尘暴
                return new SandstormType(context);
            } else if (code == 900) {//热

            } else if (code == 901) {//冷

            } else {//未知

            }
            return null;
        } else
            return null;
    }
}
