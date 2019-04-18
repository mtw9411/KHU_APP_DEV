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

public class AlarmActivity extends Fragment {

    private RecyclerView recycler_alarm;
    private ArrayList<RoomUploadDTO> arrayList_alarm = new ArrayList<>();
    private AdapterAlarm alarm_Adapter;
    private DatabaseReference databaseReference;
    private AccountDTO currentUser;
    private RoomUploadDTO roomUploadDTO;
    private String RoomId;

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

        // 현재 유저
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(AccountDTO.class);
                if(currentUser.getRoomId()!=null){
                    for(int i=0; i<currentUser.getRoomId().size(); i++){
                        // 유저가 참여한 방 id - 수정해야함###########################################################################3
                        RoomId = currentUser.getRoomId().get(i);
                        setData();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

// "알림창" RecyclerView 구현
        // 레이아웃 종류 정의
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_alarm.setLayoutManager(layoutManager);

        // 어댑터 연결
        alarm_Adapter = new AdapterAlarm(arrayList_alarm, new View.OnClickListener() {
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

        return view;

    }

    public void setData(){
        // 현재 유저 이름
        alarm_Adapter.setUser(currentUser.getUsername());
        // 내가 참여한 스터디방과 같은 방
        databaseReference.child("RoomUpload").child(RoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 글 하나하나
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    roomUploadDTO = snapshot.getValue(RoomUploadDTO.class);
                    alarm_Adapter.addRoom(roomUploadDTO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}