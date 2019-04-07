package com.studylink.khu_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

public class PasswordFinActivity extends AppCompatActivity {

    TextView client_email_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_fin);

        client_email_2 = findViewById(R.id.client_email_2);
        Linkify.addLinks(client_email_2, Linkify.EMAIL_ADDRESSES);

    }
}
