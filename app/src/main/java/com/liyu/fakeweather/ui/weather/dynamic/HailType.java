package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.liyu.fakeweather.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Created by liyu on 2017/8/18.
 */

public class HailType extends BaseWeatherType {

    private ArrayList<Hail> hails;

    private Paint mPaint;

    private Hail hail;

    float speed;

    Bitmap bitmap;

    Matrix matrix;

    public HailType(Context context) {
        super(context);
        setColor(0xFF0CB399);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        hails = new ArrayList<>();
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_hail_ground);
    }

    @Override
    public void onDrawElements(Canvas canvas) {
        clearCanvas(canvas);
        canvas.drawColor(getDynamicColor());
        mPaint.setAlpha(255);
        matrix.reset();
        matrix.postScale(0.25f, 0.25f);
        matrix.postTranslate(speed, getHeight() - bitmap.getHeight() * 0.25f);
        canvas.drawBitmap(bitmap, matrix, mPaint);

        for (int i = 0; i < hails.size(); i++) {
            hail = hails.get(i);
            mPaint.setAlpha((int) (255 * ((float) hail.y / (float) getHeight())));
            canvas.save();
            canvas.rotate(45, hail.x + hail.size / 2, hail.y + hail.size / 2);
            canvas.drawRect(hail.x, hail.y, hail.x + hail.size, hail.y + hail.size, mPaint);
            canvas.restore();
        }
        for (int i = 0; i < hails.size(); i++) {
            hail = hails.get(i);
            hail.y += hail.speed;
            if (hail.y > getHeight() + hail.size * 2) {
                hail.y = 0 - hail.size * 2;
                hail.x = getRandom(0, getWidth());
            }
        }
    }

    @Override
    public void generateElements() {
        hails.clear();
        for (int i = 0; i < 15; i++) {
            Hail hail = new Hail(
                    getRandom(0, getWidth()),
                    getRandom(0, getHeight()),
                    getRandom(dp2px(3), dp2px(8)),
                    getRandom(8, 18)
            );
            hails.add(hail);
        }
    }

    @Override
    public void startAnimation(DynamicWeatherView dynamicWeatherView, int fromColor) {
        super.startAnimation(dynamicWeatherView, fromColor);
        ValueAnimator animator = ValueAnimator.ofFloat(getWidth() - bitmap.getWidth() * 0.25f);
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

    @Override
    public void endAnimation(DynamicWeatherView dynamicWeatherView, Animator.AnimatorListener listener) {
        super.endAnimation(dynamicWeatherView, listener);
        ValueAnimator animator = ValueAnimator.ofFloat(getWidth() - bitmap.getWidth() * 0.25f, getWidth());
        animator.setDuration(1000);
        animator.setRepeatCount(0);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                speed = (float) animation.getAnimatedValue();
            }
        });
        animator.addListener(listener);
        animator.start();
    }

    private class Hail {
        int x;
        int y;
        int size;
        int speed;

        public Hail(int x, int y, int size, int speed) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
        }

    }

}
