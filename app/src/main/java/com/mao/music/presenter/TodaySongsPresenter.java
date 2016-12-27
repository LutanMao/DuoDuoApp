package com.mao.music.presenter;

import com.mao.music.activity.ITodaySongsView;
import com.mao.music.model.ITodaySongsModel;
import com.mao.music.model.TodaySongsModel;

/**
 * Created by Mao on 16-12-27.
 */
public class TodaySongsPresenter implements ITodaySongsPresenter {

    private ITodaySongsView mTodaySongsView;
    private ITodaySongsModel mTodaySongsModel;

    public TodaySongsPresenter(ITodaySongsView todaySongsView) {
        this.mTodaySongsView = todaySongsView;
        mTodaySongsModel = new TodaySongsModel(this);
    }

    @Override
    public void getTodaySongs() {
        mTodaySongsModel.getTodaySongs();
    }

}
