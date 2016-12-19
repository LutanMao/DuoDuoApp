package com.mao.duoduo.utils;

import android.util.Log;

/**
 * Created by Mao on 2016/12/19.
 */
public class MaoLog {

    private static final String TAG = "MaoLog";

    private static boolean isDebug = true;

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            Log.w(TAG, msg);
        }
    }

}
