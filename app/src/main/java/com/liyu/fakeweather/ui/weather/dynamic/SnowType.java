package com.liyu.fakeweather.ui.weather.dynamic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.IntDef;


import com.liyu.fakeweather.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Created by liyu on 2017/8/18.
 */

public class SnowType extends BaseWeatherType {

    public static final int SNOW_LEVEL_1 = 20;//小雪级别
    public static final int SNOW_LEVEL_2 = 40;//中雪级别
    public static final int SNOW_LEVEL_3 = 60;//大到暴雪级别

    private ArrayList<Snow> mSnows;

    private Paint mPaint;

    private Snow snow;

    private Shader shader;

    private int snowLevel = SNOW_LEVEL_1;

    Bitmap bitmap;

    Matrix matrix;

    public SnowType(Context context, @SnowLevel int snowLevel) {
        super(context);
        this.snowLevel = snowLevel;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        mSnows = new ArrayList<>();
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_snow_ground);
    }

    @Override
    public void onDrawElements(Canvas canvas) {
        clearCanvas(canvas);
        canvas.drawColor(Color.parseColor("#62B1FF"));
        mPaint.setAlpha(255);
        matrix.reset();
        matrix.postScale(0.25f, 0.25f);
        matrix.postTranslate(getWidth() - bitmap.getWidth() * 0.25f, getHeight() - bitmap.getHeight() * 0.25f);
        canvas.drawBitmap(bitmap, matrix, mPaint);
        for (int i = 0; i < mSnows.size(); i++) {
            snow = mSnows.get(i);
            shader = new RadialGradient(snow.x, snow.y, snow.size, Color.parseColor("#99ffffff"),
                    Color.parseColor("#00ffffff"), Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            canvas.drawCircle(snow.x, snow.y, snow.size, mPaint);
        }
        for (int i = 0; i < mSnows.size(); i++) {
            snow = mSnows.get(i);
            snow.y += snow.speed;
            if (snow.y > getHeight() + snow.size) {
                snow.y = 0 - snow.size;
                snow.x = getRandom(0, getWidth());
            }
        }
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public void generateElements() {
        mSnows.clear();
        for (int i = 0; i < snowLevel; i++) {
            Snow snow = new Snow(
                    getRandom(0, getWidth()),
                    getRandom(0, getHeight()),
                    getRandom(dp2px(8), dp2px(20)),
                    getRandom(1, snowLevel / 10)
            );
            mSnows.add(snow);
        }
    }

    private class Snow {
        /**
         * 雪花 x 轴坐标
         */
        int x;
        /**
         * 雪花 y 轴坐标
         */
        int y;
        /**
         * 雪花大小
         */
        int size;
        /**
         * 雪花移动速度
         */
        int speed;

        public Snow(int x, int y, int size, int speed) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
        }

    }

    @IntDef({SNOW_LEVEL_1, SNOW_LEVEL_2, SNOW_LEVEL_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SnowLevel {
    }
}
