package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainDetailActivity extends AppCompatActivity {

    TextView Detail_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        RoomDTO roomDTO = (RoomDTO)bundle.get("roomDetail");

        Detail_title = findViewById(R.id.Detail_title);

        Detail_title.setText(roomDTO.getRoomName());
    }
}
