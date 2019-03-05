package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.widget.TextView;

public class IdFinActivity extends AppCompatActivity {

    TextView client_email_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_fin);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");

        client_email_2.setText(email);

//        client_email_2 = findViewById(R.id.client_email_2);
//        Linkify.addLinks(client_email_2, Linkify.EMAIL_ADDRESSES);

    }
}
