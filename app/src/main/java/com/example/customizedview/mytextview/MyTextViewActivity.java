package com.example.customizedview.mytextview;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.customizedview.R;

import java.util.ArrayList;
import java.util.List;

public class MyTextViewActivity extends AppCompatActivity {
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout linearLayout;
    private List<MyTextView> myTextViewList;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_text_view);
        myTextViewList = new ArrayList<>();
        linearLayout = findViewById(R.id.linearLayout);
        mViewPager = findViewById(R.id.viewPager);
        initLinearLayout();
        initViewPager();
    }

    private void initLinearLayout() {
        for (int i = 0; i < items.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            MyTextView myTextView = new MyTextView(this);
            myTextView.setTextSize(24);
            myTextView.setChangeColor(Color.RED);
            myTextView.setText(items[i]);
            myTextView.setLayoutParams(params);
            linearLayout.addView(myTextView);
            myTextViewList.add(myTextView);
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return ItemFragment.newInstance(items[i]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int i1) {
                MyTextView left = myTextViewList.get(position);
                left.setDirection(MyTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1 - positionOffset);

                if (position == items.length - 1) return;

                MyTextView right = myTextViewList.get(position + 1);
                right.setDirection(MyTextView.Direction.LEFT_TO_RIGHT);
                right.setCurrentProgress(positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void back(View view) {
        finish();
    }
}
