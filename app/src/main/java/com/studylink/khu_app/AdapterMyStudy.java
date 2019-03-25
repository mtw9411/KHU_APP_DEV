package com.studylink.khu_app;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterMyStudy extends RecyclerView.Adapter<AdapterMyStudy.MyViewHolder> {

    private ArrayList<RoomDTO> arrayList;
    public AdapterMyStudy(ArrayList<RoomDTO> items){
        arrayList = items;
    }

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images");


    // 아이템 뷰에서 아이디 찾기
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_title;
        public ImageView imageView_makeStudy;
        public View root;
        public MyViewHolder(View v) {
            super(v);
            root = v;
            textView_title = v.findViewById(R.id.textView_title);
            imageView_makeStudy = v.findViewById(R.id.imageView_makeStudy);
        }
    }

    // 내가 참여중인 스터디 방 아이템뷰 연결
    @Override
    public AdapterMyStudy.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_mystudy, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // 아이템 뷰에 데이터 넣기
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RoomDTO room = arrayList.get(position);

        // 이미지 설정
        if(room.getimageName() != null) {
            String fileName = room.getimageName();
            storageRef.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    holder.imageView_makeStudy.setImageURI(uri);
                    Glide.with(holder.root).load(uri.toString()).into(holder.imageView_makeStudy);
                }
            });
        }

        // 텍스트 설정
        holder.textView_title.setText(room.getRoomName());

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addRoom(RoomDTO room){
        arrayList.add(room);
        notifyItemInserted(arrayList.size()-1);
    }
}