package com.mao.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import com.mao.duoduo.R;

/**
 * Created by Mao on 16-12-27.
 */
public class MusicMainActivity extends AppCompatActivity {

    @BindView(R.id.gv_content)
    GridView mGvContent;

    private ArrayAdapter mAdapter;

    private String[] mListData = new String[]{"今日歌单", "我的电台", "推荐电台", "最新榜单", "最新专辑", "推荐艺人"};

    @OnItemClick(R.id.gv_content)
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                //今日歌单
                Intent intent = new Intent(MusicMainActivity.this, TodaySongsActivity.class);
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_music_main);
        ButterKnife.bind(this);

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mListData);
        mGvContent.setAdapter(mAdapter);

    }

    private void initData() {

    }

}
