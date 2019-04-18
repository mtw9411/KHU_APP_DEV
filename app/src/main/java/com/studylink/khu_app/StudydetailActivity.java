package com.studylink.khu_app;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudydetailActivity extends AppCompatActivity {

    private TextView total_member, studyDetail_contentTitle, content, Fine, spinner2, studyDetail_region, studyDetail_age, studyDetail_gender,
            dispo1, dispo2, dispo3, dispo4, type2;
    private List<String> dispo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etc_studydetail);

        total_member = findViewById(R.id.textView18);
        studyDetail_contentTitle = findViewById(R.id.studyDetail_contentTitle);
        content = findViewById(R.id.textView33);
        Fine = findViewById(R.id.textView32);
        spinner2 = findViewById(R.id.textView17);
        studyDetail_region = findViewById(R.id.studyDetail_region);
        studyDetail_age = findViewById(R.id.studyDetail_age);
        studyDetail_gender = findViewById(R.id.studyDetail_gender);
        type2 = findViewById(R.id.textView14);
        dispo1 = findViewById(R.id.textView27);
        dispo2 = findViewById(R.id.textView28);
        dispo3 = findViewById(R.id.textView29);
        dispo4 = findViewById(R.id.textView30);

        Bundle bundle = getIntent().getExtras();
        RoomDTO room = (RoomDTO)bundle.getSerializable("roomDetail");

        total_member.setText(room.getMember() + "/" + room.getTotal_member());
        studyDetail_contentTitle.setText(room.getContent1());
        content.setText(room.getContent2());
        Log.d("##################3", room.getId());
        Log.d("##################3", room.getFine().toString());
        Fine.setText(room.getFine().toString());
        spinner2.setText(room.getSpinner2() + " 스터디");
        studyDetail_region.setText(room.getRegion());
        studyDetail_age.setText(room.getAge());
        studyDetail_gender.setText(room.getGender());

        dispo = room.getRoomdisposition();
        dispo1.setText(dispo.get(0));
        dispo2.setText(dispo.get(1));
        dispo3.setText(dispo.get(2));
        dispo4.setText(dispo.get(3));

        String str1 = dispo.get(0);
        if(str1.equals("내향적인")) {
            type2.setText("조용한 성향이신 분들을 위한");
        }

        else{
            type2.setText("활발한 성향이신 분들을 위한");
        }

    }
}
