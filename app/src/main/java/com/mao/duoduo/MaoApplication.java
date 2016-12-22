package com.mao.duoduo;

import android.app.Application;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import com.mao.duoduo.utils.ActivityStack;

/**
 * Created by Mao on 2016/11/2.
 */
public class MaoApplication extends Application {

    private static MaoApplication mApplication = null;
    public ActivityStack mActivityStack = null;

    public static MaoApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initBmobConfig();
        initActivityStack();
    }

    /**
     * 初始化Bmob参数
     */
    private void initBmobConfig() {
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
//        Bmob.initialize(this, "d158a25e670d067ba0614a82a2c11e31");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("d158a25e670d067ba0614a82a2c11e31")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }

    private void initActivityStack() {
        mActivityStack = new ActivityStack();
    }

}
