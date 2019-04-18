package com.studylink.khu_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AbsentActivity extends AppCompatActivity {

    private TextView insign_absent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etc_absent);

        insign_absent = findViewById(R.id.insign_absent);
        insign_absent.setClickable(true);
        insign_absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbsentActivity.this, absent_registrate.class);
                startActivity(intent);
            }
        });

    }
}
