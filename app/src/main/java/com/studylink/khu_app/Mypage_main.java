package com.studylink.khu_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mypage_main extends AppCompatActivity{

    private ImageView mypage_schedule, mypage_store, mypage_editProfile;
    private ViewGroup mypage_schedulelayout;
    private ViewGroup mypage_storelayout;
    private TextView mypage_Logout, mypage_myName;
    private FirebaseAuth auth;
    private int layoutIndex = 0;
    private DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_main);
        auth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

        mypage_Logout = findViewById(R.id.mypage_Logout);
        mypage_editProfile = findViewById(R.id.mypage_editProfile);
        mypage_myName = findViewById(R.id.mypage_myName);
        mypage_schedule = findViewById(R.id.mypage_schedule);
        mypage_store = findViewById(R.id.mypage_store);
        mypage_schedulelayout = findViewById(R.id.mypage_schedulelayout);
        mypage_storelayout = findViewById(R.id.mypage_storelayout);

        mypage_editProfile.setClickable(true);
        mypage_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage_main.this, MypageEditActivity.class);
                startActivity(intent);
            }
        });

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccountDTO me = dataSnapshot.getValue(AccountDTO.class);
                mypage_myName.setText(me.getUsername()+"님,");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mypage_schedule.setClickable(true);
        mypage_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewChange1();
            }
        });

        mypage_store.setClickable(true);
        mypage_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewChange2();
            }
        });

        mypage_Logout.setClickable(true);
        mypage_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });
    }

    private void viewChange1(){
        if(layoutIndex == 1){
            mypage_schedulelayout.setVisibility(View.VISIBLE);
            mypage_storelayout.setVisibility(View.INVISIBLE);

            layoutIndex = 0;
        }
    }
    private void viewChange2(){
        if(layoutIndex == 0){
            mypage_schedulelayout.setVisibility(View.INVISIBLE);
            mypage_storelayout.setVisibility(View.VISIBLE);

            layoutIndex = 1;
        }
    }

    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("로그아웃");
        builder.setMessage("로그아웃 하시겠습니까?");
        builder.setIcon(android.R.mipmap.sym_def_app_icon);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                Intent intent = new Intent(Mypage_main.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
