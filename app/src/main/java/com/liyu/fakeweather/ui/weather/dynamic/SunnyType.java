package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.LinearInterpolator;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.utils.TimeUtils;

/**
 * Created by liyu on 2017/8/18.
 */

public class SunnyType extends BaseWeatherType {

    private Paint mPaint;               // 画笔

    private Path mPathFront;            //近处的波浪 path

    private Path mPathRear;             //远处的波浪 path

    private Path sunPath;               // 太阳升起的半圆轨迹

    private PathMeasure sunMeasure;

    private float speed;                //振幅，用于初始时的动画效果

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的切线写角度值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 小船儿
    private Matrix mMatrix;             // 矩阵,用于对小船儿进行一些操作

    private static final float bitmapScale = 0.2f; //图片的缩小倍数

    private PathMeasure measure;

    private float φ;

    private Shader shader;

    private int color = 0xFF51C0F8;

    private static final int colorDay = 0xFF51C0F8;

    private static final int colorNight = 0xFF7F9EE9;

    private static final int colorWaveStartNight = 0x337187DB;

    private static final int colorWaveEndNight = 0xFF7187DB;

    private static final int colorWaveStartDay = 0x33FFFFFF;

    private static final int colorWaveEndDay = 0xFFFFFFFF;

    private float currentSunPosition;
    private float currentMoonPosition;

    private float[] sunPos;
    private float[] sunTan;

    private float[] moonPos;
    private float[] moonTan;

    public SunnyType(Context context, ShortWeatherInfo info) {
        super(context);
        mPathFront = new Path();
        mPathRear = new Path();
        sunPath = new Path();
        mPaint = new Paint();
        pos = new float[2];
        tan = new float[2];
        mMatrix = new Matrix();
        measure = new PathMeasure();
        sunMeasure = new PathMeasure();
        currentSunPosition = TimeUtils.getTimeDiffPercent(info.getSunrise(), info.getSunset());
        currentMoonPosition = TimeUtils.getTimeDiffPercent(info.getMoonrise(), info.getMoonset());
        if (currentSunPosition >= 0 && currentSunPosition <= 1) {
            color = colorDay;
            mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_boat_day);
        } else {
            color = colorNight;
            mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_boat_night);
        }

    }

    @Override
    public void onDrawElements(Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        clearCanvas(canvas);
        canvas.drawColor(color);

        if (currentSunPosition >= 0 && currentSunPosition <= 1) {
            mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
            canvas.drawCircle(sunPos[0], sunPos[1], 40, mPaint);
            mPaint.setMaskFilter(null);
            if (shader == null) {
                shader = new LinearGradient(0, getHeight(), getWidth(), getHeight(), colorWaveStartDay, colorWaveEndDay, Shader.TileMode.CLAMP);
            }
        } else {
            mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
            canvas.drawCircle(moonPos[0], moonPos[1], 40, mPaint);
            mPaint.setColor(color);
            canvas.drawCircle(moonPos[0] + 20, moonPos[1] - 20, 40, mPaint);
            mPaint.setColor(Color.WHITE);
            mPaint.setMaskFilter(null);
            if (shader == null) {
                shader = new LinearGradient(0, getHeight(), getWidth(), getHeight(), colorWaveStartNight, colorWaveEndNight, Shader.TileMode.CLAMP);
            }
        }

        mPaint.setShader(shader);

        φ -= 0.05f;

        float y, y2;

        double ω = 2 * Math.PI / getWidth();

        mPathRear.reset();
        mPathFront.reset();
        mPathFront.moveTo(0, getHeight());
        mPathRear.moveTo(0, getHeight());
        for (float x = 0; x <= getWidth(); x += 20) {
            /**
             *  y=Asin(ωx+φ)+k
             *  A—振幅越大，波形在y轴上最大与最小值的差值越大
             *  ω—角速度， 控制正弦周期(单位角度内震动的次数)
             *  φ—初相，反映在坐标系上则为图像的左右移动，通过不断改变φ,达到波浪移动效果
             *  k—偏距，反映在坐标系上则为图像的上移或下移。
             */
            y = (float) (speed * Math.cos(ω * x + φ) + getHeight() * 6 / 7);
            y2 = (float) (speed * Math.sin(ω * x + φ) + getHeight() * 6 / 7 - 8);
            mPathFront.lineTo(x, y);
            mPathRear.lineTo(x, y2);
        }

        mPathFront.lineTo(getWidth(), getHeight());
        mPathRear.lineTo(getWidth(), getHeight());

        mPaint.setAlpha(60);
        canvas.drawPath(mPathRear, mPaint);

        measure.setPath(mPathFront, false);
        measure.getPosTan(measure.getLength() * 0.618f, pos, tan);
        mMatrix.reset();
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        mMatrix.postScale(bitmapScale, bitmapScale);
        mMatrix.postRotate(degrees, mBitmap.getWidth() * bitmapScale / 2, mBitmap.getHeight() * bitmapScale / 2);
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2 * bitmapScale, pos[1] - mBitmap.getHeight() * bitmapScale + 4);
        mPaint.setAlpha(255);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        mPaint.setAlpha(100);
        canvas.drawPath(mPathFront, mPaint);

    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void generateElements() {
        sunPath.reset();
        RectF rectF = new RectF(0 + 100, getHeight() * (8.5f / 10f) - getWidth() / 2, getWidth() - 100, getHeight() * (8.5f / 10f) + getWidth() / 2);
        sunPath.arcTo(rectF, 180, 180);
        sunMeasure.setPath(sunPath, false);
    }

    @Override
    public void startAnimation(final DynamicWeatherView2 dynamicWeatherView) {
        sunPos = new float[2];
        sunTan = new float[2];
        moonPos = new float[2];
        moonTan = new float[2];
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(0);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                speed = (float) animation.getAnimatedValue() * 32;
            }
        });
        animator.start();

        ValueAnimator sunAnimator = ValueAnimator.ofFloat(0, 1);
        sunAnimator.setDuration(3000);
        sunAnimator.setRepeatCount(0);
        sunAnimator.setInterpolator(PathInterpolatorCompat.create(0.5f, 1));
        sunAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sunMeasure.getPosTan(sunMeasure.getLength() * (float) animation.getAnimatedValue() * currentSunPosition, sunPos, sunTan);
                sunMeasure.getPosTan(sunMeasure.getLength() * (float) animation.getAnimatedValue() * currentMoonPosition, moonPos, moonTan);
            }
        });
        sunAnimator.start();
    }
}
