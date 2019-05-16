package com.studylink.khu_app;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TimelinePost extends AppCompatActivity {

    private RecyclerView timelinePost_recyclerPicture;
    private WritingRecyclerViewAdapter WritingAdapter;
    private RelativeLayout timelinePost_write, timelinePost_writeChild, timelinePost_vote, timelinePost_voteChild, timelinePost_votePlus;
    private LinearLayout timelinePost_multi, timelinePost_deadline, timelinePost_Linearlayout, voteRelativeLayout_Deadline;
    private TextView timelinePost_writeTitle, timelinePost_voteTitle, timelinePost_multiText, timelinePost_deadlineText, vote_deadlineText, timelinePost_fin;
    private ImageView timelinePost_writeImg, timelinePost_voteImg, timelinePost_img, timelinePost_multiImg, timelinePost_deadlineImg;
    private EditText timelinePost_content, timelinePost_voteChildTitle;
    private Button vote_deadlineSet;
    private boolean checkWrite = true;
    private int checkMulti = 0;
    private int checkDeadline = 0;
    private boolean pass = false;
    private int selectedPosition;
    private static final int GALLERY_CODE = 10;
    private int choiceNum = 0;
    private Date deadlineDate;

    private RoomDTO currentRoom;
    private RoomUploadDTO roomUploadDTO = new RoomUploadDTO();
    private AccountDTO currentUser;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference dataref, dataref2, dataref3;
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
        currentUser = (AccountDTO)intent.getSerializableExtra("currentUser");
        Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_SHORT).show();

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

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
        timelinePost_Linearlayout = findViewById(R.id.timelinePost_Linearlayout);
        timelinePost_votePlus = findViewById(R.id.timelinePost_votePlus);
        voteRelativeLayout_Deadline = findViewById(R.id.voteRelativeLayout_Deadline);
        vote_deadlineSet = findViewById(R.id.vote_deadlineSet);
        vote_deadlineText = findViewById(R.id.vote_deadlineText);

        timelinePost_fin = findViewById(R.id.timelinePost_fin);

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
                checkWrite = true;
                onChangeLayout(timelinePost_writeChild, timelinePost_writeTitle, timelinePost_writeImg,
                        timelinePost_voteChild, timelinePost_voteTitle, timelinePost_voteImg);
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
                checkWrite = false;
                onChangeLayout(timelinePost_voteChild, timelinePost_voteTitle, timelinePost_voteImg,
                        timelinePost_writeChild, timelinePost_writeTitle, timelinePost_writeImg);
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
                if(checkDeadline==1){
                    ViewGroup.LayoutParams layoutParams = voteRelativeLayout_Deadline.getLayoutParams();
                    layoutParams.height = Toolbar.LayoutParams.WRAP_CONTENT;
                    voteRelativeLayout_Deadline.setLayoutParams(layoutParams);
                }
                else{
                    ViewGroup.LayoutParams layoutParams = voteRelativeLayout_Deadline.getLayoutParams();
                    layoutParams.height = 0;
                    voteRelativeLayout_Deadline.setLayoutParams(layoutParams);
                }
            }
        });

        vote_deadlineSet.setClickable(true);
        vote_deadlineSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int date = calendar.get(calendar.DATE);

                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(TimelinePost.this, year + "년" + (monthOfYear+1) + "월" + dayOfMonth + "일", Toast.LENGTH_SHORT).show();
                        vote_deadlineText.setText(year + "년" + (monthOfYear+1) + "월" + dayOfMonth + "일");
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        deadlineDate = cal.getTime();
                        Toast.makeText(TimelinePost.this, deadlineDate.toString(), Toast.LENGTH_SHORT).show();
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(TimelinePost.this, listener, year, month, date);
                dialog.show();
            }
        });

        // 선택지 추가 버튼
        timelinePost_votePlus.setClickable(true);
        timelinePost_votePlus.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(choiceNum<10){
                    choiceNum++;
                    addEditTextView(choiceNum);
                }
                else{
                    Toast.makeText(TimelinePost.this, "항목을 10개 이상 만들 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 완료 버튼
        timelinePost_fin.setClickable(true);
        timelinePost_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(roomUploadDTO);

                if(pass){
                    Intent intent = new Intent(TimelinePost.this, Fragement_navi.class);
                    intent.putExtra("frag_num", 1);
                    intent.putExtra("myRoomNum", selectedPosition);
                    Toast.makeText(TimelinePost.this, "업로드 완료", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addEditTextView(int i){
        // EditTextView 생성
        EditText choice = new EditText(this);
        //int choiceId = getResources().getIdentifier("choice_" + i, "id", this.getPackageName());
        //Log.d("#######choiceId", Integer.toString(choiceId));
        choice.setTag("choice_"+ i);
        choice.setHint("선택지");
        choice.setHintTextColor(Color.parseColor("#4D222222"));
        choice.setTextSize(13);
        choice.setTextColor(Color.parseColor("#707070"));
        choice.setPadding(80,30,100,30);
        choice.setBackgroundResource(R.drawable.choice);
        choice.setElevation(10);
        choice.setHeight(140);

        //layout_width, layout_height, gravity 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,5,10,10);
        choice.setLayoutParams(lp);

        //부모 뷰에 추가
        timelinePost_Linearlayout.addView(choice);
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
        dataref3=database.getReference("users");
        final RoomUploadDTO roomUpload = roomUploadDTO;

        String currentRoomid = currentRoom.getId();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
        Date now = new Date();

        // 데이터베이스에 저장될 이름 = 업로드 시간
        String uploadingTime = formatter.format(now);

    //업로드할 내용이 있으면 수행
        // 방 이름
        roomUpload.setRoomId(currentRoom.getId());
        // 스터디 카테고리
        roomUpload.setCategory(currentRoom.getSpinner1());
        // 글쓴이 id & 닉네임
        roomUpload.setUploaderId(currentUser.getUid());
        roomUpload.setUploadername(currentUser.getUsername());
        // 글쓴이 프로필
        if(currentUser.getProfileImg()!=null){
            roomUpload.setUploaderImg(currentUser.getProfileImg());
        }
        //업로드 시간
        roomUpload.setTime(now);
        // 메인페이지 "내가참여중인 방" 최근 소식 시간 최신화
        currentRoom.setNewsTime(now);

    // 글쓰기 저장
        if(checkWrite){
            String contentCheck = timelinePost_content.getText().toString();
            if (contentCheck.trim().length()>0){
                // 글 종류
                roomUpload.setTextType("소식");
                // 내용
                roomUpload.setWriting_content(contentCheck);
                //업로드할 이미지가 있으면 수행
                if (mDataset.size() != 0) {
                    //Unique한 파일명을 만들자.
                    for(int i = 0; i < mDataset.size(); i++) {
                        upfilename.add(uploadingTime + i +".jpeg");
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

                // 알람 설정
                List<AccountDTO> roomMemberList = currentRoom.getMemberList();
                for(int i=0; i<roomMemberList.size(); i++){
                    AccountDTO member = roomMemberList.get(i);

                    // 나를 제외한 다른 멤버들
                    if(!member.getUid().equals(currentUser.getUid())){
                        List<RoomUploadDTO> alarmList;
                        if(member.getMyAlarm()!=null) {
                            alarmList = member.getMyAlarm();
                        }
                        else{
                            alarmList = new ArrayList<>();
                        }
                        alarmList.add(roomUpload);
                        member.setMyAlarm(alarmList);
                        // 알람 저장
                        dataref3.child(member.getUid()).setValue(member);
                    }
                }

                // 데이터베이스에 업로드
                dataref.child(currentRoomid).child(uploadingTime).setValue(roomUpload);
                dataref2.child(currentRoom.getId()).setValue(currentRoom);
                pass = true;
            }
            else{
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }

    // 투표 저장
        else{
            String contentCheck = timelinePost_voteChildTitle.getText().toString();
            if(contentCheck.trim().length()>0){
                // 선택지 리스트
                List<VoteDTO> choiceLIst = new ArrayList<>();
                for(int i=0; i<choiceNum; i++){
                    // 선택지 각각의 아이디 받아오기
                    //Context context = TimelinePost.this;
                    //int choiceId  = context.getResources().getIdentifier("choice_" + (i+1), "id", this.getPackageName());
                    //EditText choiceView = findViewById(choiceId);
                    EditText choiceView = timelinePost_Linearlayout.findViewWithTag("choice_" + (i+1));
                    Log.d("#########check1", "choice_" + (i+1));
                    // 비어있지 않으면
                    String choiceText = choiceView.getText().toString();
                    if(choiceText.trim().length()>0){
                        VoteDTO voteDTO = new VoteDTO();
                        voteDTO.setContent(choiceText);
                        voteDTO.setVoteNum(0);
                        choiceLIst.add(voteDTO);
                    }
                }
                // 선택지가 하나라도 있으면
                if(choiceLIst.size()!=0){
                    Log.d("#########check2", Integer.toString(choiceLIst.size()));
                    // 선택지가 두개 이상이면
                    if(choiceLIst.size()>1){
                        // 선택지 배열 추가
                        roomUpload.setVoteList(choiceLIst);
                        // 글 종류
                        roomUpload.setTextType("투표");
                        // 내용
                        roomUpload.setWriting_content(contentCheck);

                        // 중복 가능 체크 확인
                        if(checkMulti==1){
                            roomUpload.setDuplicate(true);
                        }
                        else {
                            roomUpload.setDuplicate(false);
                        }
                        // 마감 기한 설정 확인
                        if(checkDeadline==1){
                            // 마감 기한이 비어있지 않으면
                            if(deadlineDate!=null){
                                roomUpload.setVoteDeadline(deadlineDate);

                                // 알람 설정
                                List<AccountDTO> roomMemberList = currentRoom.getMemberList();
                                for(int i=0; i<roomMemberList.size(); i++){
                                    AccountDTO member = roomMemberList.get(i);

                                    // 나를 제외한 다른 멤버들
                                    if(!member.getUid().equals(currentUser.getUid())){
                                        List<RoomUploadDTO> alarmList;
                                        if(member.getMyAlarm()!=null) {
                                            alarmList = member.getMyAlarm();
                                        }
                                        else{
                                            alarmList = new ArrayList<>();
                                        }
                                        alarmList.add(roomUpload);
                                        member.setMyAlarm(alarmList);
                                        // 알람 저장
                                        dataref3.child(member.getUid()).setValue(member);
                                    }
                                }

                                // 데이터베이스에 업로드
                                dataref.child(currentRoomid).child(uploadingTime).setValue(roomUpload);
                                dataref2.child(currentRoom.getId()).setValue(currentRoom);
                                pass = true;
                            }
                            else{
                                Toast.makeText(this, "마감 기한을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            // 알람 설정
                            List<AccountDTO> roomMemberList = currentRoom.getMemberList();
                            for(int i=0; i<roomMemberList.size(); i++){
                                AccountDTO member = roomMemberList.get(i);

                                // 나를 제외한 다른 멤버들
                                if(!member.getUid().equals(currentUser.getUid())){
                                    List<RoomUploadDTO> alarmList;
                                    if(member.getMyAlarm()!=null) {
                                        alarmList = member.getMyAlarm();
                                    }
                                    else{
                                        alarmList = new ArrayList<>();
                                    }
                                    alarmList.add(roomUpload);
                                    member.setMyAlarm(alarmList);
                                    // 알람 저장
                                    dataref3.child(member.getUid()).setValue(member);
                                }
                            }

                            // 데이터베이스에 업로드
                            dataref.child(currentRoomid).child(uploadingTime).setValue(roomUpload);
                            dataref2.child(currentRoom.getId()).setValue(currentRoom);
                            pass = true;
                        }
                    }
                    else{
                        Toast.makeText(this, "선택지를 2개 이상 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "선택지를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "투표 제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
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
    public void onChangeLayout(View selectView, TextView selectText, ImageView selectImg, View hideView, TextView hideText, ImageView hideImg) {
     // 선택한 레이아웃
        // 레이아웃 높이 설정
        ViewGroup.LayoutParams layoutParams = selectView.getLayoutParams();
        if(selectText.getText().equals("SESSION 투표")){
            layoutParams.height = Toolbar.LayoutParams.MATCH_PARENT;
        }
        else{
            layoutParams.height = Toolbar.LayoutParams.WRAP_CONTENT;
        }
        selectView.setLayoutParams(layoutParams);
        // 레이아웃 텍스트 색깔 변경
        selectText.setTextColor(Color.parseColor("#4c81f8"));
        // 레아이웃 이미지 변경
        selectImg.setImageResource(R.mipmap.icon_44);

     // 선택 안된 레이아웃
        // 레이아웃 높이 설정
        ViewGroup.LayoutParams layoutParams2 = hideView.getLayoutParams();
        layoutParams2.height = 0;
        hideView.setLayoutParams(layoutParams2);
        // 선택된 레이아웃 텍스트 색깔 변경
        hideText.setTextColor(Color.parseColor("#707070"));
        // 선택된 레아이웃 이미지 변경
        hideImg.setImageResource(R.mipmap.icon_45);
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