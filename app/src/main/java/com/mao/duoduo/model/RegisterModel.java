package com.mao.duoduo.model;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.mao.duoduo.bean.User;
import com.mao.duoduo.presenter.IRegisterPresenter;

/**
 * Created by Mao on 2016/11/2.
 */
public class RegisterModel implements IRegisterModel {

    private static final String TAG = "RegisterModel";

    private IRegisterPresenter mRegisterPresenter;

    public RegisterModel(IRegisterPresenter registerPresenter) {
        this.mRegisterPresenter = registerPresenter;
    }

    @Override
    public void register(String account, String password) {
        User user = new User();
        user.setUsername(account);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    mRegisterPresenter.registerResult(true, "注册成功");
                } else {
                    mRegisterPresenter.registerResult(false, e.getMessage());
                }
            }
        });
    }

}
