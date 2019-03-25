package com.studylink.khu_app;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
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

import java.io.File;
import java.util.ArrayList;

public class Timeline_writing extends AppCompatActivity {

    private static final int GALLERY_CODE = 10;
    private RecyclerView writing_recycler;
    private WritingRecyclerViewAdapter WritingAdapter;
    private EditText writing_title;
    private EditText writing_content;
    private ImageView writing_picture;
    private ImageView upload_image;
    private ImageView delete_picture;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_writing);

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

                startActivityForResult(intent, GALLERY_CODE);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {                                       //version 몇 이상부터 작동하기
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == GALLERY_CODE){
            imagePath = getPath(data.getData());

            File f = new File (imagePath);
            upload_image.setImageURI(Uri.fromFile(f));                                                 //경로의 파일을 이미지뷰에 올리기
        }
    }

    public String getPath(Uri uri){                                 //경로 코드
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    class WritingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_writing_recycler_picture, viewGroup, false);

            return new WritingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        private class WritingViewHolder extends RecyclerView.ViewHolder{
            ImageView upload_image;
            ImageView delete_picture;
            public WritingViewHolder(View view){
                super(view);
                upload_image = findViewById(R.id.upload_imageview);
                delete_picture = findViewById(R.id.delete_picture);

            }
        }
    }
}
