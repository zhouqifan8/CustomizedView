package com.example.customizedview.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.customizedview.R;

/**
 * Created by zhouqifan on 2020/6/2
 * 圆形进度条
 */
public class ProgressBar extends View {
    private int mInnerBackgroundColor = Color.RED;
    private int mOutBackgroundColor = Color.RED;
    private int mRoundWidth = 10;//px
    private float mProgressTextSize = 15;
    private int mProgressTextColor = Color.RED;

    private Paint mInnerPaint, mOutPaint, mTextPaint;

    private int mMax = 100;
    private int mProgress = 0;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mInnerBackgroundColor = array.getColor(R.styleable.ProgressBar_innerBackground, mInnerBackgroundColor);
        mOutBackgroundColor = array.getColor(R.styleable.ProgressBar_outBackground, mOutBackgroundColor);
        mRoundWidth = (int) array.getDimension(R.styleable.ProgressBar_roundWidth, dip2px(10));
        mProgressTextSize = array.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, sp2px(mProgressTextSize));
        mProgressTextColor = array.getColor(R.styleable.ProgressBar_progressTextColor, mProgressTextColor);
        array.recycle();

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBackgroundColor);
        mInnerPaint.setStrokeWidth(mRoundWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setColor(mOutBackgroundColor);
        mOutPaint.setStrokeWidth(mRoundWidth);
        mOutPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
    }

    private int sp2px(float mProgressTextSize) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mProgressTextSize, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先画内园
        int center = getWidth() / 2;
        canvas.drawCircle(center, center, center - mRoundWidth / 2, mInnerPaint);
        //画外圆，画圆弧
        RectF rectF = new RectF(mRoundWidth / 2, mRoundWidth / 2, getWidth() - mRoundWidth / 2, getHeight() - mRoundWidth / 2);
        if (mProgress == 0) return;
        float percent = (float) mProgress / mMax;
        canvas.drawArc(rectF, -90, percent * 360, false, mOutPaint);

        //画进度文字
        String text = (int) (percent * 100) + "%";
        Rect textBouns = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBouns);
        int x = getWidth() / 2 - textBouns.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mInnerPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, mTextPaint);
    }

    //给几个方法
    public synchronized void setMax(int max) {
        if (max < 0) {

        }
        this.mMax = max;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0 || progress > mMax) {

        }
        this.mProgress = progress;
        invalidate();
    }
}
