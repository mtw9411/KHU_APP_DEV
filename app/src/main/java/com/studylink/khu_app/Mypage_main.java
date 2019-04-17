package com.studylink.khu_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class Mypage_main extends Fragment{

    private ImageView mypage_schedule;
    private ImageView mypage_store;
    private TextView mypage_Logout;
    private FirebaseAuth auth;
    private Mypage_schedule_fragment scheduleFragment;
    private Mypage_store_fragment storeFragment;
    private android.support.v4.app.FragmentManager fm;
    private FragmentTransaction ft;
    private ImageView back_btn;

    private ImageView mypage_editProfile;
    private ViewGroup mypage_schedulelayout;
    private ViewGroup mypage_storelayout;
    private TextView mypage_myName;
    private int layoutIndex = 0;
    private DatabaseReference dr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_mypage_main, container, false);
        auth = FirebaseAuth.getInstance();
        fm = getFragmentManager();
        ft = fm.beginTransaction();


        mypage_Logout = (TextView) view.findViewById(R.id.mypage_Logout);
        mypage_schedule = (ImageView) view.findViewById(R.id.mypage_schedule);
        mypage_store = (ImageView) view.findViewById(R.id.mypage_store);
        back_btn = (ImageView) view.findViewById(R.id.back_btn);

        scheduleFragment = new Mypage_schedule_fragment();
        storeFragment = new Mypage_store_fragment();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

        mypage_Logout = view.findViewById(R.id.mypage_Logout);
        mypage_editProfile = view.findViewById(R.id.mypage_editProfile);
        mypage_myName = view.findViewById(R.id.mypage_myName);
        mypage_schedule = view.findViewById(R.id.mypage_schedule);
        mypage_store = view.findViewById(R.id.mypage_store);
        mypage_schedulelayout = view.findViewById(R.id.mypage_schedulelayout);
//        mypage_storelayout = view.findViewById(R.id.mypage_storelayout);

        mypage_editProfile.setClickable(true);
        mypage_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageEditActivity.class);
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
                setFrag(0);
                mypage_schedule.setBackground(getResources().getDrawable(R.drawable.mypage_icon_after));
                mypage_store.setBackground(getResources().getDrawable(R.drawable.mypage_icon_before));
            }
        });

        mypage_store.setClickable(true);
        mypage_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(1);
                mypage_schedule.setBackground(getResources().getDrawable(R.drawable.mypage_icon_before));
                mypage_store.setBackground(getResources().getDrawable(R.drawable.mypage_icon_after));
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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        setFrag(0);
        return view;
    }

    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right, R.animator.enter_from_right, R.animator.exit_to_left)
                        .replace(R.id.main_frame, scheduleFragment).commit();
                break;

            case 1:
                ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right)
                        .replace(R.id.main_frame, storeFragment).commit();
                break;
        }
    }

    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("로그아웃");
        builder.setMessage("로그아웃 하시겠습니까?");
        builder.setIcon(android.R.mipmap.sym_def_app_icon);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
