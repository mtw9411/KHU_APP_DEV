package com.studylink.khu_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Mypage_main extends Fragment{

    private RelativeLayout mypage_schedule, mypage_store, mypage_relativeEditProfile;
    private FirebaseAuth auth;
    private Mypage_schedule_fragment scheduleFragment;
    private Mypage_store_fragment storeFragment;
    private android.support.v4.app.FragmentManager fm;
    private FragmentTransaction ft;
    private TextView icontext1, icontext2, mypage_Logout, mypage_myName;
    private ImageView test1, myPage_img1, myPage_img2, mypage_editProfile;
    private ViewGroup mypage_schedulelayout, mypage_storelayout;
    private int layoutIndex = 0;
    private DatabaseReference dr;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private AccountDTO currentUser;
    private String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_mypage_main, container, false);
        auth = FirebaseAuth.getInstance();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://studylink-ec173.appspot.com").child("images/profiles/");

        icontext1 = view.findViewById(R.id.iconText1);
        icontext2 = view.findViewById(R.id.iconText2);
        myPage_img1 = view.findViewById(R.id.myPage_img1);
        myPage_img2 = view.findViewById(R.id.myPage_img2);

        mypage_Logout = view.findViewById(R.id.mypage_Logout);
        mypage_schedule = view.findViewById(R.id.mypage_schedule);
        mypage_store = view.findViewById(R.id.mypage_store);

        mypage_Logout = view.findViewById(R.id.mypage_Logout);
        mypage_relativeEditProfile = view.findViewById(R.id.mypage_relativeEditProfile);
        mypage_editProfile = view.findViewById(R.id.mypage_editProfile);
        mypage_myName = view.findViewById(R.id.mypage_myName);
        mypage_schedule = view.findViewById(R.id.mypage_schedule);
        mypage_store = view.findViewById(R.id.mypage_store);
        mypage_schedulelayout = view.findViewById(R.id.mypage_schedulelayout);
//        mypage_storelayout = view.findViewById(R.id.mypage_storelayout);

        // 프래그먼트 초기화
        scheduleFragment = new Mypage_schedule_fragment();
        storeFragment = new Mypage_store_fragment();
        ft.replace(R.id.main_frame, scheduleFragment).commit();

        // 현재 유저
        uid = auth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(AccountDTO.class);
                mypage_myName.setText(currentUser.getUsername()+"님,");
                setprofile();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mypage_relativeEditProfile.setClickable(true);
        mypage_relativeEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageEditActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        });

        mypage_schedule.setClickable(true);
        mypage_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(0);
                mypage_schedule.setBackground(getResources().getDrawable(R.drawable.mypage_icon_after));
                mypage_store.setBackground(getResources().getDrawable(R.drawable.mypage_icon_before));
                myPage_img1.setImageResource(R.mipmap.icon_19);
                myPage_img2.setImageResource(R.mipmap.icon_18);
                icontext1.setTextColor(getResources().getColor(R.color.mypageTextselected));
                icontext2.setTextColor(getResources().getColor(R.color.mypageTextunSelected));
            }
        });

        mypage_store.setClickable(true);
        mypage_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(1);
                mypage_schedule.setBackground(getResources().getDrawable(R.drawable.mypage_icon_before));
                mypage_store.setBackground(getResources().getDrawable(R.drawable.mypage_icon_after));
                myPage_img1.setImageResource(R.mipmap.icon_41);
                myPage_img2.setImageResource(R.mipmap.icon_42);
                icontext1.setTextColor(getResources().getColor(R.color.mypageTextunSelected));
                icontext2.setTextColor(getResources().getColor(R.color.mypageTextselected));
            }
        });

        mypage_Logout.setClickable(true);
        mypage_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });

        return view;
    }

    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        switch (n){
            case 0:
                ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right, R.animator.enter_from_right, R.animator.exit_to_left)
                        .replace(R.id.main_frame, scheduleFragment).commit();
                break;

            case 1:
                ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right)
                        .replace(R.id.main_frame, storeFragment).commit();
                break;
        }
    }

    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("로그아웃");
        builder.setMessage("로그아웃 하시겠습니까?");
        builder.setIcon(android.R.mipmap.sym_def_app_icon);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setprofile(){
//        if(getArguments() != null) {
//            byte[] byteArray = getArguments().getByteArray("imagebyte");
//            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            mypage_editProfile.setImageBitmap(bmp);
//        }
        if(currentUser.getProfileImg()!=null){
            storageReference.child(uid + "/" + uid + ".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    mypage_editProfile.setImageURI(uri);
                    Glide.with(getActivity()).load(uri.toString()).apply(RequestOptions.circleCropTransform())
                            .override(60,60).into(mypage_editProfile);
                }
            });
        }
    }
}
