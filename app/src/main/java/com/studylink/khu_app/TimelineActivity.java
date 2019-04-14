package com.studylink.khu_app;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends Fragment {

    private TextView write_content;
    private String write_title;
    private ImageView toMypage;
    private ImageView toMainpage;
    private RecyclerView recycler_studyname;
    private RecyclerView recycler_timelineBoard;
    private ArrayList<RoomDTO> nameofroom = new ArrayList<>();
    private ArrayList<String> getroomname = new ArrayList<>();
    private ArrayList<ArrayList<Uri>> groupUri = new ArrayList<ArrayList<Uri>>();
    private StudynameRecyclerViewAdapter StudynameAdapter;
    private TimelineBoardViewAdapter timelineBoardViewAdapter;
    private FirebaseDatabase mdatabase;
    private DatabaseReference dataref;
    private FirebaseAuth mauth;
    private FirebaseStorage storage;
    private String currentUser;
    private ArrayList<RoomUploadDTO> uploadDTOArrayList = new ArrayList<>();
    private ArrayList<Uri> imageUri = new ArrayList<>();
    private int i = 0;
    private int k = 0;
    private String currentRoomuid;
    private String roomkey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.timeline_main, container, false);
        mdatabase = FirebaseDatabase.getInstance();
        dataref = mdatabase.getReference();
        mauth = FirebaseAuth.getInstance();
        currentUser = mauth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();


        toMypage = (ImageView) view.findViewById(R.id.toMypage);
        toMainpage = (ImageView) view.findViewById(R.id.toMainpage);

        write_content = (TextView) view.findViewById(R.id.write_content);
        write_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Timeline_writing.class);
                intent.putExtra("currentRoomid", currentRoomuid);
                startActivity(intent);
            }
        });

        toMypage.setClickable(true);
        toMainpage.setClickable(true);
        toMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Mypage_main.class);
                startActivity(intent);
            }
        });
        toMainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        recycler_studyname = (RecyclerView) view.findViewById(R.id.recycler_studyname);
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager)horizontalLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_studyname.setLayoutManager(horizontalLayoutManager);
        StudynameAdapter = new StudynameRecyclerViewAdapter(nameofroom);
        recycler_studyname.setAdapter(StudynameAdapter);

        setData();

        StudynameAdapter.notifyDataSetChanged();

        dataref.child("users").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccountDTO acc = dataSnapshot.getValue(AccountDTO.class);
                currentRoomuid = acc.getRoomId().get(0);
                setTimelineData();

                timelineBoardViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recycler_timelineBoard = (RecyclerView) view.findViewById(R.id.recycler_timelineBoard);
        recycler_timelineBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        timelineBoardViewAdapter = new TimelineBoardViewAdapter(groupUri, uploadDTOArrayList);
        recycler_timelineBoard.setAdapter(timelineBoardViewAdapter);

        getbundle();

        timelineBoardViewAdapter.notifyDataSetChanged();

        //initialize();

        return view;
    }

    private void getbundle(){
        if(getArguments()!=null){
            Bundle bundle = getArguments();
            imageUri = bundle.getParcelableArrayList("ImageData");
            roomkey = bundle.getString("roomkey");
            write_title = bundle.getString("puttitle");
            timelineBoardViewAdapter.notifyDataSetChanged();
        }
    }

//    private void initialize(){
//        mdatabase.getReference().child("users").child(mauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                AccountDTO accountDTO = dataSnapshot.getValue(AccountDTO.class);
//                currentRoomuid = accountDTO.getRoomId().get(0);
//
//                timelineBoardViewAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void setTimelineData() {
        if(k == 0){
            dataref.child("RoomUpload").child(currentRoomuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    RoomUploadDTO uploadDTO;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        uploadDTO = data.getValue(RoomUploadDTO.class);
                        uploadDTOArrayList.add(uploadDTO);
                    }
                    timelineBoardViewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            k++;
        }
    }


