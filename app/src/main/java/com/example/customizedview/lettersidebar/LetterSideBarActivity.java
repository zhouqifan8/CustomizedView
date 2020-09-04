package com.example.customizedview.lettersidebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customizedview.R;

public class LetterSideBarActivity extends AppCompatActivity implements LetterSideBar.onSelectLetterListener {
    private LetterSideBar letterSideBar;
    private TextView show_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_side_bar);
        letterSideBar = findViewById(R.id.letterSideBar);
        show_TV = findViewById(R.id.show_TV);
        letterSideBar.setSelectLetterListener(this);
    }


    public void back(View view) {
        finish();
    }

    @Override
    public void setSelectLetter(String letter, boolean select) {
        if (select) {
            show_TV.setVisibility(View.VISIBLE);
            show_TV.setText(letter);
        } else {
            show_TV.setVisibility(View.GONE);
        }
    }
}
