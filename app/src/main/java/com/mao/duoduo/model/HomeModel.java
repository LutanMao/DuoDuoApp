package com.mao.duoduo.model;

import com.mao.duoduo.presenter.IHomePresenter;
import com.mao.duoduo.utils.MaoLog;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by Mao on 2016/12/21.
 */
public class HomeModel implements IHomeModel {

    private static final String TAG = "HomeModel";

    private static final String HOST_WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini?";

//    http://wthrcdn.etouch.cn/weather_mini?city=北京
//    通过城市名字获得天气数据，json数据
//    http://wthrcdn.etouch.cn/weather_mini?citykey=101010100
//    通过城市id获得天气数据，json数据

    private IHomePresenter mHomePresenter;

    public HomeModel(IHomePresenter homePresenter) {
        this.mHomePresenter = homePresenter;
    }

    @Override
    public void getWeatherByName(String cityName) {
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(HOST_WEATHER_API + "city=" + cityName);
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                mHomePresenter.getWeatherResult(false, e.getMessage());
                MaoLog.e(TAG, "Get weather failure : " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MaoLog.e(TAG, "Get weather success.");
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    MaoLog.e(TAG, "Get weather success -- cache " + str);
                } else {
                    String result = response.body().string();
                    // TODO: 16-12-26 返回的结果还是在子线程中，在UI线程中更新UI
//                    mHomePresenter.getWeatherResult(true, result);
                    MaoLog.e(TAG, "Get weather success -- network " + result);
                }
            }
        });
    }

    @Override
    public void getWeatherById(String cityId) {
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(HOST_WEATHER_API + "citykey=" + cityId);
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MaoLog.e(TAG, "Get weather failure : " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MaoLog.e(TAG, "Get weather success.");
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    MaoLog.e(TAG, "Get weather success -- cache " + str);
                } else {
                    String result = response.body().string();
                    MaoLog.e(TAG, "Get weather success -- network " + result);
                }
            }
        });
    }

}
