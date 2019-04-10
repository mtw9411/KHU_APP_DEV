package com.studylink.khu_app;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterNewStudy extends RecyclerView.Adapter<AdapterNewStudy.MyViewHolder> {

    private ArrayList<RoomDTO> arrayList;
    private static View.OnClickListener onClickListener;

    public AdapterNewStudy(ArrayList<RoomDTO> items, View.OnClickListener onClick){
        arrayList = items;
        onClickListener = onClick;
    }

    // 아이템 뷰에서 아이디 찾기
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView newStudy_title, newStudy_time, newStudy_region, newStudy_go;
        public View root;
        public MyViewHolder(View v) {
            super(v);
            root = v;
            newStudy_title = v.findViewById(R.id.newStudy_title);
            newStudy_time = v.findViewById(R.id.newStudy_time);
            newStudy_region = v.findViewById(R.id.newStudy_region);
            newStudy_go = v.findViewById(R.id.newStudy_go);

            newStudy_go.setClickable(true);
            newStudy_go.setEnabled(true);
            newStudy_go.setOnClickListener(onClickListener);
        }
    }

    // 내가 참여중인 스터디 방 아이템뷰 연결
    @Override
    public AdapterNewStudy.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_newstudy, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // 아이템 뷰에 데이터 넣기
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RoomDTO room = arrayList.get(position);

        // 텍스트 설정
        holder.newStudy_title.setText(room.getRoomName());
        holder.newStudy_region.setText(room.getRegion());

        // 시간 설정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
        String nowDate = dateFormat.format(new Date());
        String roomDate = dateFormat.format(room.getTime());
        try {
            Date startDate = dateFormat.parse(nowDate);
            Date endDate = dateFormat.parse(roomDate);
            long calDate = endDate.getTime() - startDate.getTime();
            long day = calDate/(24*60*60*1000);
            long hour = calDate/(60*60*1000);
            long minute = calDate/(60*1000);
            day = Math.abs(day);
            hour = Math.abs(hour);
            minute = Math.abs(minute);
            if (day == 0){
                if(hour == 0){
                    if(minute < 10){
                        holder.newStudy_time.setText("방금");
                    }
                    else if(minute < 60){
                        holder.newStudy_time.setText(minute + "분 전");
                    }
                    else{
                        holder.newStudy_time.setText("이건 보일리가 없음");
                    }
                }
                else{
                    holder.newStudy_time.setText(hour + "시간 전");
                }
            }
            else{
                holder.newStudy_time.setText(day + "일 전");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 태그 설정
        holder.newStudy_go.setTag(position);

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