package com.liyu.fakeweather.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.airbnb.lottie.L;
import com.liyu.fakeweather.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2017/10/19.
 */

public class PagerTitleView extends View implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    private Paint roundPaint;

    private Paint textPaint;

    private float textSize;

    private float roundSize;

    private float roundPadding = 10;

    private List<String> titles;

    public PagerTitleView(Context context) {
        this(context, null);
    }

    public PagerTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        titles = new ArrayList<>();

        textSize = SizeUtils.sp2px(context, 18);
        roundSize = 10;

        roundPaint = new Paint();
        roundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        roundPaint.setStrokeWidth(2);
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setStrokeWidth(0);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = titles.size();
        float roundTotleWidth = 2 * roundSize * count + (count - 1) * roundPadding;
        float startX = getWidth() / 2 - roundTotleWidth / 2 + roundSize;

        for (int i = 0; i < count; i++) {
            canvas.drawCircle(startX + (2 * roundSize * i) + roundPadding * i, getHeight() / 2, roundSize, roundPaint);
        }

    }

    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        titles.clear();
        PagerAdapter adapter = mViewPager.getAdapter();
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            titles.add("test");
        }
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = SizeUtils.dp2px(getContext(), 100);
        int desiredHeight = SizeUtils.dp2px(getContext(), 48);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
