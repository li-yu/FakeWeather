package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.liyu.fakeweather.R;

/**
 * Created by liyu on 2017/8/19.
 */

public class HazeType extends BaseWeatherType {

    private Paint mPaint;

    private Path mPathFront;

    private Path mPathRear;

    private float speed;

    private float rotate;

    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作

    private float φ;

    private Shader shader;

    Camera camera;

    public HazeType(Context context) {
        super(context);
        setColor(0xFF7F8195);
        mPathFront = new Path();
        mPathRear = new Path();
        mPaint = new Paint();
        mMatrix = new Matrix();
        camera = new Camera();
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_haze_ground);
    }

    @Override
    public void onDrawElements(Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        clearCanvas(canvas);
        canvas.drawColor(getDynamicColor());

        shader = new LinearGradient(0, getHeight(), getWidth(), getHeight(), 0x33ffffff, 0xccffffff, Shader.TileMode.CLAMP);
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

        canvas.save();

        camera.save(); // 保存 Camera 的状态
        camera.rotateX(90 - 90 * rotate);
        canvas.translate(getWidth() - mBitmap.getWidth() * 0.3f * 4 / 3, getHeight()); // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-(getWidth() - mBitmap.getWidth() * 0.3f * 4 / 3), -(getHeight())); // 旋转之前把绘制内容移动到轴心（原点）
        camera.restore(); // 恢复 Camera 的状态

        mPaint.setAlpha(255);
        mMatrix.reset();
        mMatrix.postScale(0.3f, 0.3f);
        mMatrix.postTranslate(getWidth() - mBitmap.getWidth() * 0.3f * 4 / 3, getHeight() - mBitmap.getHeight() * 0.3f + 2f);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        canvas.restore();

        mPaint.setAlpha(60);
        canvas.drawPath(mPathRear, mPaint);

        mPaint.setAlpha(100);
        canvas.drawPath(mPathFront, mPaint);

    }

    @Override
    public void generateElements() {

    }

    @Override
    public void startAnimation(final DynamicWeatherView dynamicWeatherView, int fromColor) {
        super.startAnimation(dynamicWeatherView, fromColor);
        ValueAnimator animator1 = ValueAnimator.ofFloat(0, 1);
        animator1.setInterpolator(new OvershootInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                speed = (float) animation.getAnimatedValue() * 32;
                rotate = (float) animation.getAnimatedValue();
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator1);
        animSet.setDuration(1000);
        animSet.start();

    }

    @Override
    public void endAnimation(DynamicWeatherView dynamicWeatherView, Animator.AnimatorListener listener) {
        super.endAnimation(dynamicWeatherView, listener);

        ValueAnimator animator1 = ValueAnimator.ofFloat(1, 0);
        animator1.setInterpolator(new AccelerateInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                speed = (float) animation.getAnimatedValue() * 32;
                rotate = (float) animation.getAnimatedValue();
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator1);
        animSet.setDuration(1000);
        animSet.addListener(listener);
        animSet.start();
    }
}
