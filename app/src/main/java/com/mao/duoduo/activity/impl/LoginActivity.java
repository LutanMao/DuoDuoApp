package com.mao.duoduo.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mao.duoduo.R;
import com.mao.duoduo.activity.ILoginView;
import com.mao.duoduo.presenter.impl.LoginPresenter;

/**
 * Created by Mao on 2016/11/2.
 */
public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.et_login_name)
    EditText mEtLoginName;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mLoginPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        mLoginPresenter.login(mEtLoginName.getText().toString(), mEtLoginPwd.getText().toString());
    }

    @Override
    public void loginResult(boolean result, Object data) {
        if (result) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, (String) data, Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

}
