package com.studylink.khu_app;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.List;

public class AdapterMatching extends RecyclerView.Adapter<AdapterMatching.MyViewHolder> {

    private ArrayList<RoomDTO> arrayList;
    private static View.OnClickListener onClickListener, onClickListener2;
    private AccountDTO currentUser;

    // 생성자
    public AdapterMatching(ArrayList<RoomDTO> items, View.OnClickListener onclick, View.OnClickListener onclick2){
        arrayList = items;
        onClickListener = onclick;
        onClickListener2 = onclick2;
    }

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images");


    // 아이템 뷰에서 아이디 찾기
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView matching_percent, matching_title,matching_member,matching_totalMember,matching_region,matching_age,matching_gender;
        public ImageView imageView_matching, img_ribbon;
        public LinearLayout btn_detail, btn_entrance;
        public View root;
        public MyViewHolder(View v) {
            super(v);
            root = v;
            matching_percent = v.findViewById(R.id.matching_percent);
            matching_title = v.findViewById(R.id.matching_title);
            matching_member = v.findViewById(R.id.matching_member);
            matching_totalMember = v.findViewById(R.id.matching_totalMember);
            matching_region = v.findViewById(R.id.matching_region);
            matching_age = v.findViewById(R.id.matching_age);
            matching_gender = v.findViewById(R.id.matching_gender);
            imageView_matching = v.findViewById(R.id.imageView_matching);
            img_ribbon = v.findViewById(R.id.img_ribbon);
            btn_detail = v.findViewById(R.id.btn_detail);
            btn_entrance = v.findViewById(R.id.btn_entrance);

            btn_detail.setClickable(true);
            btn_detail.setEnabled(true);
            btn_detail.setOnClickListener(onClickListener);

            btn_entrance.setClickable(true);
            btn_entrance.setEnabled(true);
            btn_entrance.setOnClickListener(onClickListener2);
        }
    }

    // 내가 참여중인 스터디 방 아이템뷰 연결
    @Override
    public AdapterMatching.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_matching, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // 아이템 뷰에 데이터 넣기
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RoomDTO room = arrayList.get(position);
        // 매칭률 설정
        double matchingNum=0;
        List<String> userDispo = currentUser.getDisposition();
        List<String> roomDispo = room.getRoomdisposition();
        for(int i=0; i<4; i++){
            // 성향이 같으면
            if(userDispo.get(i).equals(roomDispo.get(i))){
                matchingNum++;
            }
        }
        int matchingRate = (int)Math.round(matchingNum/4*100);

        holder.matching_percent.setText("매칭 " + matchingRate + "%");
        // 이미지 설정
        if(room.getimageName() != null) {
            String fileName = room.getimageName();
            storageRef.child(room.getRoomName()+ fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    holder.imageView_matching.setImageURI(uri);
                    Glide.with(holder.root).load(uri.toString()).into(holder.imageView_matching);
                }
            });
        }
        // 텍스트 설정
        holder.matching_title.setText(room.getRoomName());
        // 인원 수 설정
        holder.matching_member.setText(String.valueOf(room.getMember()));
        holder.matching_totalMember.setText("/" + room.getTotal_member().toString());
        // 지역, 나이, 성별 설정
        holder.matching_region.setText(room.getRegion());
        holder.matching_age.setText(room.getAge());
        holder.matching_gender.setText(room.getGender());

        // 태그 설정
        holder.btn_detail.setTag(position);
        holder.btn_entrance.setTag(position);

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

    public void setUser(AccountDTO user){
        currentUser = user;
    }
}