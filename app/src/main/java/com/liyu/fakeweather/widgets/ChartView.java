package com.liyu.fakeweather.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.utils.SizeUtils;

import java.util.List;

/**
 * Created by liyu on 2016/12/8.
 */

public class ChartView extends View {

    private Paint paint;

    private List<Integer> minTemp;
    private List<Integer> maxTemp;

    private int minValue = 0;
    private int maxValue = 0;

    Path minPath;
    Path maxPath;

    private int textMargin;

    private float mAnimatorValue;

    private static final int LINE_COLOR = 0xffE1E5E8;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setTextSize(SizeUtils.sp2px(context, 10));
        textMargin = SizeUtils.dp2px(context, 8);
        minPath = new Path();
        maxPath = new Path();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();

        final int heightScale = (int) (height / (maxValue - minValue));
        final int widthtScale = (int) (width / (2 * minTemp.size()));

        paint.setStrokeWidth(widthtScale / 3);

        Rect rect = new Rect();
        for (int i = 0; i < minTemp.size(); i++) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(LINE_COLOR);
            canvas.drawLine((2 * i + 1) * widthtScale, (maxValue - minTemp.get(i)) * heightScale * mAnimatorValue, (2 * i + 1) * widthtScale, (maxValue - maxTemp.get(i)) * heightScale * mAnimatorValue, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getContext().getResources().getColor(R.color.colorTextDark2nd));
            final String valueStrMin = String.valueOf(minTemp.get(i));
            paint.getTextBounds(valueStrMin, 0, valueStrMin.length(), rect);
            canvas.drawText(valueStrMin + "°", (2 * i + 1) * widthtScale - rect.width() / 2, (maxValue - minTemp.get(i)) * heightScale * mAnimatorValue + rect.height() + textMargin, paint);
            final String valueStrMax = String.valueOf(maxTemp.get(i));
            paint.getTextBounds(valueStrMax, 0, valueStrMax.length(), rect);
            canvas.drawText(valueStrMax + "°", (2 * i + 1) * widthtScale - rect.width() / 2, (maxValue - maxTemp.get(i)) * heightScale * mAnimatorValue - textMargin, paint);
        }

    }

    public void setData(List<Integer> minTemp, List<Integer> maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        minValue = minTemp.get(0);
        maxValue = maxTemp.get(0);
        for (int i : minTemp) {
            if (i < minValue) {
                minValue = i;
            }
        }
        for (int n : maxTemp) {
            if (n > maxValue) {
                maxValue = n;
            }
        }
        minValue = minValue - 3;
        maxValue = maxValue + 3;
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration((minTemp.size() + 1) * 200);
        valueAnimator.setRepeatCount(0);
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredHeight = SizeUtils.dp2px(getContext(), 140);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height;

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
        setMeasuredDimension(widthSize, height);
    }
}
