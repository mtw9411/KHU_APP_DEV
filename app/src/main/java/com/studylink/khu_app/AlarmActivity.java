package com.studylink.khu_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        BottomNavigationView bottomNavigationview = (BottomNavigationView) findViewById(R.id.bottomNavigationView_alarm);
        bottomNavigationview.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                        switch (item.getItemId()) {

                            case R.id.menuitem_bottombar_home:
                                Intent intent1 = new Intent(AlarmActivity.this, MainActivity.class);
                                startActivity(intent1);

                                return true;

                            case R.id.menuitem_bottombar_session:
                                Intent intent2 = new Intent(AlarmActivity.this, TimelineActivity.class);
                                startActivity(intent2);

                                return true;

                            case R.id.menuitem_bottombar_alarm:

                                return true;

                            case R.id.menuitem_bottombar_mys:
                                Intent intent3 = new Intent(AlarmActivity.this, Mypage_main.class);
                                startActivity(intent3);

                                return true;
                        }
                        return false;
                    }
                });
    }


}
