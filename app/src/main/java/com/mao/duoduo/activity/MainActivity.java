package com.mao.duoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mao.duoduo.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_register)
    Button mBtnRegister;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @OnClick(R.id.btn_login)
    public void login() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        // 必须要setContentView之后bind
        ButterKnife.bind(this);
    }

}
