package com.mao.duoduo.activity.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mao.duoduo.R;
import com.mao.duoduo.activity.IRegisterView;
import com.mao.duoduo.presenter.impl.RegisterPresenter;

/**
 * Created by Mao on 2016/11/2.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, IRegisterView {

    private static final String TAG = "RegisterActivity";

    private EditText mEtAccount;
    private EditText mEtPassword;
    private Button mBtnRegister;

    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mRegisterPresenter = new RegisterPresenter(this);
    }

    @Override
    public void onClick(View v) {
        mRegisterPresenter.register(mEtAccount.getText().toString(), mEtPassword.getText().toString());
    }

    @Override
    public void registerResult(boolean result, String message) {
        if (result) {
            finish();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_register);
        mEtAccount = (EditText) findViewById(R.id.et_register_name);
        mEtPassword = (EditText) findViewById(R.id.et_register_pwd);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
    }

}
