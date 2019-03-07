package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignUpFinalActivity extends AppCompatActivity {

    private TextView confirm_btn_SMS;
    private TextView confirm_btn_email;
    private Button sign_up_finish;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText signupEmail;
    private EditText signupPassword;
    private EditText signupPassword_check;
    private CheckBox signupCheckBox1;
    private CheckBox signupCheckBox2;
    private boolean password_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_final);
        mAuth = FirebaseAuth.getInstance();

        confirm_btn_SMS = (TextView)findViewById(R.id.confirm_btn_SMS);
        confirm_btn_email = (TextView) findViewById(R.id.confirm_btn_email);
        signupCheckBox1 = (CheckBox) findViewById(R.id.signupCheckBox1);
        signupCheckBox2 = (CheckBox) findViewById(R.id.signupCheckBox2);

        signupEmail = (EditText) findViewById(R.id.signupEmail);
        signupPassword = (EditText) findViewById(R.id.signupPassword);
        signupPassword_check = (EditText) findViewById(R.id.signupPassword_check);
        sign_up_finish = (Button) findViewById(R.id.sign_up_finish);

        sign_up_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(signupEmail.getText().toString(), signupPassword.getText().toString());
            }
        });

        confirm_btn_email.setClickable(true);
        confirm_btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(signupEmail.getText()).matches()) {
                    Toast.makeText(SignUpFinalActivity.this, "이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(SignUpFinalActivity.this, "이메일 형식이 확인되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupPassword_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                signupCheckBox2.setChecked(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(signupPassword.getText().toString().equals(signupPassword_check.getText().toString())){
                    signupCheckBox2.setChecked(true);
                } else{
                    signupCheckBox2.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Pattern.matches("^[a-zA-Z0-9]{8,20}$", signupPassword.getText().toString())){
                    Toast.makeText(SignUpFinalActivity.this,"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                signupCheckBox1.setChecked(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Pattern.matches("^[a-zA-Z0-9]{8,20}$", signupPassword.getText().toString()))
                {
                    signupCheckBox1.setChecked(false);
                } else{
                    signupCheckBox1.setChecked(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        confirm_btn_SMS.setClickable(true);
        confirm_btn_SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_btn_SMS.setText("재전송");
            }
        });
    }


    private void createUser(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpFinalActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpFinalActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpFinalActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
