package com.liyu.fakeweather.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.utils.ThemeUtil;

public class DragLayout extends RelativeLayout {

    private ViewDragHelper mViewDragHelper;

    private Point originPoint = new Point();

    private View targetView;

    private DragListener listener;

    private boolean callEvent = false;

    public DragLayout(Context context) {
        super(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DragLayout bind(Activity activity) {
        activity.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragCallback());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        originPoint.x = targetView.getLeft();
        originPoint.y = targetView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        targetView = findViewById(R.id.picture);
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == targetView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (releasedChild == targetView) {
                if (callEvent || yvel > 8000) {
                    if (listener != null) {
                        listener.onDragFinished();
                    }
                    callEvent = false;
                } else {
                    mViewDragHelper.settleCapturedViewAt(originPoint.x, originPoint.y);
                    invalidate();
                }
            }
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            if (top > originPoint.y) {
                float a = (float) (top - originPoint.y) / (float) (getMeasuredHeight() - originPoint.y);
                setBackgroundColor(ThemeUtil.changeAlpha(0xff000000, 1 - a));
                targetView.setScaleX(1 - a);
                targetView.setScaleY(1 - a);
                if ((top - originPoint.y) > getMeasuredHeight() / 5) {
                    callEvent = true;
                } else {
                    callEvent = false;
                }
            }
        }
    }

    public void setDragListener(DragListener listener) {
        this.listener = listener;
    }

    public interface DragListener {
        void onDragFinished();
    }

}
