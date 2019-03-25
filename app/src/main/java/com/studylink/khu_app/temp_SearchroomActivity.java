package com.studylink.khu_app;

import android.content.Context;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studylink.khu_app.R;

import java.util.ArrayList;
import java.util.List;

public class temp_SearchroomActivity extends AppCompatActivity {

    DatabaseReference databaseRoom;
    ListView listViewRoom;
    List<RoomDTO> roomList;
    private List<String> roomdispo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_searchroom);

        listViewRoom=(ListView)findViewById(R.id.listViewRoom);
        roomList=new ArrayList<>();
        databaseRoom= FirebaseDatabase.getInstance().getReference("room");






    }



    @Override
    protected void onStart() {
        super.onStart();


        databaseRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

             roomList.clear();

                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    RoomDTO roomDTO1 = roomSnapshot.getValue(RoomDTO.class);


                    roomList.add(roomDTO1); //roomList 에는 모든 방이 들어 가 있다

                }

                RoomList adapter = new RoomList(temp_SearchroomActivity.this,roomList); //adapter에 방 리스트를 붙임
                listViewRoom.setAdapter(adapter); //어댑터의 방 리스트를 listVeiw로 붙임

                listViewRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        roomdispo=new ArrayList<>();
                      roomdispo= roomList.get(position).getRoomdisposition();
                      String str1[]={roomdispo.get(0),roomdispo.get(1),roomdispo.get(2),roomdispo.get(3)};

                      Intent intent = new Intent(getApplicationContext(), StudydetailActivity.class);
                        intent.putExtra("member",roomList.get(position).getTotal_member());
                        intent.putExtra("fine",roomList.get(position).getFine());
                        intent.putExtra("dispor",str1);
                        intent.putExtra("spinner2",roomList.get(position).getSpinner2());
                        intent.putExtra("content",roomList.get(position).getContent());
                        intent.putExtra("id",roomList.get(position).getId());

                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }
}




