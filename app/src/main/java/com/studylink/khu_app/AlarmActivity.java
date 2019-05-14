package com.studylink.khu_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlarmActivity extends Fragment {

    private RecyclerView recycler_alarm;
    private ArrayList<RoomUploadDTO> arrayList_alarm = new ArrayList<>();
    private AdapterAlarm alarm_Adapter;
    private DatabaseReference databaseReference;
    private AccountDTO currentUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_alarm, container, false);
        recycler_alarm = view.findViewById(R.id.recycler_alarm);

        arrayList_alarm.clear();

        currentUser = ((Fragement_navi)getActivity()).currentUser;

// "알림창" RecyclerView 구현
        // 레이아웃 종류 정의
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_alarm.setLayoutManager(layoutManager);

        // 어댑터 연결
        alarm_Adapter = new AdapterAlarm(arrayList_alarm, currentUser, new View.OnClickListener() {
            @Override
            // "알림창" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Fragment fragment = new TimelineActivity();
                    Bundle bundle = new Bundle();
                    bundle.putInt("myRoomNum", position);
                    fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.Frame_navi, fragment).commit();
                }
            }
        });
        recycler_alarm.setAdapter(alarm_Adapter);

        // 알람이 있으면
        if(currentUser.getMyAlarm() != null){
            List<RoomUploadDTO> alarmList = currentUser.getMyAlarm();
            for(int i=0; i<alarmList.size(); i++){
                alarm_Adapter.addRoom(alarmList.get(i));
            }
        }

        return view;

    }
}