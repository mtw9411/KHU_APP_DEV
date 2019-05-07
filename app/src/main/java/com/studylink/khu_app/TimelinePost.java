package com.studylink.khu_app;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimelinePost extends AppCompatActivity {

    private RecyclerView timelinePost_recyclerPicture;
    private WritingRecyclerViewAdapter WritingAdapter;
    private RelativeLayout timelinePost_write, timelinePost_writeChild, timelinePost_vote, timelinePost_voteChild;
    private LinearLayout timelinePost_multi, timelinePost_deadline;
    private TextView timelinePost_writeTitle, timelinePost_voteTitle, timelinePost_multiText, timelinePost_deadlineText, timelinePost_fin;
    private ImageView timelinePost_writeImg, timelinePost_voteImg, timelinePost_img, timelinePost_multiImg, timelinePost_deadlineImg;
    private EditText timelinePost_content, timelinePost_voteChildTitle;
    private int checkWrite = 1;
    private int checkVote = 0;
    private int checkMulti = 0;
    private int checkDeadline = 0;
    private boolean check_content = false;
    private String currentuser;
    private int selectedPosition;
    private static final int GALLERY_CODE = 10;

    private RoomDTO currentRoom;
    private RoomUploadDTO roomUploadDTO = new RoomUploadDTO();
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference dataref, dataref2;
    private ArrayList<Uri> mDataset = new ArrayList<>();
    private ArrayList<String> mDatasetString = new ArrayList<>();
    private ArrayList<String> upfilename = new ArrayList<>();
    private ArrayList<String> filetitle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_post);

        Intent intent = getIntent();
        currentRoom = (RoomDTO)intent.getSerializableExtra("currentRoom");
        selectedPosition = intent.getIntExtra("selectedPosition", 0);

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentuser = auth.getCurrentUser().getUid();

        timelinePost_write = findViewById(R.id.timelinePost_write);
        timelinePost_writeTitle = findViewById(R.id.timelinePost_writeTitle);
        timelinePost_writeImg = findViewById(R.id.timelinePost_writeImg);
        timelinePost_writeChild = findViewById(R.id.timelinePost_writeChild);
        timelinePost_content = findViewById(R.id.timelinePost_content);
        timelinePost_img = findViewById(R.id.timelinePost_img);
        timelinePost_recyclerPicture = findViewById(R.id.timelinePost_recyclerPicture);

        timelinePost_vote = findViewById(R.id.timelinePost_vote);
        timelinePost_voteTitle = findViewById(R.id.timelinePost_voteTitle);
        timelinePost_voteImg = findViewById(R.id.timelinePost_voteImg);
        timelinePost_voteChild = findViewById(R.id.timelinePost_voteChild);
        timelinePost_multi = findViewById(R.id.timelinePost_multi);
        timelinePost_multiText = findViewById(R.id.timelinePost_multiText);
        timelinePost_multiImg = findViewById(R.id.timelinePost_multiImg);
        timelinePost_deadline = findViewById(R.id.timelinePost_deadline);
        timelinePost_deadlineText = findViewById(R.id.timelinePost_deadlineText);
        timelinePost_deadlineImg = findViewById(R.id.timelinePost_deadlineImg);
        timelinePost_voteChildTitle = findViewById(R.id.timelinePost_voteChildTitle);

        timelinePost_fin = findViewById(R.id.timelinePost_fin);

        // 현재 유저
        database.getReference().child("users").child(currentuser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccountDTO acc = dataSnapshot.getValue(AccountDTO.class);
                roomUploadDTO.setUploadername(acc.getUsername());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager)horizontalLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        timelinePost_recyclerPicture.setLayoutManager(horizontalLayoutManager);
        WritingAdapter = new WritingRecyclerViewAdapter(mDataset);
        timelinePost_recyclerPicture.setAdapter(WritingAdapter);

        // SESSION 글쓰기
        timelinePost_write.setClickable(true);
        timelinePost_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWrite = onChangeLayout(timelinePost_writeChild, timelinePost_writeTitle, timelinePost_writeImg, checkWrite);
            }
        });

        // SESSION 글쓰기 - 이미지 버튼
        timelinePost_img.setClickable(true);
        timelinePost_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);
            }
        });
        //version 몇 이상부터 작동하기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        // SESSION 투표
        timelinePost_vote.setClickable(true);
        timelinePost_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVote = onChangeLayout(timelinePost_voteChild, timelinePost_voteTitle, timelinePost_voteImg, checkVote);
            }
        });

        // 중복 선택 가능 버튼
        timelinePost_multi.setClickable(true);
        timelinePost_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMulti =  onColorChange(timelinePost_multiText, timelinePost_multiImg, checkMulti);
            }
        });

        // 마감시간 설정 버튼
        timelinePost_deadline.setClickable(true);
        timelinePost_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDeadline = onColorChange(timelinePost_deadlineText, timelinePost_deadlineImg, checkDeadline);
            }
        });

        // 완료 버튼
        timelinePost_fin.setClickable(true);
        timelinePost_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(roomUploadDTO);

                Intent intent = new Intent(TimelinePost.this, Fragement_navi.class);
                intent.putExtra("frag_num", 1);
                intent.putExtra("myRoomNum", selectedPosition);
                Toast.makeText(TimelinePost.this, "업로드 완료", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == GALLERY_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                ClipData clipData = data.getClipData();
                if(clipData != null){
                    for(int i = 0; i < clipData.getItemCount(); i++){
                        mDataset.add(clipData.getItemAt(i).getUri());
                        mDatasetString.add(clipData.getItemAt(i).getUri().toString());
                    }
                    WritingAdapter.notifyDataSetChanged();
                }
                Toast.makeText(this, "이미지 추가 완료", Toast.LENGTH_SHORT).show();
            }
        }
    }


