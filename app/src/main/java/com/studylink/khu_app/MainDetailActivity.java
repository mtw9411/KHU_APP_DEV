package com.studylink.khu_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainDetailActivity extends AppCompatActivity {

    private TextView Detail_title, Detail_member, Detail_totalMem, Detail_fine, Detail_content1, Detail_content2,
                    Detail_roomdipo1, Detail_roomdipo2, Detail_roomdipo3, Detail_roomdipo4,
                    Detail_region, Detail_age, Detail_gender;
    private RelativeLayout Detail_entrance;
    private List<String> roomList = new ArrayList<>();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final RoomDTO roomDTO = (RoomDTO)bundle.get("roomDetail");

        Detail_title = findViewById(R.id.Detail_title);
        Detail_member = findViewById(R.id.Detail_member);
        Detail_totalMem = findViewById(R.id.Detail_totalMem);
        Detail_region = findViewById(R.id.Detail_region);
        Detail_age = findViewById(R.id.Detail_age);
        Detail_gender = findViewById(R.id.Detail_gender);
        Detail_entrance = findViewById(R.id.Detail_entrance);
        Detail_fine = findViewById(R.id.Detail_fine);
        Detail_content1 = findViewById(R.id.Detail_content1);
        Detail_content2 = findViewById(R.id.Detail_content2);
        Detail_roomdipo1 = findViewById(R.id.Detail_roomdipo1);
        Detail_roomdipo2 = findViewById(R.id.Detail_roomdipo2);
        Detail_roomdipo3 = findViewById(R.id.Detail_roomdipo3);
        Detail_roomdipo4 = findViewById(R.id.Detail_roomdipo4);

        Detail_title.setText(roomDTO.getRoomName());
        Detail_member.setText(String.valueOf(roomDTO.getMember()));
        Detail_totalMem.setText("/" + roomDTO.getTotal_member().toString());
        Detail_region.setText(roomDTO.getRegion());
        Detail_age.setText(roomDTO.getAge());
        Detail_gender.setText(roomDTO.getGender());
        Detail_fine.setText(roomDTO.getFine().toString());
        Detail_content1.setText(roomDTO.getContent1());
        Detail_content2.setText(roomDTO.getContent2());
        Detail_roomdipo1.setText(roomDTO.getRoomdisposition().get(0));
        Detail_roomdipo2.setText(roomDTO.getRoomdisposition().get(1));
        Detail_roomdipo3.setText(roomDTO.getRoomdisposition().get(2));
        Detail_roomdipo4.setText(roomDTO.getRoomdisposition().get(3));

        String current_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference dr = database.child("users").child(current_user);

        Detail_entrance.setClickable(true);
        Detail_entrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AccountDTO currentUser = dataSnapshot.getValue(AccountDTO.class);
                        // 참여한 스터디가 1개라도 있으면
                        if(currentUser.getRoomId() != null){
                            Log.d("##############3", "???");
                            // 가입한 스터디방이 3개이면
                            if (currentUser.getRoomId().size() == 3){
                                Toast.makeText(MainDetailActivity.this, "3개 이상의 스터디를 가입할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                boolean check = true;
                                // 같은 스터디인지 확인
                                for(int i = 0; i<currentUser.getRoomId().size(); i++){
                                    // 중복된 스터디이면
                                    if (currentUser.getRoomId().get(i).equals(roomDTO.getId())){
                                        check = false;
                                        break;
                                    }
                                }
                                // 중복된 스터디가 없으면
                                if(check){
                                    currentUser.getRoomId().add(roomDTO.getId());
                                    dr.setValue(currentUser);
                                    roomDTO.setMember(roomDTO.getMember()+1);
                                    database.child("room").child(roomDTO.getId()).setValue(roomDTO);

                                    Intent intent = new Intent(MainDetailActivity.this, Fragement_navi.class);
                                    intent.putExtra("frag_num", 1);
                                    intent.putExtra("myRoomNum", currentUser.getRoomId().size()-1);
                                    startActivity(intent);
                                }
                                // 중복된 스터디가 있으면
                                else{
                                    Toast.makeText(MainDetailActivity.this, "이미 참여한 스터디입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        // 참여한 스터디가 없으면
                        else{
                            roomList.add(roomDTO.getId());
                            currentUser.setRoomId(roomList);
                            dr.setValue(currentUser);
                            roomDTO.setMember(roomDTO.getMember()+1);
                            database.child("room").child(roomDTO.getId()).setValue(roomDTO);

                            Intent intent = new Intent(MainDetailActivity.this, Fragement_navi.class);
                            intent.putExtra("frag_num", 1);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}
