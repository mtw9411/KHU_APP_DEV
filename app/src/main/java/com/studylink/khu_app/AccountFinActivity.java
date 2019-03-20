package com.studylink.khu_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountFinActivity extends AppCompatActivity {
    private FirebaseDatabase FirebaseDatabase;
    private FirebaseAuth auth;


    LinearLayout btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView btn1_txt, btn2_txt, btn3_txt, btn4_txt, btn5_txt, btn6_txt, btn7_txt, btn8_txt, main_btn;
    ImageView btn1_img, btn2_img, btn3_img, btn4_img, btn5_img, btn6_img, btn7_img, btn8_img;



    private String login_check = "false";
    private List<String> dispoList = new ArrayList<>();
    private boolean check1 = false;
    private boolean check2 = false;
    private boolean check3 = false;
    private boolean check4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_fin);
        FirebaseDatabase = FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();

        dispoList.add("a");
        dispoList.add("b");
        dispoList.add("c");
        dispoList.add("d");

        btn1 = findViewById(R.id.btn1);
        btn1_txt = findViewById(R.id.btn1_txt);
        btn1_img = findViewById(R.id.btn1_img);
        btn2 = findViewById(R.id.btn2);
        btn2_txt = findViewById(R.id.btn2_txt);
        btn2_img = findViewById(R.id.btn2_img);
        btn3 = findViewById(R.id.btn3);
        btn3_txt = findViewById(R.id.btn3_txt);
        btn3_img = findViewById(R.id.btn3_img);
        btn4 = findViewById(R.id.btn4);
        btn4_txt = findViewById(R.id.btn4_txt);
        btn4_img = findViewById(R.id.btn4_img);
        btn5 = findViewById(R.id.btn5);
        btn5_txt = findViewById(R.id.btn5_txt);
        btn5_img = findViewById(R.id.btn5_img);
        btn6 = findViewById(R.id.btn6);
        btn6_txt = findViewById(R.id.btn6_txt);
        btn6_img = findViewById(R.id.btn6_img);
        btn7 = findViewById(R.id.btn7);
        btn7_txt = findViewById(R.id.btn7_txt);
        btn7_img = findViewById(R.id.btn7_img);
        btn8 = findViewById(R.id.btn8);
        btn8_txt = findViewById(R.id.btn8_txt);
        btn8_img = findViewById(R.id.btn8_img);

        main_btn = findViewById(R.id.main_btn);

        btn1.setClickable(true);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn1_txt, btn2_txt, btn1_img, btn2_img);
                check1 = true;
                dispoList.set(0,"1_1");
            }
        });

        btn2.setClickable(true);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn2_txt, btn1_txt, btn2_img, btn1_img);
                check1 = true;
                dispoList.set(0,"1_2");
            }
        });
        btn3.setClickable(true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn3_txt, btn4_txt, btn3_img, btn4_img);
                check2 = true;
                dispoList.set(1,"2_1");
            }
        });
        btn4.setClickable(true);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn4_txt, btn3_txt, btn4_img, btn3_img);
                check2 = true;
                dispoList.set(1,"2_2");
            }
        });
        btn5.setClickable(true);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn5_txt, btn6_txt, btn5_img, btn6_img);
                check3 = true;
                dispoList.set(2,"3_1");
            }
        });
        btn6.setClickable(true);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn6_txt, btn5_txt, btn6_img, btn5_img);
                check3 = true;
                dispoList.set(2,"3_2");
            }
        });
        btn7.setClickable(true);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn7_txt, btn8_txt, btn7_img, btn8_img);
                check4 = true;
                dispoList.set(3,"4_1");
            }
        });
        btn8.setClickable(true);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn8_txt, btn7_txt, btn8_img, btn7_img);
                check4 = true;
                dispoList.set(3,"4_2");
            }
        });

        main_btn.setClickable(true);
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });
        alreadysign();
    }

    public void selectChange(TextView change_blue, TextView change_gray, ImageView selected_image, ImageView non_image){
        selected_image.setImageResource(R.mipmap.checked_btn);
        non_image.setImageResource(R.mipmap.check_btn);
        change_blue.setTextColor(Color.parseColor("#3664cd"));
        change_gray.setTextColor(Color.parseColor("#66222222"));
    }

    public void Accountfinal(){                                                                     //파이어베이스에 성향을 올림. 네개 다 체크가 되어야 확인이 눌리도록설정
        if(check1 == true && check2 == true && check3 == true && check4 == true) {
            AccountDTO Accountset = new AccountDTO();

            String uid = auth.getCurrentUser().getUid();
            Accountset.disposition = dispoList;

            FirebaseDatabase.getReference().child("users").child(uid).child("dispo").setValue(dispoList);

            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원가입");
        builder.setMessage("회원가입을 완료하시겠습니까");
        builder.setIcon(android.R.mipmap.sym_def_app_icon);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Accountfinal();
                auth.signOut();
                Intent intent = new Intent(AccountFinActivity.this, LoginActivity.class);
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

    private void alreadysign(){
        if(check1 == true && check2 == true && check3 == true && check4 == true){
            Intent intent = new Intent (AccountFinActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
