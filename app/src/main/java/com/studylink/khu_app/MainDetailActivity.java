package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainDetailActivity extends AppCompatActivity {

    TextView Detail_title, Detail_member, Detail_totalMem, Detail_fine, Detail_content, Detail_roomdipo1, Detail_roomdipo2, Detail_roomdipo3, Detail_roomdipo4;
    RelativeLayout Detail_entrance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        RoomDTO roomDTO = (RoomDTO)bundle.get("roomDetail");

        Detail_title = findViewById(R.id.Detail_title);
        Detail_member = findViewById(R.id.Detail_member);
        Detail_totalMem = findViewById(R.id.Detail_totalMem);
        Detail_entrance = findViewById(R.id.Detail_entrance);
        Detail_fine = findViewById(R.id.Detail_fine);
        Detail_content = findViewById(R.id.Detail_content);
        Detail_roomdipo1 = findViewById(R.id.Detail_roomdipo1);
        Detail_roomdipo2 = findViewById(R.id.Detail_roomdipo2);
        Detail_roomdipo3 = findViewById(R.id.Detail_roomdipo3);
        Detail_roomdipo4 = findViewById(R.id.Detail_roomdipo4);

        Detail_title.setText(roomDTO.getRoomName());
        Detail_member.setText(String.valueOf(roomDTO.getMember()));
        Detail_totalMem.setText("/" + roomDTO.getTotal_member().toString());
        Detail_fine.setText(roomDTO.getFine().toString());
        Detail_content.setText(roomDTO.getContent());
        Detail_roomdipo1.setText(roomDTO.getRoomdisposition().get(0));
        Detail_roomdipo2.setText(roomDTO.getRoomdisposition().get(1));
        Detail_roomdipo3.setText(roomDTO.getRoomdisposition().get(2));
        Detail_roomdipo4.setText(roomDTO.getRoomdisposition().get(3));

        Detail_entrance.setClickable(true);
        Detail_entrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDetailActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });
    }
}
