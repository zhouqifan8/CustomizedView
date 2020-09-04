package com.example.customizedview.shapeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.customizedview.R;

public class ShapeViewActivity extends AppCompatActivity {
    private ShapeView shapeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_view);
        shapeView = findViewById(R.id.shapeView);
    }

    public void exchange(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shapeView.exChange();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException i) {

                    }
                }
            }
        }).start();
    }

    public void back(View view) {
        finish();
    }
}
