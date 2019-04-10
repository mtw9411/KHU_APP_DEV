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

public class AlarmActivity extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_alarm, container, false);

//        // "내가 참여중인 방" RecyclerView 구현
//        // 레이아웃 종류 정의
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView_myStudy.setLayoutManager(layoutManager);
//
//        // 어댑터 연결
//        myStudy_Adapter = new AdapterMyStudy(arrayList_myStudy, new View.OnClickListener() {
//            @Override
//            // "참여중인 스터디방" 클릭 이벤트
//            public void onClick(View v) {
//                Object obj = v.getTag();
//                if (obj != null) {
//                    int position = (int) obj;
//                    Fragment fragment = new TimelineActivity();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("Timeline", myStudy_Adapter.getRoom(position));
//                    fragment.setArguments(bundle);
//
//                    ft.replace(R.id.Frame_navi, fragment).commit();
//                }
//            }
//        });
//        recyclerView_myStudy.setAdapter(myStudy_Adapter);


        return view;

    }
}