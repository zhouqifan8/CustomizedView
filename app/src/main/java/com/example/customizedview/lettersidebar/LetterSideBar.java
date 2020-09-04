package com.example.customizedview.lettersidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.customizedview.R;

/**
 * Created by zhouqifan on 2020/6/3
 */
public class LetterSideBar extends View {
    private Paint mNormalPaint, mSelectPaint;
    private int normalColor = Color.BLUE;
    private int selectColor = Color.RED;
    private float normalLetterSize = 10;
    private float selectLetterSize = 12;
    private int itemHeight;
    private String mCueerenLetter = "A";

    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        normalColor = array.getColor(R.styleable.LetterSideBar_normalColor, normalColor);
        selectColor = array.getColor(R.styleable.LetterSideBar_selectColor, selectColor);
        normalLetterSize = array.getDimensionPixelSize(R.styleable.LetterSideBar_normalLetterSize, sp2px(normalLetterSize));
        selectLetterSize = array.getDimensionPixelSize(R.styleable.LetterSideBar_selectLetterSize, sp2px(selectLetterSize));
        array.recycle();

        mNormalPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setTextSize(normalLetterSize);
        mNormalPaint.setColor(normalColor);

        mSelectPaint = new Paint();
        mSelectPaint.setAntiAlias(true);
        mSelectPaint.setTextSize(selectLetterSize);
        mSelectPaint.setColor(selectColor);
    }

    private int sp2px(float mProgressTextSize) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mProgressTextSize, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算指定宽度=左右padding+字母宽度(取决于画笔)
        int weight = getPaddingLeft() + getPaddingRight() + (int) mNormalPaint.measureText("A");
        //高度可以直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(weight, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        for (int i = 0; i < letters.length; i++) {
            int x = (int) (getWidth() / 2 - mNormalPaint.measureText(letters[i]) / 2);
            int centerY = itemHeight * i + itemHeight / 2 + getPaddingTop();
            Paint.FontMetricsInt fontMetricsInt = mNormalPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseLine = centerY + dy;
            if (mCueerenLetter.equals(letters[i])) {
                canvas.drawText(letters[i], x, baseLine, mSelectPaint);
            } else {
                canvas.drawText(letters[i], x, baseLine, mNormalPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //计算出当前位置
                float currentY = event.getY();
                int currentP = (int) (currentY / itemHeight);
                if (currentP < 0) {
                    currentP = 0;
                }
                if (currentP > letters.length - 1) {
                    currentP = letters.length - 1;
                }
                String currentLetter = letters[currentP];
                onSelectLetterListener.setSelectLetter(currentLetter, true);
                if (mCueerenLetter.equals(currentLetter)) {
                    return true;
                } else {
                    mCueerenLetter = currentLetter;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onSelectLetterListener.setSelectLetter(mCueerenLetter, false);
                    }
                }, 800);

                break;
        }
        return true;
    }

    private onSelectLetterListener onSelectLetterListener;

    public void setSelectLetterListener(onSelectLetterListener letterListener) {
        this.onSelectLetterListener = letterListener;
    }

    public interface onSelectLetterListener {
        void setSelectLetter(String letter, boolean select);
    }
}
