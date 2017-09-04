package com.liyu.fakeweather.ui.weather.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by liyu on 2017/8/16.
 */

public class DynamicWeatherView extends TextureView implements TextureView.SurfaceTextureListener {

    private Context mContext;
    private DrawThread mDrawThread;
    private BaseWeatherType mWeather;
    private int mViewWidth;
    private int mViewHeight;

    public DynamicWeatherView(Context context) {
        this(context, null);
    }

    public DynamicWeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mWeather = new RainType(context);
        setSurfaceTextureListener(this);
    }

    public int getColor() {
        return mWeather.getColor();
    }

    public void setType(BaseWeatherType weatherType) {
        this.mWeather = weatherType;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        if (mWeather != null) {
            mWeather.onSizeChanged(mContext, w, h);
        }
    }

    public void resume() {
        mDrawThread.reStart();
    }

    public void pause() {
        if (mDrawThread != null && mDrawThread.isRunning) {
            mDrawThread.pause();
        }
    }

    /**
     * Invoked when a {@link TextureView}'s SurfaceTexture is ready for use.
     *
     * @param surface The surface returned by
     *                {@link TextureView#getSurfaceTexture()}
     * @param width   The width of the surface
     * @param height  The height of the surface
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mDrawThread = new DrawThread();
        mDrawThread.setRunning(true);
        mDrawThread.start();
        mWeather.startAnimation(this);

    }

    /**
     * Invoked when the {@link SurfaceTexture}'s buffers size changed.
     *
     * @param surface The surface returned by
     *                {@link TextureView#getSurfaceTexture()}
     * @param width   The new width of the surface
     * @param height  The new height of the surface
     */
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        mViewWidth = width;
        mViewHeight = height;
        if (mWeather != null) {
            mWeather.onSizeChanged(mContext, width, height);
        }
    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is about to be destroyed.
     * If returns true, no rendering should happen inside the surface texture after this method
     * is invoked. If returns false, the client needs to call {@link SurfaceTexture#release()}.
     * Most applications should return true.
     *
     * @param surface The surface about to be destroyed
     */
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mDrawThread.setRunning(false);
        return true;
    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is updated through
     * {@link SurfaceTexture#updateTexImage()}.
     *
     * @param surface The surface just updated
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private class DrawThread extends Thread {

        private boolean isRunning = false;

        private boolean suspended = false;

        public void setRunning(boolean running) {
            isRunning = running;
        }

        public boolean isRunning() {
            return isRunning;
        }

        /**
         * 暂停
         */
        public void pause() {
            suspended = true;
        }

        /**
         * 继续
         */
        public synchronized void reStart() {
            suspended = false;
            notify();
        }

        @Override
        public void run() {
            while (isRunning) {
                try {
                    synchronized (this) {
                        while (suspended) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {

                }
                if (mWeather != null && mViewWidth != 0 && mViewHeight != 0) {
                    final long startTime = AnimationUtils.currentAnimationTimeMillis();
                    Canvas canvas = lockCanvas();
                    if (canvas != null) {
                        mWeather.onDrawElements(canvas);
                        final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                        final long needSleepTime = 16 - drawTime;
                        if (needSleepTime > 0) {
                            SystemClock.sleep(needSleepTime);
                        }
                        if (isRunning) {
                            unlockCanvasAndPost(canvas);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }
}
