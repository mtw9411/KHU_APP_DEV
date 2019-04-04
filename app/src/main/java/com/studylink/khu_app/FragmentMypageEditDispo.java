package com.studylink.khu_app;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FragmentMypageEditDispo extends Fragment {

    private boolean check1 = false;
    private boolean check2 = false;
    private boolean check3 = false;
    private boolean check4 = false;

    private LinearLayout btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    private TextView btn1_txt, btn2_txt, btn3_txt, btn4_txt, btn5_txt, btn6_txt, btn7_txt, btn8_txt;
    private ImageView btn1_img, btn2_img, btn3_img, btn4_img, btn5_img, btn6_img, btn7_img, btn8_img;
    private List<String> dispo;
    private CardView mypageEditDispo_finish;

    public FragmentMypageEditDispo(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.mypage_edit_fragment_right, container, false);

        btn1 = view.findViewById(R.id.btn1);
        btn1_txt = view.findViewById(R.id.btn1_txt);
        btn1_img = view.findViewById(R.id.btn1_img);
        btn2 = view.findViewById(R.id.btn2);
        btn2_txt = view.findViewById(R.id.btn2_txt);
        btn2_img = view.findViewById(R.id.btn2_img);
        btn3 = view.findViewById(R.id.btn3);
        btn3_txt = view.findViewById(R.id.btn3_txt);
        btn3_img = view.findViewById(R.id.btn3_img);
        btn4 = view.findViewById(R.id.btn4);
        btn4_txt = view.findViewById(R.id.btn4_txt);
        btn4_img = view.findViewById(R.id.btn4_img);
        btn5 = view.findViewById(R.id.btn5);
        btn5_txt = view.findViewById(R.id.btn5_txt);
        btn5_img = view.findViewById(R.id.btn5_img);
        btn6 = view.findViewById(R.id.btn6);
        btn6_txt = view.findViewById(R.id.btn6_txt);
        btn6_img = view.findViewById(R.id.btn6_img);
        btn7 = view.findViewById(R.id.btn7);
        btn7_txt = view.findViewById(R.id.btn7_txt);
        btn7_img = view.findViewById(R.id.btn7_img);
        btn8 = view.findViewById(R.id.btn8);
        btn8_txt = view.findViewById(R.id.btn8_txt);
        btn8_img = view.findViewById(R.id.btn8_img);

        mypageEditDispo_finish = view.findViewById(R.id.mypageEditDispo_finish);

        // 저장된 성향 불러오기
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("disposition");
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dispo = (List) dataSnapshot.getValue();
                if(dispo.get(0).equals(btn1_txt.getText())){
                    selectChange(btn1_txt,btn2_txt,btn1_img,btn2_img);
                }
                else{
                    selectChange(btn2_txt,btn1_txt,btn2_img,btn1_img);
                }
                if(dispo.get(1).equals(btn3_txt.getText())){
                    selectChange(btn3_txt,btn4_txt,btn3_img,btn4_img);
                }
                else{
                    selectChange(btn4_txt,btn3_txt,btn4_img,btn3_img);
                }
                if(dispo.get(2).equals(btn5_txt.getText())){
                    selectChange(btn5_txt,btn6_txt,btn5_img,btn6_img);
                }
                else{
                    selectChange(btn6_txt,btn5_txt,btn6_img,btn5_img);
                }
                if(dispo.get(3).equals(btn7_txt.getText())){
                    selectChange(btn7_txt,btn8_txt,btn7_img,btn8_img);
                }
                else{
                    selectChange(btn8_txt,btn7_txt,btn8_img,btn7_img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btn1.setClickable(true);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn1_txt, btn2_txt, btn1_img, btn2_img);
                check1 = true;
                dispo.set(0,"외향적인");
            }
        });

        btn2.setClickable(true);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn2_txt, btn1_txt, btn2_img, btn1_img);
                check1 = true;
                dispo.set(0,"내향적인");
            }
        });
        btn3.setClickable(true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn3_txt, btn4_txt, btn3_img, btn4_img);
                check2 = true;
                dispo.set(1,"직관적인");
            }
        });
        btn4.setClickable(true);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn4_txt, btn3_txt, btn4_img, btn3_img);
                check2 = true;
                dispo.set(1,"현실적인");
            }
        });
        btn5.setClickable(true);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn5_txt, btn6_txt, btn5_img, btn6_img);
                check3 = true;
                dispo.set(2,"이성적인");
            }
        });
        btn6.setClickable(true);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn6_txt, btn5_txt, btn6_img, btn5_img);
                check3 = true;
                dispo.set(2,"원칙적인");
            }
        });
        btn7.setClickable(true);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn7_txt, btn8_txt, btn7_img, btn8_img);
                check4 = true;
                dispo.set(3,"계획적인");
            }
        });
        btn8.setClickable(true);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectChange(btn8_txt, btn7_txt, btn8_img, btn7_img);
                check4 = true;
                dispo.set(3,"탐색적인");
            }
        });

        mypageEditDispo_finish.setClickable(true);
        mypageEditDispo_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.setValue(dispo);
                Toast.makeText(getActivity(),"수정 완료!", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }


    public void selectChange(TextView selectTxt, TextView nonTxt, ImageView selected_image, ImageView non_image){
        selected_image.setImageResource(R.mipmap.icon_17);
        non_image.setImageResource(R.mipmap.icon_15);
        selectTxt.setTextColor(Color.parseColor("#8c47ff"));
        nonTxt.setTextColor(Color.parseColor("#66222222"));
    }

}
