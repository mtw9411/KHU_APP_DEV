package com.studylink.khu_app;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.ID;

public class AccountSetActivity extends AppCompatActivity {
    private FirebaseDatabase FirebaseDatabase;
    private FirebaseAuth auth;
    private FirebaseUser mUser;
    private TextView account_set_next;
    private EditText account_username;
    private EditText account_userbirth;
    private TextView account_userregion;
    private String sex_check = "false";


    TextView gender_male, gender_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_set);
        auth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase = FirebaseDatabase.getInstance();
        account_username = (EditText) findViewById(R.id.account_username);
        account_userbirth = (EditText) findViewById(R.id.account_userbirth);
        account_userregion = (TextView) findViewById(R.id.account_userregion);

        gender_male = findViewById(R.id.gender_male);
        gender_female = findViewById(R.id.gender_female);
        account_set_next = findViewById(R.id.account_set_next);

        gender_male.setClickable(true);
        gender_female.setClickable(true);
        account_set_next.setClickable(true);

        gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(gender_male, gender_female);
                sex_check = "male";
            }
        });
        gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(gender_female, gender_male);
                sex_check = "female";
            }
        });

        account_set_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAccountset();
            }
        });
    }

    @SuppressLint("NewApi")
    public void colorChange(TextView change_blue, TextView change_gray){
        change_blue.setBackground(getResources().getDrawable(R.drawable.blue_border_register, null));
        change_gray.setBackground(getResources().getDrawable(R.drawable.gray_border_register, null));
        change_blue.setTextColor(Color.parseColor("#2f78db"));
        change_gray.setTextColor(Color.parseColor("#e4e4e4"));
    }

    public void databaseAccountset(){                                                               //데이터를 파이어베이스에 올림
        AccountDTO Accountset = new AccountDTO();

        String name = account_username.getText().toString();
        String birth = account_userbirth.getText().toString();
        String region = account_userregion.getText().toString();

//        Accountset.registcheck = "true";
        if(name.trim().length()>0){
            if (birth.trim().length()>0){
                if (!sex_check.equals("false")){
                    Accountset.setUsername(name);
                    Accountset.setUserbirth(birth);
                    Accountset.setUsersex(sex_check);
                    if (region.trim().length()>0){
                        Accountset.setUserregion(region);
                    }
                    else{
                        Accountset.setUserregion("지역 상관없음");
                    }
                    String uid = auth.getCurrentUser().getUid();
                    FirebaseDatabase.getReference().child("users").child(uid).setValue(Accountset);
                    Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent (AccountSetActivity.this, AccountFinActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "성별을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "생년월일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }



    /*public void alreadysign() {
        FirebaseDatabase.getReference().child("users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccountDTO accountDTOs = dataSnapshot.getValue(AccountDTO.class);
                if(accountDTOs.registcheck.equals("true")){
                    Intent intent = new Intent(AccountSetActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

//        FirebaseDatabase.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    AccountDTO accountDTO = dataSnapshot1.getValue(AccountDTO.class);
//                    String a = accountDTO.registcheck;
//                    if (a.equals("true")) {
//                        Intent intent = new Intent(AccountSetActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                    Toast.makeText(AccountSetActivity.this, a, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
}


    /*public void postFirebaseDatabase(boolean add){
        FirebaseDatabase = FirebaseDatabase.getInstance();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> accountValues = null;
        if(add){
            AccountDTO accountDTO = new AccountDTO(account_username, account_userbirth, sex_check, );
            accountValues = accountDTO.toMap();
        }
        childUpdates.put("/users/" + auth.getCurrentUser().getUid(), accountValues);
        FirebaseDatabase.getReference().updateChildren(childUpdates);
    }*/
