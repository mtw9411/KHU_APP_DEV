package com.studylink.khu_app;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentMypageEditProfile extends Fragment {

    ImageView profileImg;
    EditText mypageEdit_name, mypageEdit_birth;
    TextView mypageEdit_female, mypageEdit_male;
    CardView mypageEdit_finish, mypageEdit_address;
    private MypageEditPopupActivity popUp;
    private int check = 0;

    public FragmentMypageEditProfile(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.mypage_edit_fragment, container, false);

        final Bundle bundle = this.getArguments();

        mypageEdit_name = view.findViewById(R.id.mypageEdit_name);
        profileImg = view.findViewById(R.id.profileImg);
        mypageEdit_birth = view.findViewById(R.id.mypageEdit_birth);
        mypageEdit_female = view.findViewById(R.id.mypageEdit_female);
        mypageEdit_male = view.findViewById(R.id.mypageEdit_male);
        mypageEdit_address = view.findViewById(R.id.mypageEdit_address);
        mypageEdit_finish = view.findViewById(R.id.mypageEdit_finish);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        if(bundle!=null){
            AccountDTO user = (AccountDTO)bundle.getSerializable("userData");
            mypageEdit_name.setText(user.getUsername());
            mypageEdit_birth.setText(user.getUserbirth());
            if(user.getUsersex().equals("male")){
                colorChange(mypageEdit_male, mypageEdit_female);
                check = 1;
            }
            else{
                colorChange(mypageEdit_female, mypageEdit_male);
                check = 0;
            }
        }


        mypageEdit_female.setClickable(true);
        mypageEdit_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(mypageEdit_female, mypageEdit_male);
                check = 0;
            }
        });

        mypageEdit_male.setClickable(true);
        mypageEdit_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChange(mypageEdit_male, mypageEdit_female);
                check = 1;
            }
        });

        mypageEdit_address.setClickable(true);
        mypageEdit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 담아서 팝업(액티비티) 호출
//                Intent intent = new Intent(getActivity(), MypageEditPopupActivity.class);
//                intent.putExtra("data", "Test Popup");
//                startActivityForResult(intent, 1)
                popUp = new MypageEditPopupActivity(getActivity(), "[다이얼로그 제목]");
                popUp.show();
            }
        });


        mypageEdit_finish.setClickable(true);
        mypageEdit_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AccountDTO saveData = dataSnapshot.getValue(AccountDTO.class);

                        saveData.setUsername(mypageEdit_name.getText().toString());
                        saveData.setUserbirth(mypageEdit_birth.getText().toString());
                        if(check == 1){
                            saveData.setUsersex("male");
                        }
                        else{
                            saveData.setUsersex("female");
                        }
                        dr.setValue(saveData);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getActivity(), "수정 완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Mypage_main.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @SuppressLint("NewApi")
    public void colorChange(TextView select, TextView non){
        select.setBackground(getResources().getDrawable(R.drawable.pink_border, null));
        non.setBackground(null);
    }

}
