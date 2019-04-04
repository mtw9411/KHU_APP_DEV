package com.studylink.khu_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class MypageEditActivity extends AppCompatActivity {

    TextView mypageEdit_editProfile, mypageEdit_editProfileBar, mypageEdit_editDispo, mypageEdit_editDispoBar;
    RelativeLayout mypageEdit_relativeProfile, mypageEdit_relativeDispo;
    private int check = 1;
    private Fragment fragment, fragment2;
    private FragmentManager fm = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(auth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // fragment에 현재 유저 정보 넘기기
                fragment = new FragmentMypageEditProfile();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userData", dataSnapshot.getValue(AccountDTO.class));
                fragment.setArguments(bundle);

                // fragment 초기화
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mypageEdit_fragment, fragment).commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mypageEdit_editProfile = findViewById(R.id.mypageEdit_editProfile);
        mypageEdit_editProfileBar = findViewById(R.id.mypageEdit_editProfileBar);
        mypageEdit_editDispo = findViewById(R.id.mypageEdit_editDispo);
        mypageEdit_editDispoBar = findViewById(R.id.mypageEdit_editDispoBar);
        mypageEdit_relativeProfile = findViewById(R.id.mypageEdit_relativeProfile);
        mypageEdit_relativeDispo = findViewById(R.id.mypageEdit_relativeDispo);

        mypageEdit_relativeProfile.setClickable(true);
        mypageEdit_relativeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(mypageEdit_editProfile,mypageEdit_editProfileBar,mypageEdit_editDispo,mypageEdit_editDispoBar);
                check = 1;
                switchFragment(check);
            }
        });
        mypageEdit_relativeDispo.setClickable(true);
        mypageEdit_relativeDispo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(mypageEdit_editDispo,mypageEdit_editDispoBar,mypageEdit_editProfile,mypageEdit_editProfileBar);
                check = 0;
                switchFragment(check);
            }
        });

    }

    public void colorChange(TextView select_txt, TextView select_mark, TextView non_txt, TextView non_mark){
        select_txt.setTextColor(Color.parseColor("#c895ff"));
        select_mark.setBackgroundResource(R.drawable.pink_bar);
        non_txt.setTextColor(Color.parseColor("#b1b1b1"));
        non_mark.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public void switchFragment(int i){
        if(i == 1){
            fm.beginTransaction().show(fragment).commit();
            fm.beginTransaction().hide(fragment2).commit();
        }
        else{
            if(fragment2 == null){
                fragment2 = new FragmentMypageEditDispo();
                fm.beginTransaction().add(R.id.mypageEdit_fragment, fragment2).commit();
            }

            fm.beginTransaction().hide(fragment).commit();
            fm.beginTransaction().show(fragment2).commit();
        }

    }
}
