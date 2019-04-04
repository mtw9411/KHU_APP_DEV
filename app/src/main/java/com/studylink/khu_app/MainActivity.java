package com.studylink.khu_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;                                                                      //아이디, 비번 받아올 것

    private ImageView toMypage;
    private TextView makeStudy, dateNow, myStudyNum;
    private RelativeLayout addStudy;
    private RecyclerView recyclerView_myStudy,recyclerView_matching;
    private ArrayList<RoomDTO>arrayList_myStudy = new ArrayList<>();
    private ArrayList<RoomDTO>arrayList_matching = new ArrayList<>();
    private AdapterMyStudy myStudy_Adapter;
    private AdapterMatching matching_Adapter;
    private int myStudyroomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        auth = FirebaseAuth.getInstance();
        toMypage = findViewById(R.id.toMypage);
        dateNow = findViewById(R.id.dateNow);
        makeStudy = findViewById(R.id.makeStudy);
        myStudyNum = findViewById(R.id.myStudyNum);
        addStudy = findViewById(R.id.addStudy);
        recyclerView_myStudy = findViewById(R.id.recyclerView_myStudy);
        recyclerView_matching = findViewById(R.id.recyclerView_matching);


        toMypage.setClickable(true);
        toMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Mypage_main.class);
                startActivity(intent);
            }
        });

        // 오늘 날짜 가져오기
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 d일");
        String getTime = sdf.format(date);
        dateNow.setText(getTime);


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


// "내가 참여중인 방" RecyclerView 구현
        // 레이아웃 종류 정의
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_myStudy.setLayoutManager(layoutManager);

        // 어댑터 연결
        myStudy_Adapter = new AdapterMyStudy(arrayList_myStudy, new View.OnClickListener() {
            @Override
            // "참여중인 스터디방" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Intent intent = new Intent(MainActivity.this, TimelineActivity.class);
                    intent.putExtra("Timeline", myStudy_Adapter.getRoom(position));
                    startActivity(intent);
                }
            }
        });
        recyclerView_myStudy.setAdapter(myStudy_Adapter);

// "스터디 매칭" RecyclerView 구현
        // 레이아웃 종류 정의
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager2).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_matching.setLayoutManager(layoutManager2);

        // 어댑터 연결
        matching_Adapter = new AdapterMatching(arrayList_matching, new View.OnClickListener() {
            @Override
            // "자세히 보기" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Intent intent = new Intent(MainActivity.this, MainDetailActivity.class);
                    intent.putExtra("roomDetail", matching_Adapter.getRoom(position));
                    startActivity(intent);
                }
            }
        }, new View.OnClickListener() {
            @Override
            // "방 입장하기" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Intent intent = new Intent(MainActivity.this, TimelineActivity.class);
                    intent.putExtra("Timeline", matching_Adapter.getRoom(position));
                    startActivity(intent);
                }
            }
        });
        recyclerView_matching.setAdapter(matching_Adapter);


// firebase에 저장된 데이터 가져오기
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("room");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RoomDTO roomDTO = snapshot.getValue(RoomDTO.class);
                    myStudy_Adapter.addRoom(roomDTO);
                    matching_Adapter.addRoom(roomDTO);
                    Log.d("###############first", Integer.toString(myStudy_Adapter.getItemCount()));
                }
                myStudyNum.setText("+" + myStudy_Adapter.getItemCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}

