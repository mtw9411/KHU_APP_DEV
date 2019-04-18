package com.studylink.khu_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends Fragment {
    private FirebaseAuth auth;                                                                      //아이디, 비번 받아올 것

    private TextView makeStudy, myStudyNum;
    private RelativeLayout addStudy;
    private RecyclerView recyclerView_myStudy,recyclerView_matching, recyclerView_myTown, recyclerView_deadline, recyclerView_newStudy;
    private ArrayList<RoomDTO>arrayList_myStudy = new ArrayList<>();
    private ArrayList<RoomDTO>arrayList_matching = new ArrayList<>();
    private ArrayList<RoomDTO>arrayList_myTown = new ArrayList<>();
    private ArrayList<RoomDTO>arrayList_deadline = new ArrayList<>();
    private ArrayList<RoomDTO>arrayList_newStudy = new ArrayList<>();
    private AdapterMyStudy myStudy_Adapter;
    private AdapterMatching matching_Adapter;
    private AdapterMyTown myTown_Adapter;
    private AdapterDeadline deadline_Adapter;
    private AdapterNewStudy newStudy_Adapter;
    private List<String> roomList = new ArrayList<>();
    private AccountDTO currentUser;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);

        makeStudy = view.findViewById(R.id.makeStudy);
        myStudyNum = view.findViewById(R.id.myStudyNum);
        addStudy = view.findViewById(R.id.addStudy);
        recyclerView_myStudy = view.findViewById(R.id.recyclerView_myStudy);
        recyclerView_matching = view.findViewById(R.id.recyclerView_matching);
        recyclerView_myTown = view.findViewById(R.id.recyclerView_myTown);
        recyclerView_deadline = view.findViewById(R.id.recyclerView_deadline);
        recyclerView_newStudy = view.findViewById(R.id.recyclerView_newStudy);

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        auth = FirebaseAuth.getInstance();

        // array 초기화
        arrayList_myStudy.clear();
        arrayList_matching.clear();
        arrayList_myTown.clear();
        arrayList_deadline.clear();
        arrayList_newStudy.clear();

        // 방 정보
        final DatabaseReference myRef = database.child("room");

        // 현재 유저
        String current_user = auth.getCurrentUser().getUid();
        final DatabaseReference dr = database.child("users").child(current_user);
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(AccountDTO.class);
                if(currentUser.getRoomId() != null){
                    myStudyNum.setText("+" + currentUser.getRoomId().size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 스터디 만들기 버튼
        makeStudy.setClickable(true);
        makeStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeStudyActivity.class);
                startActivity(intent);
            }
        });

        //스터디 검색 버튼
        addStudy.setClickable(true);
        addStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudySearchActivity.class);
                startActivity(intent);
            }
        });

// "내가 참여중인 방" RecyclerView 구현
        // 레이아웃 종류 정의
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView_myStudy.setLayoutManager(layoutManager);

        // 어댑터 연결
        myStudy_Adapter = new AdapterMyStudy(arrayList_myStudy, new View.OnClickListener() {
            @Override
            // "참여중인 스터디방" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Fragment fragment = new TimelineActivity();
                    Bundle bundle = new Bundle();
                    bundle.putInt("myRoomNum", position);
                    fragment.setArguments(bundle);

                    ft.replace(R.id.Frame_navi, fragment).commit();
                }
            }
        });
        recyclerView_myStudy.setAdapter(myStudy_Adapter);

