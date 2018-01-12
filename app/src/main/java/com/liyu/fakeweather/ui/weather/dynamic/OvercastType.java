package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.liyu.fakeweather.utils.SizeUtils;

/**
 * Created by liyu on 2017/8/19.
 */

public class OvercastType extends BaseWeatherType {

    private static final int hillColor = 0xFF59789D; //山坡的颜色

    private Paint mPaint;

    private Path mPathFront;                         // 近处的山坡

    private Path mPathRear;                          // 远处的山坡

    private Path fanPath = new Path();               // 旋转的风扇的扇叶
    private Path fanPillarPath = new Path();         // 旋转的风扇的柱子
    private float fanPillerHeight;
    private float curRotate;                         // 旋转的风扇的角度

    private float[] pos;                             // 当前点的实际位置
    private float[] tan;                             // 当前点的tangent值,用于计算图片所需旋转的角度

    PathMeasure measure;

    private String windSpeed;

    private Shader cloudShader;

    private Path cloudPath;

    private float cloudTransFactor;

    private float hillTransFactor;

    private float cloudOffset = 0;

    public OvercastType(Context context, ShortWeatherInfo info) {
        super(context);
        setColor(0xFF6D8DB1);
        mPathFront = new Path();
        mPathRear = new Path();
        mPaint = new Paint();
        pos = new float[2];
        tan = new float[2];
        measure = new PathMeasure();
        windSpeed = info.getWindSpeed();
        cloudPath = new Path();
        cloudOffset = SizeUtils.dp2px(context, 32);
    }

    @Override
    public void onDrawElements(Canvas canvas) {

        mPaint.reset();
        mPaint.setColor(hillColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        clearCanvas(canvas);
        canvas.drawColor(getDynamicColor());

        mPaint.setAlpha(100);

        mPathRear.reset();
        mPathRear.moveTo(getWidth(), getHeight() - getHeight() * 1 / 7 * hillTransFactor);
        mPathRear.rQuadTo(-getWidth() / 2, -20 * hillTransFactor, -getWidth(), getHeight() / 10 * hillTransFactor);
        mPathRear.lineTo(0, getHeight());
        mPathRear.lineTo(getWidth(), getHeight());
        mPathRear.close();
        canvas.drawPath(mPathRear, mPaint);

        mPaint.setAlpha(255);

        mPathFront.reset();
        mPathFront.moveTo(0, getHeight() - getHeight() * 1 / 7 * hillTransFactor);
        mPathFront.rQuadTo(getWidth() / 2, -20 * hillTransFactor, getWidth(), getHeight() / 10 * hillTransFactor);
        mPathFront.lineTo(getWidth(), getHeight());
        mPathFront.lineTo(0, getHeight());
        mPathFront.close();

        drawFan(canvas, mPathFront, 0.618f * hillTransFactor, 1f);

        drawFan(canvas, mPathRear, 0.15f * hillTransFactor, 0.4f);

        mPaint.reset();
        mPaint.setColor(hillColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(mPathFront, mPaint);

        mPaint.setShader(cloudShader);
        cloudPath.reset();
        cloudPath.addCircle(getWidth() * cloudTransFactor, getHeight() * 0.618f - cloudOffset, getHeight() / 22, Path.Direction.CW);
        cloudPath.addCircle(getWidth() * cloudTransFactor, getHeight() * 0.618f, getHeight() / 22, Path.Direction.CW);
        cloudPath.addCircle(getWidth() * cloudTransFactor - getHeight() / 19, getHeight() * 0.618f - cloudOffset * 2 / 3, getHeight() / 26, Path.Direction.CW);
        cloudPath.addCircle(getWidth() * cloudTransFactor + getHeight() / 20, getHeight() * 0.618f - cloudOffset * 2 / 3, getHeight() / 30, Path.Direction.CW);
        canvas.drawPath(cloudPath, mPaint);

    }

    private void drawFan(Canvas canvas, Path path, float location, float scale) {
        int saveCount = canvas.save();
        measure.setPath(path, false);
        measure.getPosTan(getWidth() * location, pos, tan);
        canvas.translate(pos[0], pos[1] - fanPillerHeight * scale);
        canvas.scale(scale, scale);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawPath(fanPillarPath, mPaint);
        canvas.rotate(curRotate * 360f);
        float speed = 0f;
        try {
            speed = Float.valueOf(windSpeed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        speed = Math.max(speed, 0.75f);
        curRotate += 0.0002f * speed;
        if (curRotate > 1f) {
            curRotate = 0f;
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(fanPath, mPaint);
        canvas.rotate(120f);
        canvas.drawPath(fanPath, mPaint);
        canvas.rotate(120f);
        canvas.drawPath(fanPath, mPaint);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void generateElements() {
        cloudShader = new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight() * 0.618f, 0xFFFFFFFF,
                0x00FFFFFF, Shader.TileMode.CLAMP);
        final float textSize = getHeight() / 32f;
        fanPath.reset();
        final float fanSize = textSize * 0.2f;// 风扇底部半圆的半径
        final float fanHeight = textSize * 2f;
        final float fanCenterOffsetY = fanSize * 1.6f;
        fanPath.addArc(new RectF(-fanSize, -fanSize - fanCenterOffsetY, fanSize, fanSize - fanCenterOffsetY), 0,
                180);
        fanPath.quadTo(-fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, 0, -fanHeight - fanCenterOffsetY);
        fanPath.quadTo(fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, fanSize, -fanCenterOffsetY);
        fanPath.close();

        fanPillarPath.reset();
        final float fanPillarSize = textSize * 0.20f;// 柱子的宽度
        fanPillarPath.moveTo(0, 0);
        fanPillerHeight = textSize * 4f;// 柱子的高度
        fanPillarPath.lineTo(2, 0);
        fanPillarPath.lineTo(fanPillarSize, fanPillerHeight);
        fanPillarPath.lineTo(-fanPillarSize, fanPillerHeight);
        fanPillarPath.lineTo(-2, 0);
        fanPillarPath.close();
    }

    @Override
    public void startAnimation(final DynamicWeatherView dynamicWeatherView, int fromColor) {
        super.startAnimation(dynamicWeatherView, fromColor);
        ValueAnimator animator = ValueAnimator.ofFloat(-0.2f, 1.2f);
        animator.setDuration(30000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cloudTransFactor = (float) animation.getAnimatedValue();
            }
        });
        animator.start();

        ValueAnimator animator2 = ValueAnimator.ofFloat(0, 1);
        animator2.setDuration(1000);
        animator2.setRepeatCount(0);
        animator2.setInterpolator(new OvershootInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                hillTransFactor = (float) animation.getAnimatedValue();
            }
        });
        animator2.start();
    }

    @Override
    public void endAnimation(DynamicWeatherView dynamicWeatherView, Animator.AnimatorListener listener) {
        super.endAnimation(dynamicWeatherView, listener);
        ValueAnimator animator = ValueAnimator.ofFloat(1, -1);
        animator.setDuration(1000);
        animator.setRepeatCount(0);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                hillTransFactor = (float) animation.getAnimatedValue();
            }
        });
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.start();
    }
}
