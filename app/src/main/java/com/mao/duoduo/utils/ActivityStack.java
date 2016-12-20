package com.mao.duoduo.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Created by Mao on 16-12-20.
 */
public class ActivityStack {

    public LinkedList<Activity> mActivityList = null;

    public ActivityStack() {
        mActivityList = new LinkedList<Activity>();
    }

    /**
     * 将Activity添加到activityList中
     */
    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 获取栈顶Activity
     */
    public Activity getLastActivity() {
        return mActivityList.getLast();
    }

    /**
     * 将Activity移除
     */
    public void removeActivity(Activity activity) {
        if (null != activity && mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    /**
     * 判断某一Activity是否在运行
     */
    public boolean isActivityRunning(String className) {
        if (className != null) {
            for (Activity activity : mActivityList) {
                if (activity.getClass().getName().equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 退出所有的Activity
     */
    public void finishAllActivity() {
        for (Activity activity : mActivityList) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
