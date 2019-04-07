package com.studylink.khu_app;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private String imagePath;
    private ArrayList<MyData> mDataset;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_writing);

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        writing_recycler = findViewById(R.id.recycler_picture);
        writing_recycler.setLayoutManager(new LinearLayoutManager(this));
        WritingAdapter = new WritingRecyclerViewAdapter();
        writing_recycler.setAdapter(WritingAdapter);

        upload_image = (ImageView) findViewById(R.id.upload_imageview);
        delete_picture = (ImageView) findViewById(R.id.delete_picture);
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

                startActivityForResult(intent, GALLERY_CODE);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {                                       //version 몇 이상부터 작동하기
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        complete_btn = (TextView) findViewById(R.id.writing_complete);
        complete_btn.setClickable(true);
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(imagePath);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == GALLERY_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    upload_image.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imagePath = getPath(data.getData());

            }
        }
    }

//        if(requestCode == GALLERY_CODE){
//            imagePath = getPath(data.getData());
//
//            File f = new File (imagePath);
//            upload_image.setImageURI(Uri.fromFile(f));                                                 //경로의 파일을 이미지뷰에 올리기
//        }

    public String getPath(Uri uri) {                                 //경로 코드
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    class WritingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_writing_recycler_picture, viewGroup, false);

            return new WritingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ((WritingViewHolder) viewHolder).upload_image.setImageResource(mDataset.get(i).img);
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        private class WritingViewHolder extends RecyclerView.ViewHolder {
            ImageView upload_image;
            ImageView delete_picture;

            public WritingViewHolder(View view) {
                super(view);
                upload_image = findViewById(R.id.upload_imageview);
                delete_picture = findViewById(R.id.delete_picture);

            }
        }
    }

    private void upload(String uri) {
        String mauth = auth.getCurrentUser().getUid();
        final RoomDTO roomDTOs = new RoomDTO();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com");

        final Uri file = Uri.fromFile(new File(uri));                                                     //파일이 업로드 되는 경우
        final StorageReference riversRef = storageRef.child("images/" + roomDTOs.getRoomName() + mauth + file.getLastPathSegment());
        final UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {                           //파일이 업로드 되었을 때 또 다른 이벤트 발생
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return riversRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUrl = task.getResult();

                            TimelineWritingDTO writingDTO = new TimelineWritingDTO();
                            writingDTO.setTitle(writing_title.getText().toString());
                            writingDTO.setDescription(writing_content.getText().toString());
                            writingDTO.setUid(auth.getCurrentUser().getUid());
                            writingDTO.setUserId(auth.getCurrentUser().getEmail());
                            writingDTO.setImageName(file.getLastPathSegment());
                            writingDTO.setImageUrl(downloadUrl.toString());

                            database.getReference().child(roomDTOs.getRoomName()).child("roomcontent").setValue(writingDTO);
                            Toast.makeText(Timeline_writing.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    class MyData {
        public int img;

        public MyData(int img) {
            this.img = img;
        }
    }
}