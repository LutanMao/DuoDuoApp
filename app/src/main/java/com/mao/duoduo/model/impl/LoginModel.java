package com.mao.duoduo.model.impl;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.mao.duoduo.bean.User;
import com.mao.duoduo.model.ILoginModel;
import com.mao.duoduo.presenter.ILoginPresenter;

/**
 * Created by Mao on 2016/11/2.
 */
public class LoginModel implements ILoginModel {

    private ILoginPresenter mLoginPresenter;

    public LoginModel(ILoginPresenter loginPresenter) {
        this.mLoginPresenter = loginPresenter;
    }

    @Override
    public void login(String account, String password) {
        User user = new User();
        user.setUsername(account);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    mLoginPresenter.loginResult(true, BmobUser.getCurrentUser());
                } else {
                    mLoginPresenter.loginResult(false, e.getMessage());
                }
            }
        });
    }

}
