package com.studylink.khu_app;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Timeline_writing extends AppCompatActivity {

    private static final int GALLERY_CODE = 10;
    private RecyclerView writing_recycler;
    private WritingRecyclerViewAdapter WritingAdapter;
    private TextView complete_btn;
    private EditText writing_title;
    private EditText writing_content;
    private ImageView writing_picture;
    private ImageView upload_image;
    private ImageView delete_picture;
    private ArrayList<String> filetitle = new ArrayList<>();
    private ArrayList<Uri> mDataset = new ArrayList<>();
    private ArrayList<String> mDatasetString = new ArrayList<>();
    private List<Bitmap> mDatasetBitmap;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference dataref;
    private ArrayList<String> upfilename = new ArrayList<>();
    private RoomUploadDTO roomUploadDTO;
    private String currentuser;
    private String roomkey = "0";
    private String currentRoomid, currentRoomCategory;
    private boolean check_title = false;
    private boolean check_content = false;
    private String contentCheck1;
    private String contentCheck2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_writing);
        roomUploadDTO = new RoomUploadDTO();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentuser = auth.getCurrentUser().getUid();


        writing_recycler = findViewById(R.id.recycler_picture);
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager)horizontalLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        writing_recycler.setLayoutManager(horizontalLayoutManager);
        WritingAdapter = new WritingRecyclerViewAdapter(mDataset);
        writing_recycler.setAdapter(WritingAdapter);

        upload_image = (ImageView) findViewById(R.id.upload_imageview);
        writing_title = (EditText) findViewById(R.id.writing_title);
        writing_content = (EditText) findViewById(R.id.writing_content);
        writing_picture = (ImageView) findViewById(R.id.writing_picture);

        writing_picture.setClickable(true);
        writing_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {                                       //version 몇 이상부터 작동하기
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

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

        Intent intent2 = getIntent();
        currentRoomid = intent2.getStringExtra("currentRoomid");
        currentRoomCategory = intent2.getStringExtra("currentRoomCategory");

        complete_btn = (TextView) findViewById(R.id.writing_complete);
        complete_btn.setClickable(true);
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(roomUploadDTO);
                Fragment fragment = new TimelineActivity();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("imageData", mDataset);
                bundle.putString("roomkey", roomkey);
                bundle.putString("puttitle", writing_title.getText().toString());
                fragment.setArguments(bundle);

                Intent intent = new Intent(Timeline_writing.this, Fragement_navi.class);
                intent.putExtra("frag_num", 1);
                Toast.makeText(Timeline_writing.this, "업로드 완료", Toast.LENGTH_SHORT).show();
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

    public void toBitmap() {
        for (int i = 0; i < mDataset.size(); i++) {
            try {
                mDatasetBitmap.add(MediaStore.Images.Media.getBitmap(getContentResolver(), mDataset.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

    //데이터 업로드
    private void uploadFile(RoomUploadDTO roomUploa) {
        dataref= database.getReference("RoomUpload");

        RoomUploadDTO roomUpload = roomUploa;

        contentCheck1 = writing_title.getText().toString();
        contentCheck2 = writing_content.getText().toString();
        //업로드할 내용이 있으면 수행
        // 스터디 카테고리
        roomUpload.setCategory(currentRoomCategory);
        // 글 종류
        roomUpload.setTextType("소식");
        // 제목
        if (contentCheck1.trim().length()>0){
            roomUpload.setTitle(contentCheck1);
            check_title = true;
        }
        //내용
        if (contentCheck2.trim().length()>0) {
            roomUpload.setWriting_content(contentCheck2);
            check_content = true;
        }
        //업로드 시간
        roomUpload.setTime(new Date());
        //업로드할 이미지가 있으면 수행
        if (mDataset.size() != 0) {
            //Unique한 파일명을 만들자.
            for(int i = 0; i < mDataset.size(); i++) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
                Date now = new Date();
                upfilename.add(formatter.format(now) + i +".jpeg");
            }
            //storage 주소와 폴더 파일명을 지정해 준다.
            for(int j = 0; j < mDataset.size(); j++) {
                StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images/" + currentRoomid + "/" + contentCheck1 + "/" + upfilename.get(j));
                storageRef.putFile(mDataset.get(j));
                filetitle.add(upfilename.get(j));
            }
            roomUpload.setFiletitle(filetitle);
            roomUpload.setFilename(upfilename);
        }
        dataref.child(currentRoomid).child(contentCheck1).setValue(roomUpload);
    }
}