package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class StudySearchActivity extends AppCompatActivity {

    ImageView search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etc_studysearch);

        search_btn = findViewById(R.id.imageView21);

        search_btn.setClickable(true);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudySearchActivity.this, StudySearchingActivity.class);
                startActivity(intent);
            }
        });
    }
}
