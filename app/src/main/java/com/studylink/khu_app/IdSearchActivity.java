package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class IdSearchActivity extends AppCompatActivity {

    TextView id_search_btn, email_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);

        id_search_btn = findViewById(R.id.id_search_btn);
        email_search = findViewById(R.id.email_search);


        id_search_btn.setClickable(true);
        id_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_search.getText().toString();
                if(email.trim().length()>0){
                    Intent intent = new Intent(IdSearchActivity.this, IdFinActivity.class);
                    intent.putExtra("email", email);

                    startActivity(intent);
                }
                else{
                    Toast.makeText(IdSearchActivity.this, "이메일을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
