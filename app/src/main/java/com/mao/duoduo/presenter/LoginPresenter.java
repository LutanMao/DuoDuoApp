package com.mao.duoduo.presenter;

import com.mao.duoduo.activity.ILoginView;
import com.mao.duoduo.model.ILoginModel;
import com.mao.duoduo.model.LoginModel;

/**
 * Created by Mao on 2016/11/2.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView mLoginView;
    private ILoginModel mLoginModel;

    public LoginPresenter(ILoginView loginView) {
        this.mLoginView = loginView;
        mLoginModel = new LoginModel(this);
    }

    @Override
    public void login(String name, String password) {
        mLoginModel.login(name, password);
    }

    @Override
    public void loginResult(boolean result, Object data) {
        mLoginView.loginResult(result, data);
    }

}
