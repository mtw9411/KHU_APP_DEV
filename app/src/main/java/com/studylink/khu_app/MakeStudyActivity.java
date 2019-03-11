package com.studylink.khu_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MakeStudyActivity extends AppCompatActivity {

    TextView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makestudy);

        btn1 = findViewById(R.id.textView43);
        btn2 = findViewById(R.id.textView49);
        btn3 = findViewById(R.id.textView53);
        btn4 = findViewById(R.id.textView54);
        btn5 = findViewById(R.id.textView55);
        btn6 = findViewById(R.id.textView56);
        btn7 = findViewById(R.id.textView57);
        btn8 = findViewById(R.id.textView58);
        next = findViewById(R.id.textView61);

        btn1.setClickable(true);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn1, btn2);
            }
        });
        btn2.setClickable(true);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn2, btn1);
            }
        });
        btn3.setClickable(true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn3, btn4);
            }
        });
        btn4.setClickable(true);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn4, btn3);
            }
        });
        btn5.setClickable(true);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn5, btn6);
            }
        });
        btn6.setClickable(true);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn6, btn5);
            }
        });
        btn7.setClickable(true);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn7, btn8);
            }
        });
        btn8.setClickable(true);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn8, btn7);
            }
        });

        next.setClickable(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakeStudyActivity.this, MakeStudyFinActivity.class));
            }
        });
    }

    public void colorChange(TextView select, TextView non){
        select.setBackground(getResources().getDrawable(R.drawable.red_border));
        non.setBackground(getResources().getDrawable(R.drawable.gray_border));
        select.setTextColor(Color.parseColor("#2f78db"));
        non.setTextColor(Color.parseColor("#707070"));
    }
}
