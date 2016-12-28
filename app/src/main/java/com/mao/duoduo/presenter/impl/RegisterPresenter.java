package com.mao.duoduo.presenter.impl;

import com.mao.duoduo.activity.IRegisterView;
import com.mao.duoduo.model.IRegisterModel;
import com.mao.duoduo.model.impl.RegisterModel;
import com.mao.duoduo.presenter.IRegisterPresenter;

/**
 * Created by Mao on 2016/11/2.
 */
public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView mRegisterView;
    private IRegisterModel mRegisterModel;

    public RegisterPresenter(IRegisterView registerView) {
        this.mRegisterView = registerView;
        mRegisterModel = new RegisterModel(this);
    }

    @Override
    public void register(String name, String password) {
        mRegisterModel.register(name, password);
    }

    @Override
    public void registerResult(boolean result, String message) {
        mRegisterView.registerResult(result, message);
    }

}
