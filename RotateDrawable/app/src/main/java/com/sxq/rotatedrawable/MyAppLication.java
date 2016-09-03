package com.sxq.rotatedrawable;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by sxq on 2016/6/14.
 */
public class MyAppLication extends Application {
    @Override
    public void onCreate() {
        Logger.init().methodCount(0).hideThreadInfo();
        super.onCreate();
    }
}
