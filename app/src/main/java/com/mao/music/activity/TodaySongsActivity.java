package com.mao.music.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mao.duoduo.R;
import com.mao.music.presenter.TodaySongsPresenter;

/**
 * Created by Mao on 16-12-27.
 */
public class TodaySongsActivity extends AppCompatActivity {

    @BindView(R.id.lv_songs)
    ListView mListView;

    private TodaySongsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_today_songs);
        ButterKnife.bind(this);
    }

    private void initData() {
        mPresenter.getTodaySongs();
    }

}
