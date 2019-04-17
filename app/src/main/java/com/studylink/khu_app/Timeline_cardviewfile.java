package com.studylink.khu_app;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Timeline_cardviewfile extends AppCompatActivity {

    private ArrayList<Uri> imageUri = new ArrayList<>();
//    private FileimageRecyclerViewAdapter fileimageRecyclerViewAdapter;
    private RecyclerView image_recycler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_cardview_file);
//        imageUri = Timeline_writing.staticuri;

//        image_recycler = (RecyclerView) findViewById(R.id.image_recycler);
//        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
//        ((LinearLayoutManager)horizontalLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
//        image_recycler.setLayoutManager(horizontalLayoutManager);
//        fileimageRecyclerViewAdapter = new FileimageRecyclerViewAdapter(imageUri);
//        image_recycler.setAdapter(fileimageRecyclerViewAdapter);

    }

    /*class FileimageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{           //연결부분

        private ArrayList<Uri> uriArrayList;

        public FileimageRecyclerViewAdapter(ArrayList<Uri> uris){
            uriArrayList = uris;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_file_image, viewGroup, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
            ((CustomViewHolder)viewHolder).file_image.setImageURI(uriArrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return uriArrayList.size();
        }


        private class CustomViewHolder extends RecyclerView.ViewHolder {

            ImageView file_image;

            public CustomViewHolder(View view) {
                super(view);

                file_image = view.findViewById(R.id.file_image);

            }
        }
    }*/
}