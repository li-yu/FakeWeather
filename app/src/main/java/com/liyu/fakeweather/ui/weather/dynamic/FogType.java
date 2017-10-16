package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.liyu.fakeweather.R;

/**
 * Created by liyu on 2017/10/13.
 */

public class FogType extends BaseWeatherType {

    private int color = 0xFF8CADD3;     // 主题色

    private Paint mPaint;               // 画笔

    private float fogFactor1;           // 雾变化因子1，动态改变圆半径

    private float fogFactor2;           // 雾变化因子2，动态改变圆半径

    private float transFactor;          // 背景图位移变化因子

    private Bitmap bitmap;

    private Matrix matrix;

    private Shader shader;

    public FogType(Context context) {
        super(context);
        mPaint = new Paint();
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_fog_ground);
    }

    @Override
    public void onDrawElements(Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        clearCanvas(canvas);
        canvas.drawColor(color);

        matrix.reset();
        matrix.postScale(0.25f, 0.25f);
        matrix.postTranslate(transFactor, getHeight() - bitmap.getHeight() * 0.25f);
        canvas.drawBitmap(bitmap, matrix, mPaint);

        mPaint.setShader(shader);

        mPaint.setAlpha((int) (255 * (1 - fogFactor1)));

        canvas.drawCircle(getWidth() / 2 + getWidth() / 6, getHeight(), getWidth() / 2 * fogFactor1, mPaint);

        mPaint.setAlpha((int) (255 * (1 - fogFactor2)));

        canvas.drawCircle(getWidth() / 2 + getWidth() / 6, getHeight(), getWidth() / 2 * fogFactor2, mPaint);
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void generateElements() {
        shader = new RadialGradient(getWidth() / 2 + getWidth() / 6, getHeight(), getWidth() / 2, Color.parseColor("#00ffffff"),
                Color.parseColor("#ffffffff"), Shader.TileMode.CLAMP);
    }

    @Override
    public void startAnimation(DynamicWeatherView dynamicWeatherView) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(6000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fogFactor1 = (float) animation.getAnimatedValue();
            }
        });
        animator.setStartDelay(3000);
        animator.start();

        ValueAnimator animator2 = ValueAnimator.ofFloat(0, 1);
        animator2.setDuration(6000);
        animator2.setRepeatCount(-1);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fogFactor2 = (float) animation.getAnimatedValue();
            }
        });
        animator2.start();

        ValueAnimator animator3 = ValueAnimator.ofFloat(getWidth() - bitmap.getWidth() * 0.25f);
        animator3.setDuration(1000);
        animator3.setRepeatCount(0);
        animator3.setInterpolator(new OvershootInterpolator());
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transFactor = (float) animation.getAnimatedValue();
            }
        });
        animator3.start();

    }
}
