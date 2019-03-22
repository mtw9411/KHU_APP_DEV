package com.studylink.khu_app;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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

public class TimelineActivity extends AppCompatActivity {

    private ImageView toMypage;
    private RecyclerView recycler_studyname;
    private RecyclerView recycler_timelineBoard;
    private StudynameRecyclerViewAdapter StudynameRecyclerViewAdapter;
    private TimelineBoardViewAdapter TimelineBoardViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_main);

        toMypage = (ImageView) findViewById(R.id.toMypage);

        toMypage.setClickable(true);
        toMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, Mypage_main.class);
                startActivity(intent);
            }
        });

        recycler_studyname = (RecyclerView) findViewById(R.id.recycler_studyname);
        recycler_studyname.setLayoutManager(new LinearLayoutManager(this));
        StudynameRecyclerViewAdapter = new StudynameRecyclerViewAdapter();
        recycler_studyname.setAdapter(StudynameRecyclerViewAdapter);

        recycler_timelineBoard = (RecyclerView) findViewById(R.id.recycler_timelineBoard);
        recycler_timelineBoard.setLayoutManager(new LinearLayoutManager(this));
        TimelineBoardViewAdapter = new TimelineBoardViewAdapter();
        recycler_timelineBoard.setAdapter(TimelineBoardViewAdapter);
    }

    class StudynameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {      //어느 스터디인지를 선택하는 부분의 recycler
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_cardview, viewGroup, false);

            return new StudynameViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }


        class StudynameViewHolder extends RecyclerView.ViewHolder{

            CardView cardView_studyname;

            public StudynameViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView_studyname = (CardView) findViewById(R.id.cardview_studyname);
            }
        }

    }

    class TimelineBoardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{           //Timeline에 올라가는 컨텐츠의 recycler

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_cardview_file, viewGroup, false);


            return new TimelineBoardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class TimelineBoardViewHolder extends RecyclerView.ViewHolder{

            CardView timeline_cardview_file;


            public TimelineBoardViewHolder(@NonNull View itemView) {
                super(itemView);

                timeline_cardview_file = (CardView) findViewById(R.id.timeline_cardview_file);

            }
        }
    }
}
