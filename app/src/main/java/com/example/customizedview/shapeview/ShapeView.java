package com.example.customizedview.shapeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhouqifan on 2020/6/2
 */
public class ShapeView extends View {

    private Shape mCurrentShape = Shape.Circle;
    private Paint mPaint;
    private Path mPath;

    public enum Shape {
        Circle, Square, Triangle
    }

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
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

        switch (mCurrentShape) {
            case Circle:
                //画圆形
                int center = getWidth() / 2;
                mPaint.setColor(Color.BLACK);
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                //画正方形
                mPaint.setColor(Color.BLUE);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case Triangle:
                //画三角形
                mPaint.setColor(Color.RED);
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    mPath.lineTo(0, (float) (getWidth()/2*Math.sqrt(3)));
                    mPath.lineTo(getWidth(), (float) (getWidth()/2*Math.sqrt(3)));
                    mPath.close();
                }
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    public void exChange() {
        switch (mCurrentShape) {
            case Circle:
                //画圆形
                mCurrentShape = Shape.Square;
                break;
            case Square:
                //画正方形
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                //画三角形
                mCurrentShape = Shape.Circle;
                break;
        }
        invalidate();
    }
}
