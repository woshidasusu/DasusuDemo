package com.easiio.openrtspdemo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.easiio.openrtspdemo.utils.FileUtils;
import com.easiio.openrtspdemo.utils.ToastUtils;

import okhttp3.OkHttpClient;

public class RtspAPP extends Application {

    CheckNetworkReceiver checkNetworkReceiver;
    protected static OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        ToastUtils.initToast(mContext);
        FileUtils.initStroageDir(RtspConstans.EXTERNAL_STROAGE_ROOT_DIR);
        okHttpClient = initOkhttpClient();

        checkNetworkReceiver = new CheckNetworkReceiver();
        IntentFilter filter = new IntentFilter(RtspConstans.ACTION_CHECK_NETWORK_STATE);
        registerReceiver(checkNetworkReceiver,filter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (checkNetworkReceiver != null){
            unregisterReceiver(checkNetworkReceiver);
        }
    }

    protected OkHttpClient initOkhttpClient() {
        return new OkHttpClient.Builder().build();
    }

    public static OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    public RtspAPP(){
    }

    private static Context mContext = null;

    public static Context getAppContext(){
        return mContext;
    }

    public void checkNetworkState(){
        Intent intent = new Intent(RtspConstans.ACTION_NETWORK_STATE_CHANGE);
        sendBroadcast(intent);
    }

    public class CheckNetworkReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetworkState();
        }
    }
}
