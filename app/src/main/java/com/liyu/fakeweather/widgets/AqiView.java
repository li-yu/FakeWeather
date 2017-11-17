package com.liyu.fakeweather.widgets;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.liyu.fakeweather.model.FakeWeather;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.utils.SizeUtils;

/**
 * 自定义空气质量环形控件
 * Created by liyu on 2017/8/23.
 */

public class AqiView extends View {

    private boolean canRefresh = true;

    private Paint paint;
    private Paint textPaint;
    private Paint textPaint2;

    private int roundColor; //圆环底色

    private int roundProgressColor; // 进度条颜色

    private int textColor; //文字颜色

    private float textSize; //文字大小

    private float roundWidth = 28; //圆环宽度

    private FakeWeather.FakeAqi aqi;

    private Shader shader;

    private float speed;

    private int[] aqiColors = new int[]{
            0xFF62001E,
            0xFF6BCD07,
            0xFF6BCD07,
            0xFFFBD029,
            0xFFFE8800,
            0xFFFE0000,
            0xFF970454,
            0xFF62001E};

    int currentAqiColor;

    public AqiView(Context context) {
        this(context, null);
    }

    public AqiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AqiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textSize = SizeUtils.dp2px(context, 12);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setStrokeWidth(0);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint2 = new Paint();
        textPaint2.setAntiAlias(true);
        textPaint2.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (aqi == null)
            return;

        paint.setShader(null);

        paint.setColor(0xCC888888);

        RectF rectF;
        if (getHeight() >= getWidth()) {
            rectF = new RectF(0 + roundWidth, 0 + roundWidth, getWidth() - roundWidth, getWidth() - roundWidth);
        } else {
            rectF = new RectF(0 + (getWidth() - getHeight()) / 2 + roundWidth, 0 + roundWidth, getWidth() - (getWidth() - getHeight()) / 2 - roundWidth, getHeight() - roundWidth);
        }

        if (shader == null) {
            shader = new SweepGradient(rectF.left + rectF.width() / 2, rectF.top + rectF.height() / 2, aqiColors, new float[]{
                    0.125f,
                    0.125f,
                    0.3f,
                    0.375f,
                    0.45f,
                    0.525f,
                    0.675f,
                    0.925f});
            Matrix matrix = new Matrix();
            matrix.postRotate(45, rectF.left + rectF.width() / 2, rectF.top + rectF.height() / 2);
            shader.setLocalMatrix(matrix);
        }

        canvas.drawArc(rectF, 135, 270, false, paint);

        paint.setShader(shader);

        int aqiValue = Integer.parseInt(aqi.getApi());

        canvas.drawArc(rectF, 135, (aqiValue * speed / 500f) * 270.0f, false, paint);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式

        textPaint.setColor(Color.GRAY);

        canvas.drawText(String.valueOf((int) (aqiValue * speed)), rectF.centerX(), baseLineY, textPaint);

        float qltyStringWidth = textPaint.measureText(aqi.getQlty());

        RectF textRectf = new RectF(getWidth() / 2 - qltyStringWidth / 1.5f, rectF.bottom, getWidth() / 2 + qltyStringWidth / 1.5f, rectF.bottom + rectF.width() / 4);

        textPaint2.setColor(currentAqiColor);

        canvas.drawRoundRect(textRectf, 8, 8, textPaint2);

        int baseLineY2 = (int) (textRectf.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        textPaint.setColor(Color.WHITE);
        canvas.drawText(aqi.getQlty(), textRectf.centerX(), baseLineY2, textPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = SizeUtils.dp2px(getContext(), 100);
        int desiredHeight = SizeUtils.dp2px(getContext(), 120);

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

    public void setApi(IFakeWeather weather) {
        if (weather == null || !canRefresh) {
            return;
        }
        this.aqi = weather.getFakeAqi();
        int apiValue = Integer.parseInt(aqi.getApi());
        int targetColor;
        if (apiValue <= 50) {
            targetColor = aqiColors[2];
        } else if (apiValue <= 100) {
            targetColor = aqiColors[3];
        } else if (apiValue <= 150) {
            targetColor = aqiColors[4];
        } else if (apiValue <= 200) {
            targetColor = aqiColors[5];
        } else if (apiValue <= 300) {
            targetColor = aqiColors[6];
        } else {
            targetColor = aqiColors[7];
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                speed = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator animator2 = ValueAnimator.ofObject(new ArgbEvaluator(), aqiColors[2], targetColor);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.setRepeatCount(0);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAqiColor = (int) animation.getAnimatedValue();
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(animator2);
        animSet.setDuration(2000);
        animSet.start();

        canRefresh = false;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                canRefresh = true;
            }
        }, 2000);
    }
}
