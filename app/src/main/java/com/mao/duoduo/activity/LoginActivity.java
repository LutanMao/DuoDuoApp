package com.mao.duoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mao.duoduo.R;
import com.mao.duoduo.presenter.LoginPresenter;

/**
 * Created by Mao on 2016/11/2.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private EditText mEtLoginName;
    private EditText mEtLoginPwd;
    private Button mBtnLogin;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
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
        mEtLoginName = (EditText) findViewById(R.id.et_login_name);
        mEtLoginPwd = (EditText) findViewById(R.id.et_login_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

}
