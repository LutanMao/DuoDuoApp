package com.mao.duoduo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.mao.duoduo.MaoApplication;

/**
 * Created by Mao on 16-12-22.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final int MAX_CLICK_DELAY_TIME = 500;

    // 当前时间
    private long mCurrentTime;
    // 上一次点击时间
    private long mLastTime;
    // 点击Back次数
    private int mClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaoApplication.getInstance().mActivityStack.addActivity(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCurrentTime = System.currentTimeMillis();
        mClickTime ++;
        if (mCurrentTime - mLastTime < MAX_CLICK_DELAY_TIME) {
            MaoApplication.getInstance().mActivityStack.AppExit();
        }
    }

    protected abstract void initView();

}
