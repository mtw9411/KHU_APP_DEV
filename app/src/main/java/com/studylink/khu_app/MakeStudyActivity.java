package com.studylink.khu_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MakeStudyActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editFine;

    public FirebaseAuth auth;
    public boolean check1 = false;
    public boolean check2 = false;
    public boolean check3 = false;
    public boolean check4 = false;

    DatabaseReference databaseRoom;
    public List<String> dispoRoom = new ArrayList<>();

    TextView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makestudy);

        Spinner s1 = (Spinner)findViewById(R.id.spinner1);
        Spinner s2 =(Spinner)findViewById(R.id.spinner2);

        final String [] classification1 = {"어학","자격증","공무원","기타"};
        final String [] classification2 = {"영어","중국어","일어"};

        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(), R.layout.spin,classification1 );
        adapter.setDropDownViewResource( R.layout.spin_dropdown);

        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        ArrayAdapter adapter2 = new ArrayAdapter(
                getApplicationContext(),
                R.layout.spin,
                classification2);
        adapter2.setDropDownViewResource(R.layout.spin_dropdown);

        s2.setAdapter(adapter2);

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });




        databaseRoom= FirebaseDatabase.getInstance().getReference("room");
        dispoRoom.add("a");
        dispoRoom.add("b");
        dispoRoom.add("c");
        dispoRoom.add("d");


        btn1 = findViewById(R.id.textView43); //외향적인
        btn2 = findViewById(R.id.textView49); //내향적인
        btn3 = findViewById(R.id.textView53); //직관적인
        btn4 = findViewById(R.id.textView54); //현실적인
        btn5 = findViewById(R.id.textView55); //이상적인
        btn6 = findViewById(R.id.textView56); //원칙적인
        btn7 = findViewById(R.id.textView57); //계획적인
        btn8 = findViewById(R.id.textView58); //탐색적인
        next = findViewById(R.id.textView61);

        editTextName = (EditText) findViewById(R.id.roomName);
        editFine =(EditText) findViewById(R.id.fine);


        btn1.setClickable(true);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                colorChange(btn1,btn2);
                check1 = true;
                dispoRoom.set(0,"1_1");
            }
        });
        btn2.setClickable(true);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn2,btn1);
                check1 = true;
                dispoRoom.set(0,"1_2");
            }
        });
        btn3.setClickable(true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn3,btn4);
                check2 = true;
                dispoRoom.set(1,"2_1");
            }
        });
        btn4.setClickable(true);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn4,btn3);
                check2 = true;
                dispoRoom.set(1,"2_2");
            }
        });
        btn5.setClickable(true);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn5,btn6);
                check3 = true;
                dispoRoom.set(2,"3_1");
            }
        });
        btn6.setClickable(true);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn6,btn5);
                check3 = true;
                dispoRoom.set(2,"3_2");
            }
        });
        btn7.setClickable(true);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn7,btn8);
                check4 = true;
                dispoRoom.set(3,"4_1");
            }
        });
        btn8.setClickable(true);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn8,btn7);
                check4 = true;
                dispoRoom.set(3,"4_2");
            }
        });

        next.setClickable(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakeStudyActivity.this, MakeStudyFinActivity.class));

                addRoom();
            }
        });
    }







    public void colorChange(TextView select, TextView non){
        select.setBackground(getResources().getDrawable(R.drawable.red_border));
        non.setBackground(getResources().getDrawable(R.drawable.gray_border));
        select.setTextColor(Color.parseColor("#ff0930"));
        non.setTextColor(Color.parseColor("#707070"));
    }

    private void addRoom(){
        String name=editTextName.getText().toString();
        Long fine =  Long.parseLong(editFine.getText().toString());

        if(check1 == true && check2 == true && check3 == true && check4 == true) {
            String id= databaseRoom.push().getKey();
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setFine(fine);
            roomDTO.setRoomName(name);
            roomDTO.setId(id);
            roomDTO.setRoomdisposition(dispoRoom);


            databaseRoom.child(id).setValue(roomDTO);


            databaseRoom.child(id).child("dispo").setValue(roomDTO.getRoomdisposition());

            Toast.makeText(this, "room added", Toast.LENGTH_LONG).show();

        }

    }




}
