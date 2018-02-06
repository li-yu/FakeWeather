package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.DrawableRes;
import android.view.animation.LinearInterpolator;

import java.util.Random;

/**
 * Created by liyu on 2017/8/16.
 */

public abstract class BaseWeatherType implements WeatherHandler {
    private Context mContext;
    private int mWidth;
    private int mHeight;

    protected int color;

    protected int dynamicColor;

    public float lastUpdatedTime;

    public float getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(float lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

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

    public int getColor() {
        return color;
    }

    public int getDynamicColor() {
        return dynamicColor;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public abstract void generateElements();

    public void startAnimation(DynamicWeatherView dynamicWeatherView, int fromColor) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, color);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dynamicColor = (int) animation.getAnimatedValue();
            }
        });
        animator.start();
    }

    public void endAnimation(DynamicWeatherView dynamicWeatherView, Animator.AnimatorListener listener) {

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

    protected Bitmap decodeResource(Bitmap inBitmap, @DrawableRes int res) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        if (inBitmap != null) {
            option.inBitmap = inBitmap;
        }
        option.inMutable = true;
        inBitmap = BitmapFactory.decodeResource(getContext().getResources(), res, option);
        return inBitmap;
    }
}
