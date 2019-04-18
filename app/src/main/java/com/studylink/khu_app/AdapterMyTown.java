package com.studylink.khu_app;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterMyTown extends RecyclerView.Adapter<AdapterMyTown.MyViewHolder> {

    private ArrayList<RoomDTO> arrayList;
    private static View.OnClickListener onClickListener;


    public AdapterMyTown(ArrayList<RoomDTO> items, View.OnClickListener onClick){
        arrayList = items;
        onClickListener = onClick;
    }

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images");


    // 아이템 뷰에서 아이디 찾기
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout_myTown;
        public TextView myTown_title, myTown_region;
        public ImageView myTown_image;
        public View root;
        public MyViewHolder(View v) {
            super(v);
            root = v;
            myTown_title = v.findViewById(R.id.myTown_title);
            myTown_region = v.findViewById(R.id.myTown_region);
            myTown_image = v.findViewById(R.id.myTown_image);
            linearLayout_myTown = v.findViewById(R.id.linearLayout_myTown);

            linearLayout_myTown.setClickable(true);
            linearLayout_myTown.setEnabled(true);
            linearLayout_myTown.setOnClickListener(onClickListener);
        }
    }

    // 내가 참여중인 스터디 방 아이템뷰 연결
    @Override
    public AdapterMyTown.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_mytown, parent, false);
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
            storageRef.child(room.getRoomName() + fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    holder.myTown_image.setImageURI(uri);
                    Glide.with(holder.root).load(uri.toString()).into(holder.myTown_image);
                }
            });
        }

        // 텍스트 설정
        holder.myTown_title.setText(room.getRoomName());
        holder.myTown_region.setText(room.getRegion());

        // 태그 설정
        holder.linearLayout_myTown.setTag(position);

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addRoom(RoomDTO room){
        arrayList.add(room);
        notifyItemInserted(arrayList.size()-1);
    }
    public RoomDTO getRoom(int position){
        return arrayList.get(position);
    }
}