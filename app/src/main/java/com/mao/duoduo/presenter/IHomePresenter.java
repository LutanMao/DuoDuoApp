package com.mao.duoduo.presenter;

import org.json.JSONObject;

/**
 * Created by Mao on 2016/12/21.
 */
public interface IHomePresenter {

    public void getWeatherByName(String cityName);

    public void getWeatherById(String cityId);

    public void getWeatherResult(boolean result, JSONObject data);

}
