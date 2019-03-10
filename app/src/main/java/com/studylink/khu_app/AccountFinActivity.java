package com.studylink.khu_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountFinActivity extends AppCompatActivity {

    LinearLayout btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView btn1_txt, btn2_txt, btn3_txt, btn4_txt, btn5_txt, btn6_txt, btn7_txt, btn8_txt, main_btn;
    ImageView btn1_img, btn2_img, btn3_img, btn4_img, btn5_img, btn6_img, btn7_img, btn8_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_fin);

        btn1 = findViewById(R.id.btn1);
        btn1_txt = findViewById(R.id.btn1_txt);
        btn1_img = findViewById(R.id.btn1_img);
        btn2 = findViewById(R.id.btn2);
        btn2_txt = findViewById(R.id.btn2_txt);
        btn2_img = findViewById(R.id.btn2_img);
        btn3 = findViewById(R.id.btn3);
        btn3_txt = findViewById(R.id.btn3_txt);
        btn3_img = findViewById(R.id.btn3_img);
        btn4 = findViewById(R.id.btn4);
        btn4_txt = findViewById(R.id.btn4_txt);
        btn4_img = findViewById(R.id.btn4_img);
        btn5 = findViewById(R.id.btn5);
        btn5_txt = findViewById(R.id.btn5_txt);
        btn5_img = findViewById(R.id.btn5_img);
        btn6 = findViewById(R.id.btn6);
        btn6_txt = findViewById(R.id.btn6_txt);
        btn6_img = findViewById(R.id.btn6_img);
        btn7 = findViewById(R.id.btn7);
        btn7_txt = findViewById(R.id.btn7_txt);
        btn7_img = findViewById(R.id.btn7_img);
        btn8 = findViewById(R.id.btn8);
        btn8_txt = findViewById(R.id.btn8_txt);
        btn8_img = findViewById(R.id.btn8_img);

        main_btn = findViewById(R.id.main_btn);

        btn1.setClickable(true);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn1_txt, btn2_txt, btn1_img, btn2_img);
            }
        });
        btn2.setClickable(true);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn2_txt, btn1_txt, btn2_img, btn1_img);
            }
        });
        btn3.setClickable(true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn3_txt, btn4_txt, btn3_img, btn4_img);
            }
        });
        btn4.setClickable(true);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn4_txt, btn3_txt, btn4_img, btn3_img);
            }
        });
        btn5.setClickable(true);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn5_txt, btn6_txt, btn5_img, btn6_img);
            }
        });
        btn6.setClickable(true);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn6_txt, btn5_txt, btn6_img, btn5_img);
            }
        });
        btn7.setClickable(true);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn7_txt, btn8_txt, btn7_img, btn8_img);
            }
        });
        btn8.setClickable(true);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn8_txt, btn7_txt, btn8_img, btn7_img);
            }
        });

        main_btn.setClickable(true);
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountFinActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void selectChange(TextView change_blue, TextView change_gray, ImageView selected_image, ImageView non_image){
        selected_image.setImageResource(R.mipmap.checked_btn);
        non_image.setImageResource(R.mipmap.check_btn);
        change_blue.setTextColor(Color.parseColor("#3664cd"));
        change_gray.setTextColor(Color.parseColor("#66222222"));
    }

}
