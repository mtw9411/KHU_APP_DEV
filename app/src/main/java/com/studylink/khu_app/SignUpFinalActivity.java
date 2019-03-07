package com.studylink.khu_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    private Button signupCheckBox2;
    private boolean password_check = false;
    private boolean emailverified = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_final);
        mAuth = FirebaseAuth.getInstance();

        confirm_btn_SMS = (TextView)findViewById(R.id.confirm_btn_SMS);
        confirm_btn_email = (TextView) findViewById(R.id.confirm_btn_email);
        signupCheckBox1 = (CheckBox) findViewById(R.id.signupCheckBox1);
        signupCheckBox2 = (Button) findViewById(R.id.signupCheckBox2);

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
                    emailverified = true;
                    Toast.makeText(SignUpFinalActivity.this, "이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(SignUpFinalActivity.this, "이메일 형식이 확인되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*signupCheckBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordcheck();
            }
        });*/

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

    /*private void passwordcheck(){
        String pw1 = signupPassword.getText().toString();
        String pw2 = signupPassword_check.getText().toString();
        if(pw1 == pw2){
            password_check = true;
            Toast.makeText(this, "확인", Toast.LENGTH_SHORT).show();
        } else{
            password_check = false;
            Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
