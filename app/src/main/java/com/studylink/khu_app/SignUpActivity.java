package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private RelativeLayout select_all, select_item1, select_item2, select_item3, select_item4;
    private TextView signup_next;
    private ImageView tosImage, tosImage1, tosImage2, tosImage3, tosImage4;
    private int checkAll=1, check1=1, check2=1, check3=1, check4=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        select_all = findViewById(R.id.select_all);
        select_item1 = findViewById(R.id.select_item1);
        select_item2 = findViewById(R.id.select_item2);
        select_item3 = findViewById(R.id.select_item3);
        select_item4 = findViewById(R.id.select_item4);
        tosImage = findViewById(R.id.tosImage);
        tosImage1 = findViewById(R.id.tosImage1);
        tosImage2 = findViewById(R.id.tosImage2);
        tosImage3 = findViewById(R.id.tosImage3);
        tosImage4= findViewById(R.id.tosImage4);
        signup_next = (TextView)findViewById(R.id.sign_up_next);

        select_all.setClickable(true);
        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll == 1){
                    checkAll = changeImage(tosImage, 1);
                    check1 = changeImage(tosImage1, 1);
                    check2 = changeImage(tosImage2, 1);
                    check3 = changeImage(tosImage3, 1);
                    check4 = changeImage(tosImage4, 1);
                }
                else {
                    tosImage.setImageResource(R.mipmap.check_btn);
                    checkAll=1;
                }
            }
        });

        select_item1.setClickable(true);
        select_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check1 = changeImage(tosImage1, check1);
            }
        });
        select_item2.setClickable(true);
        select_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check2 = changeImage(tosImage2, check2);
            }
        });
        select_item3.setClickable(true);
        select_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check3 = changeImage(tosImage3, check3);
            }
        });
        select_item4.setClickable(true);
        select_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check4 = changeImage(tosImage4, check4);
            }
        });
        signup_next.setClickable(false);
        signup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check1 == 0 && check2 == 0 && check3 == 0) {
                    Intent intent = new Intent(SignUpActivity.this, SignUpFinalActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "(필수)약관에 동의했는지 확인하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public int changeImage(ImageView select, int check){
        if(check == 1) {
            select.setImageResource(R.mipmap.checked_btn);
            check=0;
            return check;
        }
        else{
            select.setImageResource(R.mipmap.check_btn);
            check=1;
            return check;
        }
    }
}
