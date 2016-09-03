package com.sxq.whiteboarddemo;

import android.app.Application;
import android.content.Context;


public class Apps extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    private static Context context;

    public static Context getAppContext(){
        return context;
    }
}
