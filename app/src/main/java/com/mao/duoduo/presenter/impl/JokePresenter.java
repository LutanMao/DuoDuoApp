package com.mao.duoduo.presenter.impl;

import com.mao.duoduo.activity.IJokeTextView;
import com.mao.duoduo.model.IJokeModel;
import com.mao.duoduo.model.impl.JokeTextModel;
import com.mao.duoduo.presenter.IJokePresenter;

/**
 * Created by Mao on 16-12-30.
 */
public class JokePresenter implements IJokePresenter {

    private IJokeTextView mJokeTextView;
    private IJokeModel mJokeModel;


    public JokePresenter(IJokeTextView jokeTextView) {
        this.mJokeTextView = jokeTextView;
        mJokeModel = new JokeTextModel(this);
    }

    @Override
    public void getTextJokes() {
        mJokeModel.getTextJokes();
    }

    @Override
    public void getTextJokesResult(boolean result, Object data) {
        mJokeTextView.getTextJokesResult(result, data);
    }

    @Override
    public void getImageJokes() {

    }

}