// "스터디 매칭" RecyclerView 구현
        // 레이아웃 종류 정의
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_matching.setLayoutManager(layoutManager2);

        // 어댑터 연결
        matching_Adapter = new AdapterMatching(arrayList_matching, new View.OnClickListener() {
            @Override
            // "자세히 보기" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Intent intent = new Intent(getActivity(), MainDetailActivity.class);
                    intent.putExtra("roomDetail", matching_Adapter.getRoom(position));
                    startActivity(intent);
                }
            }
        }, new View.OnClickListener() {
            @Override
            // "방 입장하기" 클릭 이벤트 - 현재 유저에 클릭한 방 아이디 저장
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    RoomDTO selectRoom = matching_Adapter.getRoom(position);
                    // 참여한 스터디가 1개라도 있으면
                    if (currentUser.getRoomId() != null) {
                        // 가입한 스터디방이 3개이면
                        if (currentUser.getRoomId().size() == 3){
                            Toast.makeText(getContext(), "3개 이상의 스터디를 가입할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            boolean check = true;
                            // 같은 스터디인지 확인
                            for (int i = 0; i < currentUser.getRoomId().size(); i++) {
                                // 중복된 스터디이면
                                if (currentUser.getRoomId().get(i).equals(selectRoom.getId())) {
                                    check = false;
                                    break;
                                }
                            }
                            // 중복된 스터디가 없으면
                            if (check) {
                                // 현재 유저에 방 추가
                                currentUser.getRoomId().add(selectRoom.getId());
                                dr.setValue(currentUser);
                                // 방의 멤버수 증가
                                selectRoom.setMember(selectRoom.getMember() + 1);
                                myRef.child(selectRoom.getId()).setValue(selectRoom);
//                                Intent intent = new Intent(MainActivity.this, TimelineActivity.class);
//                                intent.putExtra("Timeline", selectRoom);                //roomDTO 넘어옴
//                                startActivity(intent);
                                Fragment fragment = new TimelineActivity();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Timeline", selectRoom);
                                fragment.setArguments(bundle);

                                ft.replace(R.id.Frame_navi, fragment).commit();
                            }
                            // 중복된 스터디가 있으면
                            else {
                                Toast.makeText(getContext(), "이미 참여한 스터디입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    // 참여한 스터디가 없으면
                    else {
                        roomList.add(selectRoom.getId());
                        currentUser.setRoomId(roomList);
                        dr.setValue(currentUser);

                        Fragment fragment = new TimelineActivity();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Timeline", selectRoom);
                        fragment.setArguments(bundle);

                        ft.replace(R.id.Frame_navi, fragment).commit();
//                            Intent intent = new Intent(getContext(), TimelineActivity.class);
//                            intent.putExtra("Timeline", selectRoom);
//                            startActivity(intent);
                    }
                }
            }
        });
        recyclerView_matching.setAdapter(matching_Adapter);

// "우리동네 스터디" RecyclerView 구현
        // 레이아웃 종류 정의
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext());
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_myTown.setLayoutManager(layoutManager3);

        // 어댑터 연결
        myTown_Adapter = new AdapterMyTown(arrayList_myTown, new View.OnClickListener() {
            @Override
            // "우리동네 스터디" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;

                    Intent intent = new Intent(getContext(), MainDetailActivity.class);
                    intent.putExtra("roomDetail", myTown_Adapter.getRoom(position));         //누른 스터디방의 이름
                    startActivity(intent);
                }
            }
        });
        recyclerView_myTown.setAdapter(myTown_Adapter);

// "마감 스터디" RecyclerView 구현
        // 레이아웃 종류 정의
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext());
        layoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_deadline.setLayoutManager(layoutManager4);

        // 어댑터 연결
        deadline_Adapter = new AdapterDeadline(arrayList_deadline, new View.OnClickListener() {
            @Override
            // "마감 스터디" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Intent intent = new Intent(getContext(), MainDetailActivity.class);
                    intent.putExtra("roomDetail", deadline_Adapter.getRoom(position));         //누른 스터디방의 이름
                    startActivity(intent);
                }
            }
        });
        recyclerView_deadline.setAdapter(deadline_Adapter);

// "새 스터디" RecyclerView 구현
        // 레이아웃 종류 정의
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getContext());
        layoutManager5.setReverseLayout(true);
        layoutManager5.setStackFromEnd(true);
        recyclerView_newStudy.setLayoutManager(layoutManager5);

        // 어댑터 연결
        newStudy_Adapter = new AdapterNewStudy(arrayList_newStudy, new View.OnClickListener() {
            @Override
            // "새 스터디" 클릭 이벤트
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    int position = (int) obj;
                    Intent intent = new Intent(getContext(), MainDetailActivity.class);
                    intent.putExtra("roomDetail", newStudy_Adapter.getRoom(position));         //누른 스터디방의 이름
                    startActivity(intent);
                }
            }
        });
        recyclerView_newStudy.setAdapter(newStudy_Adapter);


// firebase에 저장된 room 데이터 가져오기
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RoomDTO roomDTO = snapshot.getValue(RoomDTO.class);

                    // 참여한 방이 있으면
                    if (currentUser.getRoomId() != null) {
                        // room의 id가 유저가 참여한 방과 같으면
                        for (int i = 0; i < currentUser.getRoomId().size(); i++) {
                            if (roomDTO.getId().equals(currentUser.getRoomId().get(i))) {
                                myStudy_Adapter.addRoom(roomDTO);
                                break;
                            }
                        }
                    }

                    // 방의 인원수가 남아 있으면
                    if (roomDTO.getMember() < roomDTO.getTotal_member()) {
                        matching_Adapter.addRoom(roomDTO);

                        // 지역이 같은 곳만
                        if (roomDTO.getRegion().equals(currentUser.getUserregion())) {
                            myTown_Adapter.addRoom(roomDTO);
                        }

                        // 인원이 한명 남은 방만
                        if (roomDTO.getTotal_member() - roomDTO.getMember() == 1) {
                            deadline_Adapter.addRoom(roomDTO);
                        }

                        // 오늘 생성된 방만
                        if ((System.currentTimeMillis() - roomDTO.getTime().getTime()) / (24 * 60 * 60 * 1000) == 0) {
                            newStudy_Adapter.addRoom(roomDTO);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }
}

