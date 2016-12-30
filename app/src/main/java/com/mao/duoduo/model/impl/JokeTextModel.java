package com.mao.duoduo.model.impl;

import com.mao.duoduo.bean.Joke;
import com.mao.duoduo.model.IJokeModel;
import com.mao.duoduo.presenter.IJokePresenter;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 16-12-30.
 */
public class JokeTextModel implements IJokeModel {

    private static final String HOST_JOKE = "http://japi.juhe.cn/joke/content/text.from?";

    private static final int PAGE_SIZE = 10;
    private static int text_page = 0;
    private static int image_page = 1;

    private IJokePresenter mJokePresenter;

    public JokeTextModel(IJokePresenter jokePresenter) {
        this.mJokePresenter = jokePresenter;
    }

    @Override
    public void getTextJokes() {
        text_page++;
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("key", "10003")
                .add("page", text_page + "")
                .add("pagesize", PAGE_SIZE + "")
                .build();

        final Request request = new Request.Builder()
                .url(HOST_JOKE)
                .post(formBody)
                .build();

        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                mJokePresenter.getTextJokesResult(false, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    List<Joke> jokes = new ArrayList<Joke>();
                    try {
                        JSONObject result = new JSONObject(response.body().toString());
                        JSONObject result1 = result.optJSONObject("result");
                        JSONArray data = result1.optJSONArray("data");
                        JSONObject json = null;
                        Joke joke = null;
                        for (int i = 0; i < data.length(); i++) {
                            json = data.optJSONObject(1);
                            joke.setContent(json.optString("content"));
                            joke.setUpdateTime(json.optString("updatetime"));
                            jokes.add(joke);
                        }
                        mJokePresenter.getTextJokesResult(true, jokes);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
