package com.easiio.openrtspdemo.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.easiio.openrtspdemo.RtspConstans;
import com.easiio.openrtspdemo.utils.NetworkUtils;

public abstract class BaseFragmentActivity extends FragmentActivity{

    NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkStateReceiver = new NetworkStateReceiver();
        IntentFilter filter = new IntentFilter(RtspConstans.ACTION_NETWORK_STATE_CHANGE);
        registerReceiver(networkStateReceiver,filter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(RtspConstans.ACTION_CHECK_NETWORK_STATE);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (networkStateReceiver != null){
            unregisterReceiver(networkStateReceiver);
        }
    }

    protected void onNetworkStateChange(boolean isAvailable){

    }

    public class NetworkStateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isAvailable = NetworkUtils.iaNetworkAvailable(context);
            onNetworkStateChange(isAvailable);
        }
    }
}
