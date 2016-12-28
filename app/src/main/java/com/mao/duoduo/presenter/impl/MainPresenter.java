package com.mao.duoduo.presenter.impl;

import com.mao.duoduo.activity.IMainView;
import com.mao.duoduo.model.IMainModel;
import com.mao.duoduo.model.impl.MainModel;
import com.mao.duoduo.presenter.IMainPresenter;

/**
 * Created by Mao on 2016/12/26.
 */
public class MainPresenter implements IMainPresenter {

    private IMainModel mMainModel;
    private IMainView mMainView;

    public MainPresenter(IMainView mainView) {
        this.mMainView = mainView;
        mMainModel = new MainModel(this);
    }

    @Override
    public void getCityId() {
        mMainModel.getCityId();
    }

    @Override
    public void getCityIdResult(boolean result, String data) {
        mMainView.getCityIdResult(result, data);
    }
}