//

    private void setData(){
        if(i == 0){
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
            i++;
        }
    }

    private void setData1(){
            mdatabase.getReference().child("room").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int j = 0;
                    RoomDTO roomm;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        roomm = data.getValue(RoomDTO.class);
                        if (j < getroomname.size()) {
                            if (getroomname.get(j).equals(roomm.getId())) {
                                nameofroom.add(roomm);
                                StudynameAdapter.notifyDataSetChanged();
                                j++;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
            ((StudynameViewHolder)viewHolder).studyname_title.setText(Room.get(position).getRoomName());
            ((StudynameViewHolder) viewHolder).studyname_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentRoomuid = Room.get(position).getId();
                }
            });
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

    class TimelineBoardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{           //Timeline에 올라가는 컨텐츠의 recycler

        private static final int VIEW_TYPE_FILE = 0;
        private static final int VIEW_TYPE_VOTE = 1;

        private ArrayList<ArrayList<Uri>> uriArrayList;
        private ArrayList<RoomUploadDTO> dtoArrayList;

        public TimelineBoardViewAdapter (ArrayList<ArrayList<Uri>> uris, ArrayList<RoomUploadDTO> uploadDTOArrayList){
            uriArrayList = uris;
            dtoArrayList = uploadDTOArrayList;
        }

//        @Override
//        public int getItemViewType(int position){
//            return position % 2 == 0 ? VIEW_TYPE_FILE : VIEW_TYPE_VOTE;
//        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

 //           if (viewType == VIEW_TYPE_FILE) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_cardview_file, viewGroup, false);
                return new FileViewHolder(view);
 //           } else {
 //               View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_cardview_vote, viewGroup, false);
 //               return new VoteViewHolder(view);
 //                    }
        }
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
//                if(uriArrayList.get(i).size() == 1) {
//                    ((FileViewHolder) viewHolder).imagefile1.setImageURI(uriArrayList.get(position).get(0));
//                } else if (uriArrayList.get(i).size() == 2){
//                    ((FileViewHolder) viewHolder).imagefile1.setImageURI(uriArrayList.get(position).get(0));
//                    ((FileViewHolder) viewHolder).imagefile2.setImageURI(uriArrayList.get(position).get(1));
//                } else if (uriArrayList.get(i).size() >= 3) {
//                    ((FileViewHolder) viewHolder).imagefile1.setImageURI(uriArrayList.get(position).get(0));
//                    ((FileViewHolder) viewHolder).imagefile2.setImageURI(uriArrayList.get(position).get(1));
//                    ((FileViewHolder) viewHolder).imagefile3.setImageURI(uriArrayList.get(position).get(2));
//                } else if(uriArrayList.get(i).size() == 0){
//                ((FileViewHolder)viewHolder).imagefile1.setImageResource(0);
//                ((FileViewHolder)viewHolder).imagefile2.setImageResource(0);
//                ((FileViewHolder)viewHolder).imagefile3.setImageResource(0);
//                }
                ((FileViewHolder)viewHolder).file_username.setText(dtoArrayList.get(position).getUploadername());
                ((FileViewHolder)viewHolder).file_contentText.setText(dtoArrayList.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return dtoArrayList.size();
        }

        class TimelineBoardViewHolder extends RecyclerView.ViewHolder{

            public TimelineBoardViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        private class FileViewHolder extends RecyclerView.ViewHolder{

            TextView file_username;
            TextView file_contentText;
            RecyclerView recyclerView;

            public FileViewHolder(@NonNull View itemView) {
                super(itemView);
                file_username = itemView.findViewById(R.id.file_username);
                file_contentText = itemView.findViewById(R.id.file_contentText);
                recyclerView = itemView.findViewById(R.id.image_recycler);

            }
        }

        class VoteViewHolder extends RecyclerView.ViewHolder{

            public VoteViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}

