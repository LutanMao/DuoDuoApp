package com.mao.duoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mao.duoduo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_register)
    Button mBtnRegister;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

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
        // 必须要setContentView之后bind
        ButterKnife.bind(this);
    }

    private void initListener() {
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

}
