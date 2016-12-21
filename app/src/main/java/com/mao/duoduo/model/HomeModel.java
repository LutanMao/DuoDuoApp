package com.mao.duoduo.model;

import android.os.Environment;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import com.mao.duoduo.presenter.IHomePresenter;
import com.mao.duoduo.utils.MaoLog;

import java.io.File;

/**
 * Created by Mao on 2016/12/21.
 */
public class HomeModel implements IHomeModel {

    private static final String TAG = "HomeModel";

    private IHomePresenter mHomePresenter;

    public HomeModel(IHomePresenter homePresenter) {
        this.mHomePresenter = homePresenter;
    }

    @Override
    public void getHeaderPic(String picPath) {
        BmobFile bmobFile = new BmobFile("cropimage.jpeg", "", picPath);
        File saveFile = new File(Environment.getExternalStorageDirectory(), bmobFile.getFilename());
        bmobFile.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                super.onStart();
                MaoLog.i(TAG, "Start downloading...");
            }

            @Override
            public void done(String s, BmobException e) {
                if (null == e) {
                    mHomePresenter.getHeaderPicResult(true, s);
                } else {
                    mHomePresenter.getHeaderPicResult(false, e.getMessage());
                    MaoLog.i(TAG, "Download failure : " + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {
                MaoLog.i(TAG, "Progress : " + integer + " , " + l);
            }

        });
    }

}