//데이터 업로드
    private void uploadFile(RoomUploadDTO roomUploadDTO) {
        dataref= database.getReference("RoomUpload");
        dataref2=database.getReference("room");
        final RoomUploadDTO roomUpload = roomUploadDTO;

        String contentCheck = timelinePost_content.getText().toString();
        String currentRoomid = currentRoom.getId();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
        Date now = new Date();

    //업로드할 내용이 있으면 수행
        // 스터디 카테고리
        roomUpload.setCategory(currentRoom.getSpinner1());
        // 글 종류
        roomUpload.setTextType("소식");
        // 내용
        if (contentCheck.trim().length()>0){
            roomUpload.setWriting_content(contentCheck);
            check_content = true;
        }
        //업로드 시간
        roomUpload.setTime(now);
        //업로드할 이미지가 있으면 수행
        if (mDataset.size() != 0) {
            //Unique한 파일명을 만들자.
            for(int i = 0; i < mDataset.size(); i++) {
                upfilename.add(formatter.format(now) + i +".jpeg");
            }
            //storage 주소와 폴더 파일명을 지정해 준다.
            for(int j = 0; j < mDataset.size(); j++) {
                StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images/" + currentRoomid + "/" + contentCheck + "/" + upfilename.get(j));
                storageRef.putFile(mDataset.get(j));
                filetitle.add(upfilename.get(j));
            }
            roomUpload.setFiletitle(filetitle);
            roomUpload.setFilename(upfilename);
        }
        // 메인페이지 "내가참여중인 방" 최근 소식 최신화
        currentRoom.setNewsTime(now);

        // 데이터베이스에 업로드
        dataref.child(currentRoomid).child(formatter.format(now)).setValue(roomUpload);
        dataref2.child(currentRoom.getId()).setValue(currentRoom);
    }




    // recyclerView 어댑터
    static class WritingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Uri> bit;

        public WritingRecyclerViewAdapter(ArrayList<Uri> bitmaps){
            bit = bitmaps;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_writing_recycler_picture, viewGroup, false);

            return new WritingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ((WritingViewHolder) viewHolder).upload_image.setImageURI(bit.get(i));
        }

        @Override
        public int getItemCount() {
            return bit.size();
        }

        private class WritingViewHolder extends RecyclerView.ViewHolder {
            ImageView upload_image;
            ImageView delete_picture;

            public WritingViewHolder(View view) {
                super(view);
                upload_image = view.findViewById(R.id.upload_imageview);

            }
        }
    }

    // 레이아웃 열고 닫기
    public int onChangeLayout(View changeView, TextView changeText, ImageView changeImg, int check){
        // 레이아웃이 닫혀있으면
        if(check == 0){
            // 레이아웃 높이 설정
            ViewGroup.LayoutParams layoutParams = changeView.getLayoutParams();
            if(changeText.getText().equals("SESSION 투표")){
                layoutParams.height = Toolbar.LayoutParams.MATCH_PARENT;
            }
            else{
                layoutParams.height = Toolbar.LayoutParams.WRAP_CONTENT;
            }
            changeView.setLayoutParams(layoutParams);
            // 레이아웃 텍스트 색깔 변경
            changeText.setTextColor(Color.parseColor("#4c81f8"));
            // 레아이웃 이미지 변경
            changeImg.setImageResource(R.mipmap.icon_44);
            check = 1;
        }
        // 레이아웃이 열려있으면
        else{
            // 선택한 레이아웃 높이 설정
            ViewGroup.LayoutParams layoutParams = changeView.getLayoutParams();
            layoutParams.height = 0;
            changeView.setLayoutParams(layoutParams);
            // 선택된 레이아웃 텍스트 색깔 변경
            changeText.setTextColor(Color.parseColor("#707070"));
            // 선택된 레아이웃 이미지 변경
            changeImg.setImageResource(R.mipmap.icon_45);
            check = 0;
        }
        return check;
    }

    public int onColorChange(TextView changeText, ImageView changeImg, int check){
        // 선택이 안되어있으면
        if(check==0){
            changeText.setTextColor(Color.parseColor("#4c81f8"));
            changeImg.setImageResource(R.mipmap.icon_16);
            check = 1;
        }
        else{
            changeText.setTextColor(Color.parseColor("#99222222"));
            changeImg.setImageResource(R.mipmap.icon_15);
            check = 0;
        }
        return check;
    }
}