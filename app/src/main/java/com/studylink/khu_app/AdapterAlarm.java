package com.studylink.khu_app;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterAlarm extends RecyclerView.Adapter<AdapterAlarm.MyViewHolder> {

    private ArrayList<RoomUploadDTO> arrayList;
    private static View.OnClickListener onClickListener;

    public AdapterAlarm(ArrayList<RoomUploadDTO> items, View.OnClickListener onClick){
        arrayList = items;
        onClickListener = onClick;
    }

    // 아이템 뷰에서 아이디 찾기
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView alarm_title, alarm_content, alarm_time;
        public ImageView alarm_image;
        public ConstraintLayout constraintLayout_alarm;
        public View root;
        public MyViewHolder(View v) {
            super(v);
            root = v;
            alarm_title = v.findViewById(R.id.alarm_title);
            alarm_content = v.findViewById(R.id.alarm_content);
            alarm_time = v.findViewById(R.id.alarm_time);
            alarm_image = v.findViewById(R.id.alarm_image);
            constraintLayout_alarm = v.findViewById(R.id.constraintLayout_alarm);

            constraintLayout_alarm.setClickable(true);
            constraintLayout_alarm.setEnabled(true);
            constraintLayout_alarm.setOnClickListener(onClickListener);
        }
    }

    // 내가 참여중인 스터디 방 아이템뷰 연결
    @Override
    public AdapterAlarm.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_content, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // 아이템 뷰에 데이터 넣기
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RoomUploadDTO room = arrayList.get(position);

        // 텍스트 설정
        holder.alarm_title.setText(room.getTitle());
        holder.alarm_content.setText(room.getWriting_content());

        // 시간 설정
        SimpleDateFormat dateFormat = new SimpleDateFormat("M월d일 HH시mm분");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("M월 d일");
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
                        holder.alarm_time.setText("방금");
                    }
                    else if(minute < 60){
                        holder.alarm_time.setText(minute + "분 전");
                    }
                    else{
                        holder.alarm_time.setText("이건 보일리가 없음");
                    }
                }
                else{
                    holder.alarm_time.setText(hour + "시간 전");
                }
            }
            else{
                holder.alarm_time.setText(dateFormat1.parse(roomDate).toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 태그 설정
        holder.constraintLayout_alarm.setTag(position);

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addRoom(RoomUploadDTO room){
        arrayList.add(room);
        notifyItemInserted(arrayList.size()-1);
    }
    public RoomUploadDTO getRoom(int position){
        return arrayList.get(position);
    }
}