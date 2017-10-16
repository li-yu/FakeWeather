package com.liyu.fakeweather.ui.weather.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;

/**
 * Created by liyu on 2017/8/19.
 */

public class OvercastType extends BaseWeatherType {

    private Paint mPaint;

    private Path mPathFront;

    private Path mPathRear;

    private float speed;

    private Path fanPath = new Path();// 旋转的风扇的扇叶
    private Path fanPillarPath = new Path();// 旋转的风扇的柱子
    private float fanPillerHeight;
    private float curRotate;// 旋转的风扇的角度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度

    PathMeasure measure;

    public OvercastType(Context context) {
        super(context);
        mPathFront = new Path();
        mPathRear = new Path();
        mPaint = new Paint();
        pos = new float[2];
        tan = new float[2];
        measure = new PathMeasure();
    }

    @Override
    public void onDrawElements(Canvas canvas) {

        mPaint.reset();
        mPaint.setColor(Color.parseColor("#59789D"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        clearCanvas(canvas);
        canvas.drawColor(Color.parseColor("#6D8DB1"));

        mPaint.setAlpha(100);

        mPathRear.reset();
        mPathRear.moveTo(getWidth(), getHeight() * 6 / 7);
        mPathRear.rQuadTo(-getWidth() / 2, -20, -getWidth(), getHeight() / 10);
        mPathRear.lineTo(0, getHeight());
        mPathRear.lineTo(getWidth(), getHeight());
        mPathRear.close();
        canvas.drawPath(mPathRear, mPaint);

        mPaint.setAlpha(255);

        mPathFront.reset();
        mPathFront.moveTo(0, getHeight() * 6 / 7);
        mPathFront.rQuadTo(getWidth() / 2, -20, getWidth(), getHeight() / 10);
        mPathFront.lineTo(getWidth(), getHeight());
        mPathFront.lineTo(0, getHeight());
        mPathFront.close();

        // draw fan and fanPillar
        drawFan(canvas, mPathFront, 0.618f, 1f);

        drawFan(canvas, mPathRear, 0.15f, 0.4f);

        mPaint.reset();
        mPaint.setColor(Color.parseColor("#59789D"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(mPathFront, mPaint);
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
            speed = Float.valueOf(1.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        speed = Math.max(speed, 0.75f);
        curRotate += 0.001f * speed;
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
    public int getColor() {
        return 0;
    }

    @Override
    public void generateElements() {
        final float textSize = getHeight() / 32f;
// fanPath和fanPillarPath的中心点在扇叶圆形的中间
        fanPath.reset();
        final float fanSize = textSize * 0.2f;// 风扇底部半圆的半径
        final float fanHeight = textSize * 2f;
        final float fanCenterOffsetY = fanSize * 1.6f;
        // fanPath.moveTo(fanSize, -fanCenterOffsetY);
        // 也可以用arcTo
        // 从右边到底部到左边了的弧
        fanPath.addArc(new RectF(-fanSize, -fanSize - fanCenterOffsetY, fanSize, fanSize - fanCenterOffsetY), 0,
                180);
        // fanPath.lineTo(0, -fanHeight - fanCenterOffsetY);
        fanPath.quadTo(-fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, 0, -fanHeight - fanCenterOffsetY);
        fanPath.quadTo(fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, fanSize, -fanCenterOffsetY);
        fanPath.close();

        fanPillarPath.reset();
        final float fanPillarSize = textSize * 0.25f;// 柱子的宽度
        fanPillarPath.moveTo(0, 0);
        fanPillerHeight = textSize * 4f;// 柱子的宽度
        fanPillarPath.lineTo(fanPillarSize, fanPillerHeight);
        fanPillarPath.lineTo(-fanPillarSize, fanPillerHeight);
        fanPillarPath.close();
    }

    @Override
    public void startAnimation(final DynamicWeatherView dynamicWeatherView) {

    }
}
