package com.studylink.khu_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    EditText editTotalmember;
    public FirebaseAuth auth;
    public boolean check1 = false;
    public boolean check2 = false;
    public boolean check3 = false;
    public boolean check4 = false;
    DatabaseReference databaseRoom;
    public List<String> dispoRoom = new ArrayList<>();
    TextView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView next;
    ArrayAdapter<CharSequence> adspin1, adspin2;
    String choice_do="";
    String choice_se="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makestudy);



       final Spinner s1 = (Spinner)findViewById(R.id.spinner1);
       final Spinner s2 =(Spinner)findViewById(R.id.spinner2);

       adspin1 = ArrayAdapter.createFromResource(this, R.array.classification1, android.R.layout.simple_spinner_dropdown_item);
       adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       s1.setAdapter(adspin1);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adspin1.getItem(i).equals("어학")) {
                    choice_do = "어학";
                    adspin2 = ArrayAdapter.createFromResource(MakeStudyActivity.this, R.array.classification2_language, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s2.setAdapter(adspin2);
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();



                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else if (adspin1.getItem(i).equals("자격증")) {
                    choice_do = "자격증";
                    adspin2 = ArrayAdapter.createFromResource(MakeStudyActivity.this, R.array.classification2_certificate, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s2.setAdapter(adspin2);
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }

                else if (adspin1.getItem(i).equals("공무원")) {
                    choice_do = "공무원";
                    adspin2 = ArrayAdapter.createFromResource(MakeStudyActivity.this, R.array.classification2_official, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s2.setAdapter(adspin2);
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }

                else if (adspin1.getItem(i).equals("기타")) {
                    choice_do = "기타";
                    adspin2 = ArrayAdapter.createFromResource(MakeStudyActivity.this, R.array.classification2_etc, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s2.setAdapter(adspin2);
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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
        editTotalmember=(EditText) findViewById(R.id.totalmember);

        btn1.setClickable(true);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                colorChange(btn1,btn2);
                check1 = true;
                dispoRoom.set(0,"외향적인");
            }
        });
        btn2.setClickable(true);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn2,btn1);
                check1 = true;
                dispoRoom.set(0,"내향적인");
            }
        });
        btn3.setClickable(true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn3,btn4);
                check2 = true;
                dispoRoom.set(1,"직관적인");
            }
        });
        btn4.setClickable(true);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn4,btn3);
                check2 = true;
                dispoRoom.set(1,"현실적인");
            }
        });
        btn5.setClickable(true);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn5,btn6);
                check3 = true;
                dispoRoom.set(2,"이상적인");
            }
        });
        btn6.setClickable(true);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn6,btn5);
                check3 = true;
                dispoRoom.set(2,"원칙적인");
            }
        });
        btn7.setClickable(true);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn7,btn8);
                check4 = true;
                dispoRoom.set(3,"계획적인");
            }
        });
        btn8.setClickable(true);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(btn8,btn7);
                check4 = true;
                dispoRoom.set(3,"탐색적인");
            }
        });

        next.setClickable(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        // 방제목 입력 확인
        String name = editTextName.getText().toString();
        String totalMember=editTotalmember.getText().toString();
        if(name.trim().length()>0){
            // 성향 입력 확인
            if(check1 == true && check2 == true && check3 == true && check4 == true) {
                // 벌금 입력 확인
                String fineCheck = editFine.getText().toString();
                Long fine = (fineCheck.trim().length()>0) ? Long.parseLong(fineCheck) : 0L;
                Long totalmember = Long.parseLong(totalMember);
                String id = databaseRoom.push().getKey();

                if(totalmember<20) {
                    RoomDTO roomDTO = new RoomDTO();
                    roomDTO.setTotal_member(totalmember);
                    roomDTO.setId(id);
                    roomDTO.setSpinner1(choice_do);
                    roomDTO.setSpinner2(choice_se);
                    roomDTO.setRoomName(name);
                    roomDTO.setFine(fine);
                    roomDTO.setRoomdisposition(dispoRoom);

                    databaseRoom.child(id).setValue(roomDTO);
                    Toast.makeText(this, "room added", Toast.LENGTH_LONG).show();
                    //다음 화면에 roomDTO 객체 전달
                    Intent intent = new Intent(MakeStudyActivity.this, MakeStudyFinActivity.class);
                    intent.putExtra("room", roomDTO);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "최대 정원은 20명입니다.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "성향을 모두 선택해 주세요.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "방 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();

        }

    }





}
