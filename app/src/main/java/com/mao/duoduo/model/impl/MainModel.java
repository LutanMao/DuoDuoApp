package com.mao.duoduo.model.impl;

import com.mao.MaoApplication;
import com.mao.duoduo.bean.DaoSession;
import com.mao.duoduo.bean.Weather;
import com.mao.duoduo.bean.WeatherDao;
import com.mao.duoduo.model.IMainModel;
import com.mao.duoduo.presenter.IMainPresenter;
import com.mao.utils.MaoLog;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Mao on 2016/12/26.
 */
public class MainModel implements IMainModel {

    private static final String TAG = "MainModel";

    private IMainPresenter mMainPresenter;

    public MainModel(IMainPresenter mainPresenter) {
        this.mMainPresenter = mainPresenter;
    }

    @Override
    public void getCityId() {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("app", "weather.city")
                .add("appkey", "10003")
                .add("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4")
                .add("format", "json")
                .build();
        Request request = new Request.Builder()
                .url("http://api.k780.com:88/?")
                .post(formBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MaoLog.i(TAG, "Get city id failure : " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    MaoLog.i(TAG, "Get city id success : " + result);
                    saveCities(result);
                    mMainPresenter.getCityIdResult(true, null);
                }
            }
        });
    }

    private void saveCities(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject result = jsonObject.optJSONObject("result");

            DaoSession daoSession = (MaoApplication.getInstance()).getDaoSession();
            WeatherDao weatherDao = daoSession.getWeatherDao();

            JSONObject object = null;
            Weather weather = null;
            Iterator it = result.keys();
            while (it.hasNext()) {
                object = result.optJSONObject((String) it.next());
                weather = new Weather(null, object.optInt("weaid"), object.optString("citynm"),
                        object.optString("cityno"), object.optLong("cityid"), object.optString("area_1"),
                        object.optString("area_2"), object.optString("area_3"));
                weatherDao.insert(weather);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
