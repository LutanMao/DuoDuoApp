package com.mao.duoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mao.duoduo.R;
import com.mao.duoduo.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final String TAG = "MainActivity";
    private static final int GET_WEATHER_CITY_ID = 106;

    private MainPresenter mMainPresenter;

    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    public void getCityIdResult(boolean result, String data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.btn_register:
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        // 必须要setContentView之后bind
        ButterKnife.bind(this);
    }

    private void initData() {
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.getCityId();
    }

}
