package com.liyu.fakeweather.widgets;

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

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.colorTextDark2nd));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setTextSize(SizeUtils.sp2px(context, 10));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        Path minPath = new Path();
        Path maxPath = new Path();

        int heightScale = (int) (height / (maxValue - minValue));
        int widthtScale = (int) (width / (2 * minTemp.size()));
        
        Rect rect = new Rect();
        for (int i = 0; i < minTemp.size(); i++) {
            paint.setStyle(Paint.Style.FILL);
            if (i == 0) {
                minPath.moveTo((2 * i + 1) * widthtScale, (maxValue - minTemp.get(i)) * heightScale);
            } else {
                minPath.lineTo((2 * i + 1) * widthtScale, (maxValue - minTemp.get(i)) * heightScale);
            }
            canvas.drawCircle((2 * i + 1) * widthtScale, (maxValue - minTemp.get(i)) * heightScale, 5, paint);
            String valueStr = String.valueOf(minTemp.get(i)) + "℃";
            paint.getTextBounds(valueStr, 0, valueStr.length(), rect);
            float textHeight = rect.height();
            float textWidth = rect.width();
            canvas.drawText(valueStr, (2 * i + 1) * widthtScale - textWidth / 2, (maxValue - minTemp.get(i)) * heightScale + textHeight + 20, paint);
        }

        for (int i = 0; i < maxTemp.size(); i++) {
            if (i == 0) {
                maxPath.moveTo((2 * i + 1) * widthtScale, (maxValue - maxTemp.get(i)) * heightScale);
            } else {
                maxPath.lineTo((2 * i + 1) * widthtScale, (maxValue - maxTemp.get(i)) * heightScale);
            }
            canvas.drawCircle((2 * i + 1) * widthtScale, (maxValue - maxTemp.get(i)) * heightScale, 5, paint);
            String valueStr = String.valueOf(maxTemp.get(i)) + "℃";
            paint.getTextBounds(valueStr, 0, valueStr.length(), rect);
            float textHeight = rect.height();
            float textWidth = rect.width();
            canvas.drawText(valueStr, (2 * i + 1) * widthtScale - textWidth / 2, (maxValue - maxTemp.get(i)) * heightScale - textHeight - 5, paint);
        }
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(minPath, paint);
        canvas.drawPath(maxPath, paint);
        super.onDraw(canvas);
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
        postInvalidate();
    }
}
