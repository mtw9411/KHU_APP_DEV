package com.studylink.khu_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class FragmentMypageEditProfile extends Fragment {

    private static final int GALLERY_CODE1 = 10;
    ImageView profileImg;
    EditText mypageEdit_name, mypageEdit_birth;
    TextView mypageEdit_female, mypageEdit_male;
    CardView mypageEdit_finish, mypageEdit_address;
    private MypageEditPopupActivity popUp;
    private int check = 0;
    private Uri uri;
    private FirebaseAuth auth;
    private Bitmap mbitmap;

    public FragmentMypageEditProfile(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.mypage_edit_fragment, container, false);

        final Bundle bundle = this.getArguments();

        auth = FirebaseAuth.getInstance();
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
                Intent intent = new Intent(getActivity(), Fragement_navi.class);
                startActivity(intent);

//                android.support.v4.app.Fragment fragment = new Mypage_main();
//                Bundle bundle = new Bundle();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                mbitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                bundle.putByteArray("imagebyte", byteArray);
//
//                fragment.setArguments(bundle);

                uploadimage();

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {                                       //version 몇 이상부터 작동하기
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        profileImg.setClickable(true);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
        }

        if (requestCode == GALLERY_CODE1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                    mbitmap = BitmapFactory.decodeStream(in);
                    in.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public void colorChange(TextView select, TextView non){
        select.setBackground(getResources().getDrawable(R.drawable.pink_border, null));
        non.setBackground(null);
    }

    private void uploadimage() {
            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            String filename = auth.getCurrentUser().getUid() + ".jpeg";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images/" + auth.getCurrentUser().getUid() + "/" + filename);
            storageRef.putFile(uri);
    }

}
