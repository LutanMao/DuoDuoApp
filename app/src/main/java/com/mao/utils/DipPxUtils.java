package com.mao.utils;

import com.mao.MaoApplication;

/**
 * Created by Mao on 16-11-3.
 */
public class DipPxUtils {

    public static int dip2px(int dipValue) {
        final float scale = MaoApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(int pxValue) {
        final float scale = MaoApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
