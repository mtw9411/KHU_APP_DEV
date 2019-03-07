package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class PasswordSearchActivity extends AppCompatActivity {

    TextView search_password_finish_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_search);

        search_password_finish_btn = findViewById(R.id.search_password_finish_btn);

        search_password_finish_btn.setClickable(true);
        search_password_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordSearchActivity.this, PasswordFinActivity.class);
                startActivity(intent);
            }
        });

    }
}
