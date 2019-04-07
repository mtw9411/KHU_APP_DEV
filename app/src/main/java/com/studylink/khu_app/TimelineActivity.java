package com.studylink.khu_app;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private ImageView toMypage;
    private ImageView toMainpage;
    private RecyclerView recycler_studyname;
    private RecyclerView recycler_timelineBoard;
    private ArrayList<RoomDTO> nameofroom = new ArrayList<>();
    private ArrayList<String> getroomname = new ArrayList<>();
    private StudynameRecyclerViewAdapter StudynameAdapter;
//    private TimelineBoardViewAdapter TimelineBoardViewAdapter;
    private FirebaseDatabase mdatabase;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_main);
        mdatabase = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();

        toMypage = (ImageView) findViewById(R.id.toMypage);
        toMainpage = (ImageView) findViewById(R.id.toMainpage);

        toMypage.setClickable(true);
        toMainpage.setClickable(true);
        toMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, Mypage_main.class);
                startActivity(intent);
            }
        });
        toMainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        recycler_studyname = (RecyclerView) findViewById(R.id.recycler_studyname);
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager)horizontalLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_studyname.setLayoutManager(horizontalLayoutManager);

        setNamerecycler();

//        recycler_timelineBoard = (RecyclerView) findViewById(R.id.recycler_timelineBoard);
//        recycler_timelineBoard.setLayoutManager(new LinearLayoutManager(this));
//        TimelineBoardViewAdapter = new TimelineBoardViewAdapter();
//        recycler_timelineBoard.setAdapter(TimelineBoardViewAdapter);
    }

    private void setNamerecycler(){
        setData();

        StudynameAdapter = new StudynameRecyclerViewAdapter(nameofroom);
        recycler_studyname.setAdapter(StudynameAdapter);

    }

    private void setData(){
        String a = mauth.getCurrentUser().getUid();
        mdatabase.getReference().child("users").child(a).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    AccountDTO acc = dataSnapshot.getValue(AccountDTO.class);
                    for(int i = 0; i < acc.getRoomId().size(); i++) {
                        getroomname.add(acc.getRoomId().get(i));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setData1();
    }

    private void setData1(){
        for(int i = 0; i < getroomname.size(); i++){
            mdatabase.getReference().child("room").child(getroomname.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    RoomDTO roomm = dataSnapshot.getValue(RoomDTO.class);
                    nameofroom.add(roomm);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        StudynameAdapter.notifyDataSetChanged();
    }

    class StudynameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {      //어느 스터디인지를 선택하는 부분의 recycler

        private ArrayList<RoomDTO> Room;

        public StudynameRecyclerViewAdapter(ArrayList<RoomDTO> items){
            Room = items;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_cardview, viewGroup, false);
            return new StudynameViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
            ((StudynameViewHolder)viewHolder).studyname_title.setText(Room.get(i).getRoomName());
        }

        @Override
        public int getItemCount() {
            return Room.size();
        }

        private class StudynameViewHolder extends RecyclerView.ViewHolder{
            TextView studyname_title;

            public StudynameViewHolder(View itemView) {
                super(itemView);
                studyname_title = (TextView) itemView.findViewById(R.id.studyname_title);
            }
        }
    }

//    class TimelineBoardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{           //Timeline에 올라가는 컨텐츠의 recycler
//
//        private static final int VIEW_TYPE_FILE = 0;
//        private static final int VIEW_TYPE_VOTE = 1;
//
//        @Override
//        public int getItemViewType(int position){
//            return position % 2 == 0 ? VIEW_TYPE_FILE : VIEW_TYPE_VOTE;
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//
//            if (viewType == VIEW_TYPE_FILE) {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_cardview_file, viewGroup, false);
//                return new FileViewHolder(view);
//            } else {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_cardview_vote, viewGroup, false);
//                return new VoteViewHolder(view);
//            }
//        }
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 0;
//        }
//
//        class TimelineBoardViewHolder extends RecyclerView.ViewHolder{
//
//            CardView timeline_cardview_file;
//
//
//            public TimelineBoardViewHolder(@NonNull View itemView) {
//                super(itemView);
//
//                timeline_cardview_file = (CardView) itemView.findViewById(R.id.timeline_cardview_file);
//
//            }
//        }
//
//        class FileViewHolder extends RecyclerView.ViewHolder{
//
//            public FileViewHolder(@NonNull View itemView) {
//                super(itemView);
//            }
//        }
//
//        class VoteViewHolder extends RecyclerView.ViewHolder{
//
//            public VoteViewHolder(@NonNull View itemView) {
//                super(itemView);
//            }
//        }
//    }
}

