package com.mao.duoduo.presenter;

/**
 * Created by Mao on 2016/12/21.
 */
public interface IHomePresenter {

    public void getWeatherByName(String cityName);

    public void getWeatherById(String cityId);

    public void getWeatherResult(boolean result, String data);

}
