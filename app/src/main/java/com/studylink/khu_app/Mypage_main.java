package com.studylink.khu_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

    private ImageView mypage_schedule;
    private ImageView mypage_store;
    private TextView mypage_Logout;
    private FirebaseAuth auth;
    private Mypage_schedule_fragment scheduleFragment;
    private Mypage_store_fragment storeFragment;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ImageView back_btn;

    private ImageView mypage_editProfile;
    private ViewGroup mypage_schedulelayout;
    private ViewGroup mypage_storelayout;
    private TextView mypage_myName;
    private int layoutIndex = 0;
    private DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_main);
        auth = FirebaseAuth.getInstance();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();


        mypage_Logout = (TextView) findViewById(R.id.mypage_Logout);
        mypage_schedule = (ImageView) findViewById(R.id.mypage_schedule);
        mypage_store = (ImageView) findViewById(R.id.mypage_store);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        scheduleFragment = new Mypage_schedule_fragment();
        storeFragment = new Mypage_store_fragment();
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
                onFragmentChanged(0);
            }
        });

        mypage_store.setClickable(true);
        mypage_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentChanged(1);
            }
        });

        mypage_Logout.setClickable(true);
        mypage_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });

        back_btn.setClickable(true);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage_main.this, MainActivity.class);
                startActivity(intent);
            }
        });
        onFragmentChanged(0);

        final BottomNavigationView bottomNavigationview = (BottomNavigationView) findViewById(R.id.bottomNavigationView_mys);
        bottomNavigationview.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                        switch (item.getItemId()) {

                            case R.id.menuitem_bottombar_home:
                                Intent intent1 = new Intent(Mypage_main.this, MainActivity.class);
                                startActivity(intent1);

                                return true;

                            case R.id.menuitem_bottombar_session:
                                Intent intent2 = new Intent(Mypage_main.this, TimelineActivity.class);
                                startActivity(intent2);

                                return true;

                            case R.id.menuitem_bottombar_alarm:
                                Intent intent3 = new Intent(Mypage_main.this, AlarmActivity.class);
                                startActivity(intent3);

                                return true;

                            case R.id.menuitem_bottombar_mys:

                                return true;
                        }
                        return false;
                    }
                });
    }

    public void onFragmentChanged(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, scheduleFragment).commit();
        } else if (index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, storeFragment).commit();
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
