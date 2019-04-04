package com.studylink.khu_app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

public class MypageEditPopupActivity extends Dialog {

    private Context context;

    private EditText editText6;
    private String name;

    public MypageEditPopupActivity(Context context,String name){
        super(context);
        this.context = context;
        this.name = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit_fragment_popup);

        editText6 = findViewById(R.id.editText6);

        if(name != null){
            editText6.setText(name);
        }
    }

}