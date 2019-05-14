package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordSearchActivity extends AppCompatActivity {

    EditText searchPassword_email;
    TextView search_password_finish_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_search);

        searchPassword_email = findViewById(R.id.searchPassword_email);
        search_password_finish_btn = findViewById(R.id.search_password_finish_btn);

        search_password_finish_btn.setClickable(true);
        search_password_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = searchPassword_email.getText().toString();
                if (email.trim().length()>0){
                    Intent intent = new Intent(PasswordSearchActivity.this, PasswordFinActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PasswordSearchActivity.this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
