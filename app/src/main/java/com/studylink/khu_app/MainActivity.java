package com.studylink.khu_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;                                                                      //아이디, 비번 받아올 것

    private ImageView toMypage;
    private TextView makeStudy;
    private RelativeLayout addStudy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        auth = FirebaseAuth.getInstance();
        toMypage = (ImageView) findViewById(R.id.toMypage);
        makeStudy = findViewById(R.id.makeStudy);
        addStudy = findViewById(R.id.addStudy);

        toMypage.setClickable(true);
        toMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Mypage_main.class);
                startActivity(intent);
            }
        });

        // 스터디 만들기 버튼
        makeStudy.setClickable(true);
        makeStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MakeStudyActivity.class);
                startActivity(intent);
            }
        });

        //스터디 검색 버튼
        addStudy.setClickable(true);
        addStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudySearchActivity.class);
                startActivity(intent);
            }
        });

    }




}
