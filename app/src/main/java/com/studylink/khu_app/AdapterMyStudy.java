package com.studylink.khu_app;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterMyStudy extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RoomDTO> arrayList;
    private static View.OnClickListener onClickListener, onClickListener2;
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images");


    public AdapterMyStudy(ArrayList<RoomDTO> items, View.OnClickListener onClick, View.OnClickListener onClick2){
        arrayList = items;
        onClickListener = onClick;
        onClickListener2 = onClick2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }


    // 스터디 검색 버튼
    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout addStudy;

        HeaderViewHolder(View headerView) {
            super(headerView);
            addStudy = headerView.findViewById(R.id.addStudy);
            addStudy.setOnClickListener(onClickListener2);
        }
    }


    // 아이템 뷰에서 아이디 찾기
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_title, textView_time;
        public ImageView imageView_myStudy;
        public View root;
        public MyViewHolder(View v) {
            super(v);
            root = v;
            textView_title = v.findViewById(R.id.textView_title);
            imageView_myStudy = v.findViewById(R.id.imageView_myStudy);
            textView_time = v.findViewById(R.id.textView_time);

            imageView_myStudy.setClickable(true);
            imageView_myStudy.setEnabled(true);
            imageView_myStudy.setOnClickListener(onClickListener);
        }
    }

    // 내가 참여중인 스터디 방 아이템뷰 연결
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        View view;

        if(viewType==TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_addstudy, parent, false);
            holder = new HeaderViewHolder(view);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_mystudy, parent, false);
            holder = new MyViewHolder(view);
        }

        return holder;
    }


    // 아이템 뷰에 데이터 넣기
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        // 리사이클러뷰 아이템
        if(holder instanceof MyViewHolder){
            RoomDTO room = arrayList.get(position-1);

            // 이미지 설정
            if(room.getimageName() != null) {
                String fileName = room.getimageName();
                storageRef.child("roomImages/" + fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ((MyViewHolder)holder).imageView_myStudy.setImageURI(uri);
                        Glide.with(((MyViewHolder)holder).root).load(uri.toString()).into(((MyViewHolder)holder).imageView_myStudy);
                    }
                });
            }

            // 텍스트 설정
            ((MyViewHolder)holder).textView_title.setText(room.getRoomName());

            // 최근 대화 설정
            if(arrayList.get(position-1).getNewsTime() != null){
                Date nowDate = new Date();                                  // 현재 시간
                Date uploadDate = arrayList.get(position-1).getNewsTime();    // 글 생성 날짜

                long calDate = nowDate.getTime() - uploadDate.getTime();
                long day = calDate/(24*60*60*1000);
                long hour = calDate/(60*60*1000);
                long minute = calDate/(60*1000);
                day = Math.abs(day);
                hour = Math.abs(hour);
                minute = Math.abs(minute);

                String updateTime;
                if (day == 0){
                    if(hour == 0){
                        if(minute < 1){
                            updateTime = "방금 전";
                        }
                        else if(minute < 60){
                            updateTime = (minute + "분 전");
                        }
                        else{
                            updateTime = "error";
                        }
                    }
                    else{
                        updateTime = (hour + "시간 전");
                    }
                }
                else{
                    updateTime = (day + "일 전");
                }
                ((MyViewHolder)holder).textView_time.setText(updateTime + " 대화");
            }
            else{
                ((MyViewHolder)holder).textView_time.setText("대화 없음");
            }


            // 태그 설정
            ((MyViewHolder)holder).imageView_myStudy.setTag(position-1);
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }

    public void addRoom(RoomDTO room){
        arrayList.add(room);
        notifyItemInserted(arrayList.size()-1);
    }
    public RoomDTO getRoom(int position){
        return arrayList.get(position);
    }
}