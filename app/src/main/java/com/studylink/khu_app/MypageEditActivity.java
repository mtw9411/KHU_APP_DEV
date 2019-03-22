package com.studylink.khu_app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MypageEditActivity extends AppCompatActivity {

    TextView textView78, textView79, textView80, textView81;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit);

        textView78 = findViewById(R.id.textView78);
        textView79 = findViewById(R.id.textView79);
        textView80 = findViewById(R.id.textView80);
        textView81 = findViewById(R.id.textView81);

        textView78.setClickable(true);
        textView78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(textView78,textView80,textView79,textView81);
            }
        });
        textView79.setClickable(true);
        textView79.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(textView79,textView81,textView78,textView80);
            }
        });

    }

    public void colorChange(TextView select_txt, TextView select_mark, TextView non_txt, TextView non_mark){
        select_txt.setTextColor(Color.parseColor("#c895ff"));
        select_mark.setBackgroundColor(Color.parseColor("#c895ff"));
        non_txt.setTextColor(Color.parseColor("#b1b1b1"));
        non_mark.setBackgroundColor(Color.parseColor("#ffffff"));
    }
}
