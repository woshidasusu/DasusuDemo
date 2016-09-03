package com.easiio.openrtspdemo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.easiio.openrtspdemo.RtspAPP;

public class NetworkStateObserver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        RtspAPP app = (RtspAPP) context.getApplicationContext();
        app.checkNetworkState();
    }
}
