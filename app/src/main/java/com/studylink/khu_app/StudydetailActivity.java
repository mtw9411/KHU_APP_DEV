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
import java.util.Map;

public class StudydetailActivity extends AppCompatActivity {

    private Button Enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etc_studydetail);

        Enter = findViewById(R.id.Enter);




            TextView total_member = (TextView) findViewById(R.id.textView18);
            Intent member_intent = getIntent();
            Long totalmember = member_intent.getLongExtra("member", 0);
            total_member.setText("1/" + String.valueOf(totalmember));

            TextView content = (TextView)findViewById(R.id.textView33);
            Intent content_intent = getIntent();
            String contents = content_intent.getStringExtra("content");
            content.setText(contents);

            TextView Fine = (TextView) findViewById(R.id.textView32);
            Intent fine_intent = getIntent();
            Long fine = fine_intent.getLongExtra("fine", 0);
            Fine.setText( String.valueOf(fine)+"won");

            TextView spinner2 = (TextView)findViewById(R.id.textView17);
            Intent spinner2_intent = getIntent();
            String spinner_2 = spinner2_intent.getStringExtra("spinner2");
            spinner2.setText(spinner_2+"스터디");





            Intent dispo_intent = getIntent();
            String dispo[] = dispo_intent.getExtras().getStringArray("dispor");
            final String str1=dispo[0];

            TextView dispo1 = (TextView)findViewById(R.id.textView27);
            String dispo_1 = dispo[0];
            dispo1.setText(dispo_1);

            TextView dispo2 = (TextView)findViewById(R.id.textView28);
            String dispo_2 = dispo[1];
            dispo2.setText(dispo_2);

            TextView dispo3 = (TextView)findViewById(R.id.textView29);
            String dispo_3 = dispo[2];
            dispo3.setText(dispo_3);

            TextView dispo4 = (TextView)findViewById(R.id.textView30);
            String dispo_4 = dispo[3];
            dispo4.setText(dispo_4);


            if(str1.equals("내향적인")) {

                TextView type2 = (TextView) findViewById(R.id.textView14);
                String type_2 = "조용한 성향이신 분들을 위한";
                type2.setText(type_2);
            }

            else if(str1.equals("외향적인")){
                TextView type2 = (TextView) findViewById(R.id.textView14);
                String type_2 = "활발한 성향이신 분들을 위한";
                type2.setText(type_2);
            }





    }
}
