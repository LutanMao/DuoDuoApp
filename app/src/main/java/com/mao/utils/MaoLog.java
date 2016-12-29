package com.mao.utils;

import android.util.Log;
import com.mao.AppConfiguration;

/**
 * Created by Mao on 2016/12/19.
 */
public class MaoLog {

    public static void i(String tag, String msg) {
        if (AppConfiguration.isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (AppConfiguration.isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (AppConfiguration.isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (AppConfiguration.isDebug) {
            Log.w(tag, msg);
        }
    }

}
