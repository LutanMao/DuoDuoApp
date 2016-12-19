package com.mao.duoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.mao.duoduo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnRegister;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.btn_login:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void initListener() {
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

}
