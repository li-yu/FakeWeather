package com.liyu.fakeweather.ui.weather.dynamic;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;

import com.liyu.fakeweather.model.IFakeWeather;

/**
 * Created by liyu on 2017/8/16.
 */

public class DynamicWeatherView2 extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private DrawThread mDrawThread;
    private BaseWeatherType weatherType;
    private int mViewWidth;
    private int mViewHeight;
    private IFakeWeather originWeather;
    int fromColor;

    public DynamicWeatherView2(Context context) {
        this(context, null);
    }

    public DynamicWeatherView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicWeatherView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        ShortWeatherInfo info = new ShortWeatherInfo();
        info.setCode("100");
        info.setWindSpeed("2");
        info.setSunrise("05:29");
        info.setSunset("19:00");
        info.setMoonrise("06:42");
        info.setMoonset("19:39");
        weatherType = new SunnyType(context, info);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        getHolder().addCallback(this);

    }

    public int getColor() {
        return weatherType.getColor();
    }

    public void setType(final BaseWeatherType type) {
        if (this.weatherType != null) {
            this.weatherType.endAnimation(this, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fromColor = weatherType.getColor();
                    weatherType = type;
                    if (weatherType != null) {
                        weatherType.onSizeChanged(mContext, mViewWidth, mViewHeight);
                    }
                    weatherType.startAnimation(DynamicWeatherView2.this, fromColor);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            fromColor = type.getColor();
            this.weatherType = type;
            if (this.weatherType != null) {
                this.weatherType.onSizeChanged(mContext, mViewWidth, mViewHeight);
            }
            this.weatherType.startAnimation(this, fromColor);
        }

    }

    public IFakeWeather getOriginWeather() {
        return originWeather;
    }

    public void setOriginWeather(IFakeWeather originWeather) {
        this.originWeather = originWeather;
    }

    public BaseWeatherType getWeather() {
        return weatherType;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        if (weatherType != null) {
            weatherType.onSizeChanged(mContext, w, h);
        }
    }

    public void onResume() {
        if (mDrawThread != null) {
            mDrawThread.setSuspend(false);
        }
    }

    public void onPause() {
        mDrawThread.setSuspend(true);
    }

    public void onDestroy() {
        mDrawThread.setRunning(false);
        getHolder().removeCallback(this);
        if (this.weatherType != null) {
            this.weatherType.endAnimation(this, null);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawThread = new DrawThread();
        mDrawThread.mSurface = holder;
        mDrawThread.setRunning(true);
        mDrawThread.start();
        weatherType.startAnimation(this, weatherType.getColor());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mDrawThread.setRunning(false);
    }

    private class DrawThread extends Thread {

        private SurfaceHolder mSurface;

        private boolean isRunning = false;

        private boolean suspended = false;

        private Object control = new Object();

        public void setRunning(boolean running) {
            isRunning = running;
        }

        public void setSuspend(boolean suspend) {
            if (!suspend) {
                synchronized (control) {
                    control.notifyAll();
                }
            }

            this.suspended = suspend;
        }

        @Override
        public void run() {
            while (isRunning) {
                if (suspended) {
                    try {
                        synchronized (control) {
                            control.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                final long startTime = AnimationUtils.currentAnimationTimeMillis();
                Canvas canvas = mSurface.lockCanvas();
                if (canvas != null) {
                    weatherType.onDrawElements(canvas);
                    mSurface.unlockCanvasAndPost(canvas);
                    System.out.print("fuck");//如果不加这一行，在某些手机上竟然会 ANR
                }
                final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                final long needSleepTime = 16 - drawTime;
                if (needSleepTime > 0) {
                    SystemClock.sleep(needSleepTime);
                }

            }
        }
    }
}
