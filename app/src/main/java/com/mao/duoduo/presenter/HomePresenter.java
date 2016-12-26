package com.mao.duoduo.presenter;

import com.mao.duoduo.activity.IHomeView;
import com.mao.duoduo.model.IHomeModel;
import com.mao.duoduo.model.HomeModel;
import org.json.JSONObject;

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
    public void getCityId() {
        mHomeModel.getCityId();
    }

    @Override
    public void getWeatherByName(String cityName) {
        mHomeModel.getWeatherByName(cityName);
    }

    @Override
    public void getWeatherById(String cityId) {
        mHomeModel.getWeatherById(cityId);
    }

    @Override
    public void getWeatherResult(boolean result, JSONObject data) {
        mHomeView.getWeatherResult(result, data);
    }
}
