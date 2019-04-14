package com.studylink.khu_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AlarmActivity extends Fragment {

    private RecyclerView recycler_alarm;
    private ArrayList<RoomUploadDTO> arrayList_alarm = new ArrayList<>();
    private AdapterAlarm alarm_Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_alarm, container, false);

// "알림창" RecyclerView 구현
        // 레이아웃 종류 정의
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_alarm.setLayoutManager(layoutManager);

        // 어댑터 연결
        alarm_Adapter = new AdapterAlarm(arrayList_alarm, new View.OnClickListener() {
            @Override
            // "알림창" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
//                    int position = (int) obj;
//                    Intent intent = new Intent(getContext(), TimelineActivity.class);
//                    intent.putExtra("Timeline", )

                }
            }
        });
        recycler_alarm.setAdapter(alarm_Adapter);


        return view;

    }
}