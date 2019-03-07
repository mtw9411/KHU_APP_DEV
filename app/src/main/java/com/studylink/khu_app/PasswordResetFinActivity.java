package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class PasswordResetFinActivity extends AppCompatActivity {

    TextView goto_login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        goto_login_btn = findViewById(R.id.goto_login_btn);
        goto_login_btn.setClickable(true);
        goto_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordResetFinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
