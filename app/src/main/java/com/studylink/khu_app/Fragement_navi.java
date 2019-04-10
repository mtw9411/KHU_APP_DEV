package com.studylink.khu_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Fragement_navi extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivity main_fragment;
    private TimelineActivity timeline_fragment;
    private AlarmActivity alarm_fragment;
    private Mypage_main mypage_fragment;
    private  BottomNavigationView bottomNavigationview;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragement_navi);
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        main_fragment = new MainActivity();
        timeline_fragment = new TimelineActivity();
        alarm_fragment = new AlarmActivity();
        mypage_fragment = new Mypage_main();

        bottomNavigationview = (BottomNavigationView) findViewById(R.id.bottomNavigationView_navi);
        bottomNavigationview.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                        switch (item.getItemId()) {

                            case R.id.menuitem_bottombar_home:
                                setFrag(0);

                                return true;

                            case R.id.menuitem_bottombar_session:
                                setFrag(1);

                                return true;

                            case R.id.menuitem_bottombar_alarm:
                                setFrag(2);

                                return true;

                            case R.id.menuitem_bottombar_mys:
                                setFrag(3);

                                return true;
                        }
                        return false;
                    }
                });
        setFrag(0);
        itemCheck();
    }

    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (n){
            case 0:
                fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.Frame_navi, main_fragment);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.Frame_navi, timeline_fragment);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.Frame_navi, alarm_fragment);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.Frame_navi,mypage_fragment);
                fragmentTransaction.commit();
                break;
        }
    }

    public void itemCheck(){
//        Fragment fragment = fragmentManager.findFragmentById(R.id.Frame_navi);
        BottomNavigationView bottomNaviview = (BottomNavigationView) findViewById(R.id.bottomNavigationView_navi);

        for (Fragment currentFragment: getSupportFragmentManager().getFragments()) {
            if (currentFragment.isVisible()) {
                //할일
                if(currentFragment == main_fragment){
                    bottomNaviview.getMenu().getItem(0).setChecked(true);
                } else if (currentFragment == timeline_fragment){
                    bottomNaviview.getMenu().getItem(1).setChecked(true);
                } else if (currentFragment == alarm_fragment){
                    bottomNaviview.getMenu().getItem(2).setChecked(true);
                } else if (currentFragment == mypage_fragment){
                    bottomNaviview.getMenu().getItem(3).setChecked(true);
                }
            }
        }
    }
}
