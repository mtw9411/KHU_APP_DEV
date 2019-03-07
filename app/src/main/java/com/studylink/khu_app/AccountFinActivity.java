package com.studylink.khu_app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AccountFinActivity extends AppCompatActivity {

    LinearLayout btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView btn1_txt;
    ImageView btn1_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_fin);

        

    }

    public void selectChange(TextView change_blue, TextView change_gray, ImageView selected_image, ImageView non_image){
        selected_image.setImageResource(R.mipmap.checked_btn);
        non_image.setImageResource(R.mipmap.check_btn);
        change_blue.setTextColor(Color.parseColor("#3664cd"));
        change_gray.setTextColor(Color.parseColor("#66222222"));
    }

}
