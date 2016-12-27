package com.mao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.mao.MaoApplication;

/**
 * Created by Mao on 16-12-22.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final int MAX_CLICK_DELAY_TIME = 1000;

    // 当前时间
    private long mCurrentTime;
    // 上一次点击时间
    private long mLastTime;

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
        if (mCurrentTime - mLastTime > MAX_CLICK_DELAY_TIME) {
            mLastTime = mCurrentTime;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            MaoApplication.getInstance().mActivityStack.AppExit();
        }
    }

    protected abstract void initView();

}
