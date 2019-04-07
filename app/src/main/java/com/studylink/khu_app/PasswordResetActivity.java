package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class PasswordResetActivity extends AppCompatActivity {

    TextView reset_password_finish_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        reset_password_finish_btn = findViewById(R.id.reset_password_finish_btn);

        reset_password_finish_btn.setClickable(true);
        reset_password_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordResetActivity.this, PasswordResetFinActivity.class);
                startActivity(intent);
            }
        });

    }
}
