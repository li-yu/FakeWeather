package com.liyu.fakeweather.ui.weather.dynamic;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by liyu on 2017/8/16.
 */

public interface WeatherHandler {
    void onDrawElements(Canvas canvas);

    void onSizeChanged(Context context, int width, int height);
}
