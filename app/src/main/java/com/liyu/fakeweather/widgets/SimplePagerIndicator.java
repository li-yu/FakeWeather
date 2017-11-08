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

import com.liyu.fakeweather.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2017/10/19.
 */

public class SimplePagerIndicator extends View implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    private Paint roundPaint;

    private Paint selectedRoundPaint;

    private Paint textPaint;

    private float textSize;

    private float roundRadius;

    private float roundPadding = 10;

    private List<String> titles;

    float textBaseline;
    float roundBaseline;

    private int selectedPosition = 0;

    private float positionOffset = 0.0f;

    float textWidth;

    public SimplePagerIndicator(Context context) {
        this(context, null);
    }

    public SimplePagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimplePagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        titles = new ArrayList<>();

        textSize = SizeUtils.sp2px(context, 18);
        roundRadius = 5;

        roundPaint = new Paint();
        roundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        roundPaint.setStrokeWidth(2);
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAntiAlias(true);
        roundPaint.setAlpha(100);

        selectedRoundPaint = new Paint();
        selectedRoundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        selectedRoundPaint.setStrokeWidth(2);
        selectedRoundPaint.setColor(Color.WHITE);
        selectedRoundPaint.setAntiAlias(true);

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
        if (count == 0)
            return;
        textBaseline = getHeight() / 2 - textPaint.getFontMetrics().top / 2 - textPaint.getFontMetrics().bottom / 2;
        roundBaseline = textBaseline + roundRadius + 20;

        float roundTotalWidth = 2 * roundRadius * count + (count - 1) * roundPadding;
        float startX = getWidth() / 2 - roundTotalWidth / 2 + roundRadius;

        for (int i = 0; i < count; i++) {
            canvas.drawCircle(startX + (2 * roundRadius * i) + roundPadding * i, roundBaseline, roundRadius, roundPaint);
        }

        float offSetX = (roundPadding + 2 * roundRadius) * positionOffset;

        canvas.drawCircle(startX + (2 * roundRadius * selectedPosition) + roundPadding * selectedPosition + offSetX, roundBaseline, roundRadius, selectedRoundPaint);

        if (positionOffset >= 0) {
            if (selectedPosition < titles.size() - 1) {
                textWidth = textPaint.measureText(titles.get(selectedPosition)) + textPaint.measureText(titles.get(selectedPosition + 1));
                textPaint.setAlpha((int) (255 * (1 - Math.abs(positionOffset))));
                canvas.drawText(titles.get(selectedPosition), getWidth() / 2 - textWidth / 2 * positionOffset, textBaseline, textPaint);
                textPaint.setAlpha((int) (255 * Math.abs(positionOffset)));
                canvas.drawText(titles.get(selectedPosition + 1), getWidth() / 2 + textWidth / 2 - textWidth / 2 * positionOffset, textBaseline, textPaint);
            } else {
                textPaint.setAlpha((int) (255 * (1 - Math.abs(positionOffset))));
                canvas.drawText(titles.get(selectedPosition), getWidth() / 2 - textWidth / 2 * positionOffset, textBaseline, textPaint);
            }
        } else {
            if (selectedPosition > 0) {
                textWidth = textPaint.measureText(titles.get(selectedPosition)) + textPaint.measureText(titles.get(selectedPosition - 1));
                textPaint.setAlpha((int) (255 * (1 - Math.abs(positionOffset))));
                canvas.drawText(titles.get(selectedPosition), getWidth() / 2 - textWidth / 2 * positionOffset, textBaseline, textPaint);
                textPaint.setAlpha((int) (255 * Math.abs(positionOffset)));
                canvas.drawText(titles.get(selectedPosition - 1), getWidth() / 2 - textWidth / 2 - textWidth / 2 * positionOffset, textBaseline, textPaint);
            }
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
            titles.add(adapter.getPageTitle(i).toString());
        }
        invalidate();
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
        if (position >= selectedPosition) {
            // 1 -> 2 -> 3 滑动
            this.positionOffset = positionOffset;
        } else if (position < selectedPosition) {
            // 1 <- 2 <- 3 滑动
            this.positionOffset = -(1 - positionOffset);
        }
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        selectedPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
