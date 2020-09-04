package com.example.customizedview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.customizedview.lettersidebar.LetterSideBarActivity;
import com.example.customizedview.mytextview.MyTextViewActivity;
import com.example.customizedview.progressbar.ProgressBarActivity;
import com.example.customizedview.ratingbar.RatingBarActivity;
import com.example.customizedview.shapeview.ShapeViewActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void text(View view) {
        startActivity(new Intent(this, MyTextViewActivity.class));
    }

    public void ProgressBarActivity(View view) {
        startActivity(new Intent(this, ProgressBarActivity.class));
    }

    public void RatingBar(View view) {
        startActivity(new Intent(this, RatingBarActivity.class));
    }

    public void ShapeView(View view) {
        startActivity(new Intent(this, ShapeViewActivity.class));
    }

    public void LetterSideBar(View view) {
        startActivity(new Intent(this, LetterSideBarActivity.class));
    }
}
