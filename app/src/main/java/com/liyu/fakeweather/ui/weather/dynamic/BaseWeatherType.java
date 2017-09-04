package com.liyu.fakeweather.ui.weather.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import java.util.Random;

/**
 * Created by liyu on 2017/8/16.
 */

public abstract class BaseWeatherType implements WeatherHandler {
    private Context mContext;
    private int mWidth;
    private int mHeight;

    public Context getContext() {
        return mContext;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public abstract int getColor();

    public abstract void generateElements();

    public void startAnimation(DynamicWeatherView dynamicWeatherView){

    }

    public BaseWeatherType(Context context) {
        mContext = context;
    }

    @Override
    public void onSizeChanged(Context context, int w, int h) {
        mWidth = w;
        mHeight = h;
        generateElements();
    }

    protected void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    protected int getRandom(int min, int max) {
        if (max < min) {
            return 1;
        }
        return min + new Random().nextInt(max - min);
    }

    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
