package com.example.customizedview.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.customizedview.R;

/**
 * Created by zhouqifan on 2020/6/3
 */
public class RatingBar extends View {
    private Bitmap mStarNormalBitmap, mStarFocusBitmap;
    private int mGradeNumber = 5;
    private int mCurrentGrade = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starNormal, 0);
        if (starNormalId == 0) {
            throw new RuntimeException("请设置 starNormal");
        }
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);
        int starFocusId = array.getResourceId(R.styleable.RatingBar_starFocus, 0);
        if (starFocusId == 0) {
            throw new RuntimeException("请设置 starFocus");
        }
        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);
        mGradeNumber = array.getInt(R.styleable.RatingBar_gradeNumber, mGradeNumber);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = mStarNormalBitmap.getWidth() * mGradeNumber;
        int height = mStarNormalBitmap.getHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mGradeNumber; i++) {
            int x = i * mStarNormalBitmap.getWidth();
            if (mCurrentGrade > i) {
                canvas.drawBitmap(mStarFocusBitmap, x, 0, null);
            } else {
                canvas.drawBitmap(mStarNormalBitmap, x, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int currentGrade = (int) (x / mStarNormalBitmap.getWidth() + 1);
                if (currentGrade < 0) {
                    currentGrade = 0;
                }
                if (currentGrade > mGradeNumber) {
                    currentGrade = mGradeNumber;
                }
                if (mCurrentGrade != currentGrade) {
                    mCurrentGrade = currentGrade;
                    invalidate();
                }
                break;
        }
        return true;
    }
}
