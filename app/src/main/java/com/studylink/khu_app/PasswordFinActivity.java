package com.studylink.khu_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

public class PasswordFinActivity extends AppCompatActivity {

    TextView client_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_fin);

        client_email = findViewById(R.id.client_email);
        Linkify.addLinks(client_email, Linkify.EMAIL_ADDRESSES);

    }
}
