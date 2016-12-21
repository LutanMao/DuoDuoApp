package com.mao.duoduo.presenter;

import com.mao.duoduo.activity.IHomeView;
import com.mao.duoduo.model.IHomeModel;
import com.mao.duoduo.model.HomeModel;

/**
 * Created by Mao on 2016/12/21.
 */
public class HomePresenter implements IHomePresenter {

    private IHomeModel mHomeModel;
    private IHomeView mHomeView;

    public HomePresenter(IHomeView homeView) {
        this.mHomeView = homeView;
        mHomeModel = new HomeModel(this);
    }

    @Override
    public void getHeaderPic(String picPath) {
        mHomeModel.getHeaderPic(picPath);
    }

    @Override
    public void getHeaderPicResult(boolean result, String picPath) {
        mHomeView.getHeaderPicResult(result, picPath);
    }

}
