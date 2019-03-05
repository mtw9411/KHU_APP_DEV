package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignUpFinalActivity extends AppCompatActivity {

    TextView confirm_btn, sign_up_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_final);

        confirm_btn = findViewById(R.id.confirm_btn);
        sign_up_finish = findViewById(R.id.sign_up_finish);

        confirm_btn.setClickable(true);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_btn.setText("재전송");
            }
        });

        sign_up_finish.setClickable(true);
        sign_up_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpFinalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
