package com.mao.duoduo.model;

import com.mao.duoduo.MaoApplication;
import com.mao.duoduo.bean.DaoSession;
import com.mao.duoduo.bean.Weather;
import com.mao.duoduo.bean.WeatherDao;
import com.mao.duoduo.presenter.IHomePresenter;
import com.mao.duoduo.utils.MaoLog;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    saveCities(result);
                }
            }
        });
    }

    @Override
    public void getWeatherByName(String cityName) {
        DaoSession daoSession = (MaoApplication.getInstance()).getDaoSession();
        WeatherDao weatherDao = daoSession.getWeatherDao();

        List<Weather> weathers = weatherDao.queryBuilder()
                .where(WeatherDao.Properties.Citynm.eq(cityName))
                .orderAsc(WeatherDao.Properties.Cityid)
                .list();

        int weaid = weathers.get(0).getWeaid();
        MaoLog.i(TAG, "weaid = " + weaid + " , city name = " + cityName);

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


    private void getWeatherIcon(String data) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            JSONObject result = jsonObject.optJSONObject("result");
            String weatherIcon = result.optString("weather_icon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
