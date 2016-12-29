package com.mao.duoduo.model.impl;

import com.mao.MaoApplication;
import com.mao.duoduo.bean.DaoSession;
import com.mao.duoduo.bean.Weather;
import com.mao.duoduo.bean.WeatherDao;
import com.mao.duoduo.model.IHomeModel;
import com.mao.duoduo.presenter.IHomePresenter;
import com.mao.utils.MaoLog;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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
        MaoLog.e(TAG, "GET WEATHER BY NAME");
        DaoSession daoSession = (MaoApplication.getInstance()).getDaoSession();
        WeatherDao weatherDao = daoSession.getWeatherDao();

        List<Weather> weathers = weatherDao.queryBuilder()
                .where(WeatherDao.Properties.Citynm.eq(cityName.substring(0, cityName.indexOf("市"))))
                .orderAsc(WeatherDao.Properties.Cityid)
                .list();

        MaoLog.e(TAG, "weather = " + weathers.size() + "; city name = " + cityName);

        int weaid = weathers.get(0).getWeaid();
        MaoLog.e(TAG, "weaid = " + weaid + " , city name = " + cityName);

        OkHttpClient httpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("app", "weather.today")
                .add("weaid", weaid + "")
                .add("appkey", "10003")
                .add("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4")
                .add("format", "json")
                .build();

        final Request request = new Request.Builder()
                .url("http://api.k780.com:88/?")
                .post(formBody)
                .build();

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
                    // TODO: 16-12-26 返回的结果还是在子线程中，在UI线程中更新UI
                    MaoLog.e(TAG, "Get weather success -- network " + result);
                    JSONObject data = null;
                    try {
                        data = new JSONObject(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject object = data.optJSONObject("result");
                    mHomePresenter.getWeatherResult(true, object);
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


//        {
//            "success":"1",
//                "result":{
//            "weaid":"399",
//                    "days":"2016-12-26",
//                    "week":"星期一",
//                    "cityno":"nanjing",
//                    "citynm":"南京",
//                    "cityid":"101190101",
//                    "temperature":"8℃/2℃",
//                    "temperature_curr":"6℃",
//                    "humidity":"92%",
//                    "weather":"小雨",
//                    "weather_curr":"阵雨",
//                    "weather_icon":"http://api.k780.com:88/upload/weather/d/3.gif",
//                    "weather_icon1":"",
//                    "wind":"西北风",
//                    "winp":"3级",
//                    "temp_high":"8",
//                    "temp_low":"2",
//                    "temp_curr":"6",
//                    "humi_high":"0",
//                    "humi_low":"0",
//                    "weatid":"4",
//                    "weatid1":"",
//                    "windid":"15",
//                    "winpid":"202"
//        }

}
