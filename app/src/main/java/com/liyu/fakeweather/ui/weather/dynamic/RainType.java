package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.view.animation.OvershootInterpolator;

import com.liyu.fakeweather.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Created by liyu on 2017/8/17.
 */

public class RainType extends BaseWeatherType {

    public static final int RAIN_LEVEL_1 = 10;//小雨级别
    public static final int RAIN_LEVEL_2 = 20;//中雨级别
    public static final int RAIN_LEVEL_3 = 30;//大到暴雨级别

    public static final int WIND_LEVEL_1 = 20;//小风
    public static final int WIND_LEVEL_2 = 30;//中风
    public static final int WIND_LEVEL_3 = 45;//大风

    private ArrayList<Rain> mRains;

    private Paint mPaint;

    private Rain rain;

    private int rainLevel = RAIN_LEVEL_1;

    private int windLevel = WIND_LEVEL_1;

    float speed;

    Bitmap bitmap;

    Matrix matrix;

    public RainType(Context context, @RainLevel int rainLevel, @WindLevel int windLevel) {
        super(context);
        this.rainLevel = rainLevel;
        this.windLevel = windLevel;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        mRains = new ArrayList<>();
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_rain_ground);
    }

    @Override
    public void onDrawElements(Canvas canvas) {
        clearCanvas(canvas);
        canvas.drawColor(Color.parseColor("#6188DA"));
        mPaint.setAlpha(255);
        matrix.reset();
        matrix.postScale(0.2f, 0.2f);
        matrix.postTranslate(speed, getHeight() - bitmap.getHeight() * 0.2f + 2f);
        canvas.drawBitmap(bitmap, matrix, mPaint);
        for (int i = 0; i < mRains.size(); i++) {
            rain = mRains.get(i);
            mPaint.setAlpha(rain.alpha);
            canvas.drawLine(rain.x, rain.y, (float) (rain.x + rain.length * Math.sin(Math.PI * windLevel / 180)), (float) (rain.y + rain.length * Math.cos(Math.PI * windLevel / 180)), mPaint);
        }
        for (int i = 0; i < mRains.size(); i++) {
            rain = mRains.get(i);
            rain.x += (rain.length + rain.speed) * Math.sin(Math.PI * windLevel / 180);
            rain.y += (rain.length + rain.speed) * Math.cos(Math.PI * windLevel / 180);
            if (rain.x > getWidth() || rain.y > getHeight()) {
                rain.x = getRandom(getWidth() / 3, getWidth() - getWidth() / 5);
                rain.y = getRandom(getHeight() / 2, getHeight() - getHeight() / 5);
            }
        }
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public void generateElements() {
        mRains.clear();
        for (int i = 0; i < rainLevel; i++) {
            Rain rain = new Rain(
                    getRandom(getWidth() / 4, getWidth() - getWidth() / 5),
                    getRandom(getHeight() / 2, getHeight() - getHeight() / 5),
                    getRandom(dp2px(10), dp2px(20)),
                    getRandom(1, 3),
                    getRandom(20, 100)
            );
            mRains.add(rain);
        }
        ValueAnimator animator = ValueAnimator.ofFloat(getWidth() - bitmap.getWidth() * 0.2f);
        animator.setDuration(1000);
        animator.setRepeatCount(0);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                speed = (float) animation.getAnimatedValue();
            }
        });
        animator.start();
    }

    private class Rain {
        /**
         * 雨点 x 轴坐标
         */
        int x;
        /**
         * 雨点 y 轴坐标
         */
        int y;
        /**
         * 雨点长度
         */
        int length;
        /**
         * 雨点移动速度
         */
        int speed;
        /**
         * 雨点透明度
         */
        int alpha;

        public Rain(int x, int y, int length, int speed, int alpha) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.speed = speed;
            this.alpha = alpha;
        }
    }

    @IntDef({RAIN_LEVEL_1, RAIN_LEVEL_2, RAIN_LEVEL_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RainLevel {
    }

    @IntDef({WIND_LEVEL_1, WIND_LEVEL_2, WIND_LEVEL_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WindLevel {
    }
}
