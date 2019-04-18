package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Etc_schedule extends AppCompatActivity {

    private ImageView to_absent_page;
    private ImageView back_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etc_schedule);

        to_absent_page = findViewById(R.id.to_absent_page);
        to_absent_page.setClickable(true);
        to_absent_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Etc_schedule.this, AbsentActivity.class);
                startActivity(intent);
            }
        });

        back_btn = findViewById(R.id.back_topage);
        back_btn.setClickable(true);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
