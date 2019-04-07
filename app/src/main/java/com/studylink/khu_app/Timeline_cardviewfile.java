package com.studylink.khu_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class Timeline_cardviewfile extends AppCompatActivity {

    private RecyclerView file_recycler;
    private FileRecyclerViewAdapter fileAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_cardview_file);

        file_recycler = findViewById(R.id.file_recyclerview);
        file_recycler.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileRecyclerViewAdapter();
        file_recycler.setAdapter(fileAdapter);

    }

    class FileRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        private class fileViewHolder extends RecyclerView.ViewHolder{

            public fileViewHolder(View view){
                super(view);

            }
        }
    }
}