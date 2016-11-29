package com.liyu.suzhoubus.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.utils.SizeUtils;
import com.liyu.suzhoubus.utils.ThemeUtil;

/**
 * Created by liyu on 2016/8/23.
 */
public class StationIndicator extends View {

    private Paint paint;

    private float roundWidth;

    private float innerCircle;

    private boolean isChecked;

    private int color;

    public StationIndicator(Context context) {
        this(context, null);
    }

    public StationIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StationIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorItem);
        color = mTypedArray.getColor(R.styleable.IndicatorItem_indicatorColor, ThemeUtil.getThemeColor(context, R.attr.colorPrimary));
        roundWidth = mTypedArray.getDimension(R.styleable.IndicatorItem_indicatorRingWidth, SizeUtils.dp2px(context, 3));
        isChecked = mTypedArray.getBoolean(R.styleable.IndicatorItem_indicatorChecked, false);
        innerCircle = mTypedArray.getDimension(R.styleable.IndicatorItem_indicatorInnerCircle, SizeUtils.dp2px(context, 4));
        mTypedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int center = getWidth() / 2;
        int height = getHeight();
        int padding = SizeUtils.dp2px(this.getContext(), 4);

        if (isChecked) {
            //绘制内圆
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(center, height / 2, innerCircle, this.paint);
        }

        //绘制外圆
        paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(roundWidth);
        canvas.drawCircle(center, height / 2, innerCircle + padding, this.paint);

        this.paint.setStrokeWidth(roundWidth);
        canvas.drawLine(center, 0, center, height / 2 - innerCircle - padding, paint);
        canvas.drawLine(center, height / 2 + innerCircle + padding, center, height, paint);

        super.onDraw(canvas);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        if (isChecked == checked)
            return;
        this.isChecked = checked;
        postInvalidate();
    }

}
