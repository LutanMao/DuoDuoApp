package com.mao.music.model;

import com.mao.music.presenter.ITodaySongsPresenter;

/**
 * Created by Mao on 16-12-27.
 */
public class TodaySongsModel implements ITodaySongsModel {

    private static final String TAG = "TodaySongsModel";

    private ITodaySongsPresenter mTodaySongsPresenter;

    public TodaySongsModel(ITodaySongsPresenter todaySongsPresenter) {
        this.mTodaySongsPresenter = todaySongsPresenter;
    }

    @Override
    public void getTodaySongs() {

    }

}
