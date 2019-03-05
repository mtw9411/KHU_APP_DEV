package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

                Intent intent = new Intent(IdSearchActivity.this, IdFinActivity.class);
                intent.putExtra("email", email);

                startActivity(intent);
            }
        });
    }
}
